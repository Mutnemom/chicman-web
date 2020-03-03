@file:JvmName("ChicManService")

package com.chicman.service

import com.chicman.service.dto.auth.GuestTokenResponse
import com.chicman.service.dto.auth.LoginPasswordRequest
import com.chicman.service.dto.auth.LoginPasswordResponse
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
//            restTemplate
//                .postForObject(
//                    "$API_ENDPOINT/auth/login/password",
//                    LoginPasswordRequest(username, password),
//                    Array<LoginPasswordResponse?>::class.java
//                )

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

}
