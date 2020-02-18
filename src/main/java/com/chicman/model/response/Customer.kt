package com.chicman.model.response

import com.fasterxml.jackson.annotation.JsonProperty

data class Customer(
    @JsonProperty("customerId")
    var customerId: Int?,
    @JsonProperty("email")
    var email: String?,
    @JsonProperty("enabled")
    var enabled: Boolean?,
    @JsonProperty("fullName")
    var fullName: String?,
    @JsonProperty("password")
    var password: String?,
    @JsonProperty("type")
    var type: String?
)
