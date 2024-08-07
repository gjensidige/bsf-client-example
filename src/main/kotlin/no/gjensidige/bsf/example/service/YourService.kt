package no.gjensidige.bsf.example.service

import no.gjensidige.bsf.example.dto.internal.*
import java.time.LocalDate

// This is probably a bit more involved in a real world implementation, and hopefully a lot more dynamic.
fun getInformationFromYourSystem() = YourDto(
    oppdragsid = "1",
    bolig = Bolig(
        adresse = Adresse(
            gateadresse = "Helgesens Gate 1A",
            poststed = "Oslo",
            postnummer = "0037",
            kommunenummer = "0001",
            gaardsnummer = "1",
            bruksnummer = 1,
        ),
        kjeller = false,
        bruksareal = 100,
        byggeaar = 2004,
        eiendomstype = Eiendomstype.ENEBOLIG
    ),
    meglerkontor = Meglerkontor(
        avdelingsnavn = "Eiendomsmegling AS, avd. Oslo",
        avdelingsId = "AVD1",
        organisasjonsnummer = "937205139",
    ),
    selger = Selger(
        fornavn = "",
        etternavn = "Selgersen",
        personnummer = "01118912345",
        epost = "test@gjensidige.no",
        telefonnummer = "12345678"
    ),
    prisantydning = 1000000,
    salgssum = 2000000,
    overtakelsedato = LocalDate.now().plusDays(30),
    salgstype = listOf(Salgstype.VANLIG)
)
