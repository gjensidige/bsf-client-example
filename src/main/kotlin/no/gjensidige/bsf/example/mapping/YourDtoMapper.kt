package no.gjensidige.bsf.example.mapping

import no.gjensidige.bsf.api.client.model.*
import no.gjensidige.bsf.example.dto.internal.YourDto

fun YourDto.mapToEstateSaleDto() =
    EstateSaleDto(
        assignmentId = assignmentId,
        brokerDepartment = BrokerDepartmentDto(
            name = brokerDepartment.name,
            orgnr = brokerDepartment.orgnr,
            id = brokerDepartment.id,
        ),
        estate = EstateDto(
            address = AddressDto(
                streetAddress = estate.address.streetAddress,
                city = estate.address.city,
                postalCode = estate.address.postalCode,
                municipalityNumber = estate.address.municipalityNumber,
                gaardsnummer = estate.address.gaardsnummer,
                bruksnummer = estate.address.bruksnummer,
                festenummer = estate.address.festenummer,
                seksjonsnummer = estate.address.seksjonsnummer,
                bolignummer = estate.address.bolignummer
            ),
            estateType = EstateTypeDto.valueOf(estate.estateType.name),
            floorArea = estate.floorArea,
            builtYear = estate.builtYear
        ),
        mainSeller = SellerDto(
            firstname = mainSeller.firstname,
            surname = mainSeller.surname,
            socialSecurityNumber = mainSeller.socialSecurityNumber,
            phoneNumber = mainSeller.phoneNumber,
            email =  mainSeller.email
        ),
        coSellers = listOf(),
        ownershipType = OwnershiptypeDto.valueOf(ownershipType.name),
        priceSuggestion = priceSuggestion,
        forcedSale = false,
        estateOfDeceased = false,
        professional = false
    )


fun YourDto.mapToEstateSaleDtoWithFormLock() =
    mapToEstateSaleDto().copy(
        selfDeclarationFormLocked = true
    )

fun YourDto.mapToEstateSaleDtoWithInsuranceActive() =
    mapToEstateSaleDto().copy(
        sellerInsuranceActive = true,
        salePrice = salePrice,
        takeoverDate = takeoverDate,
    )
