package com.chicman.service

import com.chicman.ChicManUI
import com.fasterxml.jackson.core.JsonProcessingException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component

@Component
open class BaseApiService {

    companion object {
        const val API_ENDPOINT = "http://127.0.0.1:9093/api/v1"
    }

    val log: Logger by lazy { LoggerFactory.getLogger(ChicManUI::class.java) }

    fun <R> call(task: () -> R): R? = try {
        task()
    } catch (e: JsonProcessingException) {
        e.printStackTrace()
        null
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }

    fun createAuthorizationHeader(guestToken: String): HttpHeaders {
        return HttpHeaders().apply {
            set("Authorization", "Bearer $guestToken")
            set("Content-Type", "application/json")
        }
    }

}