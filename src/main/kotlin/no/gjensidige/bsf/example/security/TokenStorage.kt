package no.gjensidige.bsf.example.security

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class TokenInfo(
    @SerialName("access_token") val accessToken: String,
)

class TokenStorage private constructor(private val oauthClient: HttpClient) {
    private val tokenStorage = mutableListOf<BearerTokens>()

    companion object {
        suspend fun create(oauthClient: HttpClient) = TokenStorage(oauthClient).also { it.addNewTokenToStorage() }
    }

    private suspend fun addNewTokenToStorage() {
        val tokenInfo: TokenInfo = oauthClient.submitForm(
            url = "https://services.testgjensidige.no/mga/sps/oauth/oauth20/token",
            formParameters = parameters {
                append("grant_type", "client_credentials")
                append("client_id", "todo")  // insert actual client id
                append("client_secret", "todo")  // insert actual client secret
            }
        ).body()
        tokenStorage.add(BearerTokens(tokenInfo.accessToken, ""))
    }

    fun getToken() = tokenStorage.last()

    suspend fun refreshAndGetToken(): BearerTokens {
        addNewTokenToStorage()
        return getToken()
    }
}
