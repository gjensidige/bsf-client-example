package no.gjensidige.bsf.example.security

import io.ktor.client.engine.mock.*
import io.ktor.http.*
import io.ktor.utils.io.*

fun tokenMockClientEngine() = MockEngine {
    respond(
        //language=JSON
        content = ByteReadChannel("""{"access_token": "valid_token"}"""),
        status = HttpStatusCode.OK,
        headers = headersOf(HttpHeaders.ContentType, "application/json")
    )
}

fun mockEgenerklaeringClientEngine() = MockEngine { request ->
    when (request.url.encodedPath) {
        "/eiendomsmegling-api/egenerklaering", "/eiendomsmegling-api/egenerklaering/lock" ->
            respond(
                //language=JSON
                content = ByteReadChannel("""
                    {
                      "premie": 1000000,
                      "kid": "1",
                      "forsikringsnummer": "1"
                    }
                    """.trimIndent()
                ),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )

        "/eiendomsmegling-api/egenerklaering/1/status" ->
            respond(
                //language=JSON
                content = ByteReadChannel("""
                  {
                    "boligselgerforsikringAccepted": true,
                    "signed": true,
                    "signedAt": "2007-12-03",
                    "egenerklaeringLaast": true,
                    "laasChangedAt": "2007-12-03",
                    "profesjonell": false
                  }
                  """.trimIndent()
                ),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        "/eiendomsmegling-api/egenerklaering/1/signed" ->
            respond(
                content = ByteReadChannel(byteArrayOf(0x25, 0x50, 0x44, 0x46)), // Represents "%PDF" header bytes
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/pdf; charset=utf-8")
            )
       else -> respondBadRequest()
   }
}


fun mockBoligselgerforsikringClientEngine() = MockEngine { request ->
    respond(
        //language=JSON
        content = ByteReadChannel("""
            {
              "pris": 1000000,
              "kid": "1",
              "kontonummer": "1",
              "forsikringsnummer": "1"
            }
            """.trimIndent()
        ),
        status = HttpStatusCode.OK,
        headers = headersOf(HttpHeaders.ContentType, "application/json")
    )
}
