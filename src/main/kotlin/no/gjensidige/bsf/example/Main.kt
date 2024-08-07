package no.gjensidige.bsf.example

import no.gjensidige.bsf.api.client.model.TilbudDto
import no.gjensidige.bsf.example.security.egenerklaeringApi
import no.gjensidige.bsf.example.security.tokenStorage
import no.gjensidige.bsf.example.mapping.mapToBoligsalgopplysninger
import no.gjensidige.bsf.example.service.getInformationFromYourSystem

suspend fun main() {
    val tokenStorage = tokenStorage()
    val egenerklaeringApi = egenerklaeringApi(tokenStorage)

    // Step 1:
    // Gather information about the estate sale and create a boligsalgsopplysninger
    val boligsalgopplysninger = getInformationFromYourSystem().mapToBoligsalgopplysninger()

    // Step 2:
    // Send an egenerklæring to the seller of the estate. Receive details on an insurance offer if successful.
    // The seller will either accept or reject the insurance when filling out the egenerklæring form.
    val tilbud: TilbudDto = egenerklaeringApi.sendEgenerklaering(boligsalgopplysninger).body()
    println("Sent egenerklaering, and received tilbud for boligselgerforsikring: $tilbud")
}
