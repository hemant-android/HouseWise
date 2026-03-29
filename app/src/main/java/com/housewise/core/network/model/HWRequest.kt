package com.housewise.core.network.model

import com.google.gson.annotations.SerializedName

// The generic wrapper request you send to /api/send
data class HWRequest(
    @SerializedName("method") val method: String,
    @SerializedName("urlPath") val urlPath: String,
    @SerializedName("body") val body: String?, // This must be a stringified JSON!
    @SerializedName("params") val params: Map<String, Any> = emptyMap(),
    @SerializedName("header") val header: Map<String, String> = emptyMap()
)

// The generic wrapper response you get back
data class HWResponse(
    @SerializedName("statusCode") val statusCode: Int?,
    @SerializedName("response") val response: HWInnerResponse?
)

data class HWInnerResponse(
    // Old format support (e.g., {"status": "Success", "data": [...]})
    @SerializedName("status") val status: String?,
    @SerializedName("data") val data: Any?,

    // NEW format support (e.g., {"Success": {"id": 34786}})
    @SerializedName("Success") val success: Any?,
    @SerializedName("Error") val error: Any?
)