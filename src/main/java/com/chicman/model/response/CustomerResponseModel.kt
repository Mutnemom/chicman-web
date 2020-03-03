package com.chicman.model.response

import com.fasterxml.jackson.annotation.JsonProperty

data class CustomerResponseModel(
    @JsonProperty("customers")
    var customers: List<Customer?>?
)