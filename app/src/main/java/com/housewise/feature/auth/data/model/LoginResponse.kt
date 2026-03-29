package com.housewise.feature.auth.data.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    val message: String?,
    val token: String?,
    val userId: Int?,
    val roles: List<String>?,
    val firstName: String?,
    val lastName: String?
)