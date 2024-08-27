package no.gjensidige.bsf.example.mapping

import no.gjensidige.bsf.api.client.model.*
import no.gjensidige.bsf.example.dto.internal.YourDto

fun YourDto.mapToBoligsalgopplysninger() =
    BoligsalgopplysningerDto(
        oppdragsid = oppdragsid,
        bolig = BoligDto(
            adresse =  AdresseDto(
                gateadresse = bolig.adresse.gateadresse,
                poststed = bolig.adresse.poststed,
                postnummer = bolig.adresse.postnummer,
                kommunenummer = bolig.adresse.kommunenummer,
                gaardsnummer = bolig.adresse.gaardsnummer,
                bruksnummer = bolig.adresse.bruksnummer,
                festenummer = bolig.adresse.festenummer,
                seksjonsnummer = bolig.adresse.seksjonsnummer,
                bolignummer = bolig.adresse.bolignummer
            ),
            kjeller =  bolig.kjeller,
            eiendomstype = listOf(EiendomstypeDto.valueOf(bolig.eiendomstype.name)),
            bruksareal = bolig.bruksareal,
            byggeaar = bolig.bruksareal
        ),
        meglerkontor = MeglerkontorDto(
            partner = PartnerDto.PROPR,
            avdelingsnavn = meglerkontor.avdelingsnavn,
            avdelingsId = meglerkontor.avdelingsId,
            organisasjonsnummer = meglerkontor.organisasjonsnummer,
        ),
        hovedselger = SelgerDto(
            fornavn = selger.fornavn,
            etternavn = selger.etternavn,
            telefonnummer = selger.telefonnummer,
            epost =  selger.epost,
            personnummer = selger.personnummer
        ),
        medselgere = listOf(),
        salgstype = salgstype.map { SalgstypeDto.valueOf(it.name) },
        prisantydning = prisantydning,
    )

fun YourDto.mapToBoligsalgopplysningerWithKjopsdetaljer() =
    mapToBoligsalgopplysninger().copy(
        kjopsopplysninger = KjopsopplysningerDto(
            salgssum = salgssum,
            overtakelsedato = overtakelsedato,
            formidlingsforingsdato = overtakelsedato,  // for when the insurance should be active from, often same as overtakelsedato
            dokumenter = DokumenterDto(
                tilstandsrapport = tilstandsrapport,
                salgsoppgave = salgsoppgave,
                salgsoppgaveVedlegg = salgsoppgaveVedlegg
            )
        )
    )
