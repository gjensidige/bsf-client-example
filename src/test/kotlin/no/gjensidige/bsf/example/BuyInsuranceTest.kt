package no.gjensidige.bsf.example

import kotlinx.coroutines.runBlocking
import no.gjensidige.bsf.example.mapping.mapToBoligsalgopplysningerWithKjopsdetaljer
import no.gjensidige.bsf.example.security.*
import no.gjensidige.bsf.example.service.getInformationFromYourSystem
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

/**
 * This is an example of how to buy a boligselgerforsikring using Gjensidiges bsf api.
 */
class BuyInsuranceTest {

    @Test
    fun `use boligselgerforsikring api`() = runBlocking {
        // init
        val tokenStorage = tokenStorage(tokenMockClientEngine())
        val egenerklaeringApi = egenerklaeringApi(tokenStorage, mockEgenerklaeringClientEngine())
        val boligselgerforsikringApi = boligselgerforsikringApi(tokenStorage, mockBoligselgerforsikringClientEngine())

        // Step 1 - Read egenerklæring status
        // Fetch status on sellers progress with the egenerklæring
        val yourDto = getInformationFromYourSystem()
        val egenerklaeringStatus = egenerklaeringApi.getEgenerklaeringStatus(
            id = yourDto.oppdragsid,
            orgnr = setOf(yourDto.meglerkontor.organisasjonsnummer)
        ).body()

        // Step 2:
        // Gather info required to buy the insurance
        val boligsalgopplysningerForKjop = yourDto.mapToBoligsalgopplysningerWithKjopsdetaljer()

        // Step 3:
        // Buy the insurance, receive payment info if successful
        assertTrue(egenerklaeringStatus.signed, "The egenerklaering should be signed before buying")
        assertTrue(egenerklaeringStatus.boligselgerforsikringAccepted, "The insurance should be accepted before buying")
        val betalingsinformasjon = boligselgerforsikringApi.buyBoligselgerforsikring(boligsalgopplysningerForKjop)
    }
}