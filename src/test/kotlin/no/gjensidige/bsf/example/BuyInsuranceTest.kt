package no.gjensidige.bsf.example

import kotlinx.coroutines.runBlocking
import no.gjensidige.bsf.example.mapping.mapToEstateSaleDtoWithInsuranceActive
import no.gjensidige.bsf.example.security.*
import no.gjensidige.bsf.example.service.getInformationFromYourSystem
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

/**
 * This is an example of how to activate (buy) a seller insurance using Gjensidige's api.
 */
class BuyInsuranceTest {

    @Test
    fun `use seller insurance api`() = runBlocking {
        // init
        val tokenStorage = tokenStorage(tokenMockClientEngine())
        val selfDeclarationApi = selfDeclarationApi(tokenStorage, mockEgenerklaeringClientEngine())
        val sellerInsuranceApi = sellerInsuranceApi(tokenStorage, mockBoligselgerforsikringClientEngine())

        // Step 1 - Fetch self declaration details
        // Fetch status on sellers progress with the self declaration
        val yourDto = getInformationFromYourSystem()
        val egenerklaeringStatus = selfDeclarationApi.getSelfDeclarationDetails(
            assignmentId = yourDto.assignmentId,
            orgnr = yourDto.brokerDepartment.orgnr
        ).body()

        // Step 2:
        // Gather info required to activate/buy the insurance
        val estateSaleDtoWithInsuranceActive = yourDto.mapToEstateSaleDtoWithInsuranceActive()

        // Step 3:
        // Buy the insurance, receive payment info if successful
        assertTrue(egenerklaeringStatus.signed, "The egenerklaering should be signed before buying")
        assertTrue(egenerklaeringStatus.insuranceAccepted, "The insurance should be accepted before buying")
        val insuranceDetails = sellerInsuranceApi.updateSellerInsurance(estateSaleDtoWithInsuranceActive).body()
        assertTrue(insuranceDetails.active, "The insurance should be active after update with insuranceAccepted=true")
    }
}
