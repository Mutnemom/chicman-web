package com.chicman.service.dto.auth

import com.google.gson.annotations.SerializedName

data class LoginPasswordRequest(
    @SerializedName("username")
    var username: String?,
    @SerializedName("password")
    var password: String?
)
