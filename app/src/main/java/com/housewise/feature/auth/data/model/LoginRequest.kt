package com.housewise.feature.auth.data.model

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("user_email_id") val email: String,
    @SerializedName("password") val password: String
)