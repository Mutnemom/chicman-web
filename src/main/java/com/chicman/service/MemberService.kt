@file:JvmName("ChicManService")

package com.chicman.service

import com.chicman.model.VerificationToken
import com.chicman.service.dto.auth.MemberActivateRequest
import com.chicman.service.dto.auth.MemberActivateResponse
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.web.client.RestTemplate
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
object MemberService : BaseApiService() {

    private val restTemplate: RestTemplate
        get() = RestTemplateBuilder().build()

    fun verifyAccount(verificationToken: VerificationToken): MemberActivateResponse? {
        return call {
            restTemplate.postForObject(
                "$API_ENDPOINT/member/activate",
                MemberActivateRequest(verificationToken.toString()),
                MemberActivateResponse::class.java
            )
        }
    }

}
