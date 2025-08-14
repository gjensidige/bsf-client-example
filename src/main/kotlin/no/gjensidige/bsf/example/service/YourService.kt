package no.gjensidige.bsf.example.service

import no.gjensidige.bsf.example.dto.internal.*
import java.time.LocalDate

// This is probably a bit more involved in a real world implementation, and hopefully a lot more dynamic.
fun getInformationFromYourSystem() = YourDto(
    assignmentId = "1",
    estate = Estate(
        address = Address(
            streetAddress = "Helgesens Gate 1A",
            city = "Oslo",
            postalCode = "0037",
            municipalityNumber = "0001",
            gaardsnummer = "1",
            bruksnummer = 1,
        ),
        builtYear = 100,
        floorArea = 2004,
        estateType = EstateType.ENEBOLIG
    ),
    brokerDepartment = BrokerDepartment(
        name = "Eiendomsmegling AS, avd. Oslo",
        id = "S000111",  // provided by Gjensidige
        orgnr = "937205139",
    ),
    mainSeller = Seller(
        firstname = "",
        surname = "Selgersen",
        socialSecurityNumber = "01118912345",
        email = "test@gjensidige.no",
        phoneNumber = "12345678"
    ),
    priceSuggestion = 1000000,
    salePrice = 2000000,
    takeoverDate = LocalDate.now().plusDays(30),
    ownershipType = OwnershipType.SELVEIER
)
