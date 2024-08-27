package no.gjensidige.bsf.example.dto.internal

import java.time.LocalDate

enum class Eiendomstype {
    ENEBOLIG,
    LEILIGHET,
    REKKEHUS,
    TOMANNSBOLIG,
    LANDBRUK,
    FRITIDSBOLIG,
    OBLIGASJONSLEILIGET,
    NAERINGSBYGG,
    HYTTE,
    ANNEN
}

enum class Salgstype {
    DODSBO,
    OPPGJOER,
    VANLIG,
    TVANGSSALG,
    VERDIVURDERING,
    FULLMAKT,
    ANNEN
}

data class Adresse(
    val gateadresse: String,
    val poststed: String,
    val postnummer: String,
    val kommunenummer: String,
    val gaardsnummer: String,
    val bruksnummer: Int,
    val festenummer: Int? = null,
    val seksjonsnummer: Int? = null,
    val bolignummer: String? = null
)

data class Bolig(
    val adresse: Adresse,
    val kjeller: Boolean = false,
    val bruksareal: Int,
    val byggeaar: Int,
    val eiendomstype: Eiendomstype
)

data class Meglerkontor(
    val avdelingsnavn: String,
    val avdelingsId: String,
    val organisasjonsnummer: String,
)

data class Selger(
    val fornavn: String,
    val etternavn: String,
    val personnummer: String,
    val epost: String,
    val telefonnummer: String
)

data class YourDto (
    val oppdragsid: String,
    val bolig: Bolig,
    val meglerkontor: Meglerkontor,
    val selger: Selger,
    val prisantydning: Int,
    val salgssum: Int,
    val overtakelsedato: LocalDate,
    val salgstype: List<Salgstype>,
    val tilstandsrapport: ByteArray? = null,
    val salgsoppgave: ByteArray? = null,
    val salgsoppgaveVedlegg: List<ByteArray> = listOf()
)
