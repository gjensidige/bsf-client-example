package no.gjensidige.bsf.example.security


import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.engine.java.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import no.gjensidige.bsf.api.client.api.SelfDeclarationApi
import no.gjensidige.bsf.api.client.api.SellerInsuranceApi
import no.gjensidige.bsf.example.util.localDateSerializer
import java.time.LocalDate


suspend fun tokenStorage(clientEngine: HttpClientEngine = Java.create { }) =
    TokenStorage.create(oauthClient(clientEngine))

fun selfDeclarationApi(tokenStorage: TokenStorage, httpClientEngine: HttpClientEngine = Java.create { }) =
    SelfDeclarationApi(
        httpClientEngine = httpClientEngine,
        httpClientConfig = httpClientConfig(tokenStorage)
    )

fun sellerInsuranceApi(tokenStorage: TokenStorage, httpClientEngine: HttpClientEngine = Java.create { }) =
    SellerInsuranceApi(
        httpClientEngine = httpClientEngine,
        httpClientConfig = httpClientConfig(tokenStorage)
    )

private fun oauthClient(clientEngine: HttpClientEngine): HttpClient = HttpClient(clientEngine) {
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
        })
    }
}

private fun httpClientConfig(tokenStorage: TokenStorage): (HttpClientConfig<*>) -> Unit = {
    it.install(ContentNegotiation) {  // Set json as content communication format
        json(Json {
            prettyPrint = true
            serializersModule = SerializersModule {
                contextual(LocalDate::class, localDateSerializer)
            }
        })
    }
    it.install(Auth) {  // Use bearer token, and define how and when to fetch tokens
        bearer {
            loadTokens {
                tokenStorage.getToken()
            }
            refreshTokens {
                tokenStorage.refreshAndGetToken()
            }
        }
    }
}
