package no.gjensidige.bsf.example

import no.gjensidige.bsf.api.client.model.InsuranceDetailsDto
import no.gjensidige.bsf.example.mapping.mapToEstateSaleDto
import no.gjensidige.bsf.example.security.selfDeclarationApi
import no.gjensidige.bsf.example.security.tokenStorage
import no.gjensidige.bsf.example.service.getInformationFromYourSystem

suspend fun main() {
    val tokenStorage = tokenStorage()
    val selfDeclarationApi = selfDeclarationApi(tokenStorage)

    // Step 1:
    // Gather information about the estate sale and create an estate sale dto
    val estateSale = getInformationFromYourSystem().mapToEstateSaleDto()

    // Step 2:
    // Send a self declaration to the seller of the estate. Receive details on an insurance offer if successful.
    // The seller will either accept or reject the insurance when filling out the self declaration form.
    val insuranceDetails: InsuranceDetailsDto = selfDeclarationApi.sendSelfDeclaration(estateSale).body()
    println("Sent self declaration, and received insurance details for seller insurance: $insuranceDetails")
}
