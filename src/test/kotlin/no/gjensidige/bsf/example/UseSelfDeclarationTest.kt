package no.gjensidige.bsf.example

import io.ktor.client.statement.*
import kotlinx.coroutines.runBlocking
import no.gjensidige.bsf.api.client.model.InsuranceDetailsDto
import no.gjensidige.bsf.example.mapping.mapToEstateSaleDto
import no.gjensidige.bsf.example.mapping.mapToEstateSaleDtoWithFormLock
import no.gjensidige.bsf.example.security.mockEgenerklaeringClientEngine
import no.gjensidige.bsf.example.security.selfDeclarationApi
import no.gjensidige.bsf.example.security.tokenMockClientEngine
import no.gjensidige.bsf.example.security.tokenStorage
import no.gjensidige.bsf.example.service.getInformationFromYourSystem
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

/**
 * This is an example of how to use Gjensidiges self declaration api.
 *
 * It will
 * - Send a self declaration form for the seller to fill out
 * - Create an insurance offer
 * - Monitor the status of the self declaration form for signing
 * - Lock and retrieve the signed pdf version of the form
 */
class UseSelfDeclarationTest {

    @Test
    fun `use self declaration api`() = runBlocking {
        // init
        val tokenStorage = tokenStorage(tokenMockClientEngine())
        val selfDeclarationApi = selfDeclarationApi(tokenStorage, mockEgenerklaeringClientEngine())

        // Step 1 - Gather information
        // Fetch information about the estate sale and create a boligsalgsopplysninger object
        val estateSaleDto = getInformationFromYourSystem().mapToEstateSaleDto()

        // Step 2 - Send the self declaration
        // Send the self declaration to the seller of the estate. Receive details on an insurance offer if successful.
        // The seller will either accept or reject the insurance when filling out the form.
        var insuranceDetails: InsuranceDetailsDto = selfDeclarationApi.sendSelfDeclaration(estateSaleDto).body()

        // Step 3 - Read self declaration form status
        // Fetch status on sellers progress with the form
        val egenerklaeringStatus = selfDeclarationApi.getSelfDeclarationDetails(
            assignmentId = estateSaleDto.assignmentId,
            orgnr = estateSaleDto.brokerDepartment.orgnr
        ).body()

        // Step 4 - Lock the self declaration
        // Prevents further editing by the seller. Note that if essential information of the estate sale changed since
        // the last time, e.g. the floor area of the estate, this might cause an update to the offer (price).
        assertTrue(egenerklaeringStatus.signed, "The egenerklaering should be signed before locking")
        val estateSaleWithLock = getInformationFromYourSystem().mapToEstateSaleDtoWithFormLock()
        insuranceDetails = selfDeclarationApi.updateSelfDeclaration(estateSaleWithLock).body()

        // Retrieve the filled and signed pdf form
        val signedEgenerklaering = selfDeclarationApi.getSignedSelfDeclarationPdf(
            assignmentId = estateSaleDto.assignmentId,
            orgnr = estateSaleDto.brokerDepartment.orgnr
        ).response.bodyAsChannel()
    }
}
