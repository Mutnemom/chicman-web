package com.chicman.service.dto.auth

import com.chicman.extension.decodeBase64
import com.chicman.extension.userPayload
import com.chicman.model.Member
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class LoginPasswordResponse(
    @SerializedName("accessToken")
    var accessToken: String? = ""
) {
    val member: Member
        get() {
            val decoded = accessToken!!.userPayload().decodeBase64()
            return Gson().fromJson(decoded, Member::class.java)
        }
}
