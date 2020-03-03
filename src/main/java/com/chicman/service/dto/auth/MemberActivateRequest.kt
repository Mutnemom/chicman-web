package com.chicman.service.dto.auth

import com.google.gson.annotations.SerializedName

data class MemberActivateRequest(
    @SerializedName("verifier")
    var verifier: String?
)
