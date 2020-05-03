@file:JvmName("ChicManService")

package com.chicman.service

import com.chicman.service.dto.auth.*
import com.chicman.utility.LogUtils
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.web.client.RestTemplate
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
object AuthService : BaseApiService() {

    private val restTemplate: RestTemplate
        get() = RestTemplateBuilder().build()

    fun getGuestToken(): GuestTokenResponse? {
        return call {
            restTemplate.getForObject(
                "$API_ENDPOINT/auth/guest",
                GuestTokenResponse::class.java
            )
        }
    }

    fun login(guestToken: String, username: String, password: String): LoginPasswordResponse? {
        return call {
            restTemplate.exchange(
                "$API_ENDPOINT/auth/login/password",
                HttpMethod.POST,
                HttpEntity(
                    LoginPasswordRequest(username, password),
                    createAuthorizationHeader(guestToken)
                ),
                Array<LoginPasswordResponse?>::class.java
            ).body
        }?.get(0)
    }

    fun register(
        guestToken: String,
        profileName: String,
        username: String,
        password: String,
        redirectUrl: String
    ): RegisterResponse? {

        LogUtils.error(profileName)
        LogUtils.error(username)
        LogUtils.error(redirectUrl)

        return call {
            restTemplate.exchange(
                "$API_ENDPOINT/auth/register",
                HttpMethod.POST,
                HttpEntity(
                    RegisterRequest(profileName, username, password, redirectUrl),
                    createAuthorizationHeader(guestToken)
                ),
                RegisterResponse::class.java
            ).body
        }
    }

}
