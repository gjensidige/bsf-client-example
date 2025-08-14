package no.gjensidige.bsf.example.dto.internal

import java.time.LocalDate

enum class EstateType {
    ENEBOLIG,
    BOLIGHUS,
    REKKEHUS,
    LEILIGHET,
    HYTTE,
    ANNET_BOLIGBYGG
}

enum class OwnershipType {
    SELVEIER,
    BORETTSLAG,
    AKSJE,
    OBLIGASJON,
    ANNEN_EIERFORM
}

data class Address(
    val streetAddress: String,
    val city: String,
    val postalCode: String,
    val municipalityNumber: String,
    val gaardsnummer: String,
    val bruksnummer: Int,
    val festenummer: Int? = null,
    val seksjonsnummer: Int? = null,
    val bolignummer: String? = null
)

data class Estate(
    val address: Address,
    val builtYear: Int,
    val floorArea: Int,
    val estateType: EstateType
)

data class BrokerDepartment(
    val name: String,
    val orgnr: String,
    val id: String,
)

data class Seller(
    val firstname: String,
    val surname: String,
    val socialSecurityNumber: String,
    val email: String,
    val phoneNumber: String
)

data class YourDto (
    val assignmentId: String,
    val brokerDepartment: BrokerDepartment,
    val estate: Estate,
    val mainSeller: Seller,
    val priceSuggestion: Int,
    val salePrice: Int,
    val takeoverDate: LocalDate,
    val ownershipType: OwnershipType,
)
