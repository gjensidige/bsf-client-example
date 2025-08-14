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
        "/eiendomsmegling-api/self-declaration" ->
            respond(
                //language=JSON
                content = ByteReadChannel(
                    """
                    {
                      "insuranceNumber": "1",
                      "kid": "1",
                      "accountNumber": "60050608460",
                      "price": 1000000,
                      "active": false
                    }
                    """.trimIndent()
                ),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )

        "/eiendomsmegling-api/self-declaration/1" ->
            respond(
                //language=JSON
                content = ByteReadChannel(
                    """
                  {
                      "insuranceAccepted": true,
                      "signed": true,
                      "locked": true,
                      "signedAt": "2023-01-03",
                      "lockStateChangedAt": "2023-01-03"
                  }
                  """.trimIndent()
                ),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        "/eiendomsmegling-api/self-declaration/1/signed" ->
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
        content = ByteReadChannel(
            """
                    {
                      "insuranceNumber": "1",
                      "kid": "1",
                      "accountNumber": "60050608460",
                      "price": 1000000,
                      "active": true
                    }
                    """.trimIndent()
        ),
        status = HttpStatusCode.OK,
        headers = headersOf(HttpHeaders.ContentType, "application/json")
    )
}
