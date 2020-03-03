package com.chicman.service.dto.auth

import com.google.gson.annotations.SerializedName

data class GuestTokenResponse(
    @SerializedName("accessToken")
    var accessToken: String? = ""
)
