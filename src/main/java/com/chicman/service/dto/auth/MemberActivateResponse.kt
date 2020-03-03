package com.chicman.service.dto.auth

import com.google.gson.annotations.SerializedName

data class MemberActivateResponse(
    @SerializedName("accessToken")
    var accessToken: String? = ""
)
