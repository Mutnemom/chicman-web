package com.chicman.service.dto.auth

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("accessToken")
    var accessToken: String? = ""
)
