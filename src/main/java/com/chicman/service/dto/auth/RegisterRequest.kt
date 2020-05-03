package com.chicman.service.dto.auth

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("profileName")
    var profileName: String?,
    @SerializedName("username")
    var username: String?,
    @SerializedName("password")
    var password: String?,
    @SerializedName("redirectUrl")
    var redirectUrl: String?
)
