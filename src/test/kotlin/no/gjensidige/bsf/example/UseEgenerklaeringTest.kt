package no.gjensidige.bsf.example

import io.ktor.client.statement.*
import kotlinx.coroutines.runBlocking
import no.gjensidige.bsf.api.client.model.TilbudDto
import no.gjensidige.bsf.example.mapping.mapToBoligsalgopplysninger
import no.gjensidige.bsf.example.security.egenerklaeringApi
import no.gjensidige.bsf.example.security.mockEgenerklaeringClientEngine
import no.gjensidige.bsf.example.security.tokenMockClientEngine
import no.gjensidige.bsf.example.security.tokenStorage
import no.gjensidige.bsf.example.service.getInformationFromYourSystem
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

/**
 * This is an example of how to use Gjensidiges egenerklaering api.
 *
 * It will
 * - Send an egenerklæring form for the seller to fill out
 * - Create an insurance offer
 * - Monitor the status of the egenerklæring for signing
 * - Lock and retrieve the signed egenerklæring
 */
class UseEgenerklaeringTest {

    @Test
    fun `use egenerklaering api`() = runBlocking {
        // init
        val tokenStorage = tokenStorage(tokenMockClientEngine())
        val egenerklaeringApi = egenerklaeringApi(tokenStorage, mockEgenerklaeringClientEngine())

        // Step 1 - Gather information
        // Fetch information about the estate sale and create a boligsalgsopplysninger object
        val boligsalgopplysninger = getInformationFromYourSystem().mapToBoligsalgopplysninger()

        // Step 2 - Send the egenerklæring
        // Send the egenerklæring to the seller of the estate. Receive details on an insurance offer if successful.
        // The seller will either accept or reject the insurance when filling out the egenerklæring form.
        var tilbud: TilbudDto = egenerklaeringApi.sendEgenerklaering(boligsalgopplysninger).body()

        // Step 3 - Read egenerklæring status
        // Fetch status on sellers progress with the egenerklæring
        val egenerklaeringStatus = egenerklaeringApi.getEgenerklaeringStatus(
            id = boligsalgopplysninger.oppdragsid,
            orgnr = setOf(boligsalgopplysninger.meglerkontor.organisasjonsnummer)
        ).body()

        // Step 4 - Lock the egenerklæring
        // Prevents further editing by the seller. Note that if essential information of the estate sale changed since
        // the last time, e.g. the floor area of the estate, this might cause an update to the offer (price).
        assertTrue(egenerklaeringStatus.signed, "The egenerklaering should be signed before locking")
        tilbud = egenerklaeringApi.lockEgenerklaering(boligsalgopplysninger).body()

        // Retrieve the filled and signed egenerklæring pdf form
        val signedEgenerklaering = egenerklaeringApi.getSignedEgenerklaering(
            id = boligsalgopplysninger.oppdragsid,
            orgnr = setOf(boligsalgopplysninger.meglerkontor.organisasjonsnummer)
        ).response.bodyAsChannel()
    }
}
