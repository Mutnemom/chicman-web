@file:JvmName("ChicManService")

package com.chicman.service

import com.chicman.ChicManUI
import com.chicman.model.response.CustomerResponseModel
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.web.client.RestTemplate
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class ChicManService {

    companion object {
        fun test() {
            val log = LoggerFactory.getLogger(ChicManUI::class.java)
            val service = ChicManService()

            try {
                val response = ObjectMapper().writeValueAsString(service.getProducts())
                log.error(response)
            } catch (e: JsonProcessingException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private val restTemplate: RestTemplate
        get() = RestTemplateBuilder().build()

    fun getProducts(): CustomerResponseModel? {
        return restTemplate.getForObject(
            "http://127.0.0.1:9093/api/products",
            CustomerResponseModel::class.java
        )
    }
}