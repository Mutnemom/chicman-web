package com.chicman.extension

import java.util.*

fun String.userPayload(): String {
    return when {
        this.isEmpty() -> this
        else -> try {
            this.split('.')[1]
        } catch (e: Exception) {
            when (e) {
                is IndexOutOfBoundsException -> e.printStackTrace()
                else -> e.printStackTrace()
            }
            ""
        }
    }
}

fun String.decodeBase64(): String {
    return when {
        this.isEmpty() -> this
        else -> try {
            val decoded = this.decodeBase64ToByteArray()
            String(decoded)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
            ""
        }
    }
}

fun String.decodeBase64ToByteArray(): ByteArray {
    return when {
        this.isEmpty() -> ByteArray(0)
        else -> try {
            Base64.getDecoder().decode(this.toByteArray(Charsets.UTF_8))
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
            ByteArray(0)
        }
    }
}
