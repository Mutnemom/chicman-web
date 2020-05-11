package com.chicman.service.dto.auth

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("redirectUrl")
    var redirectUrl: String? = ""
)
