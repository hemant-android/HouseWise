package com.housewise.feature.dashboard.tasks.data.model

import com.google.gson.annotations.SerializedName

// This is the actual data we will convert to a String for the HWRequest body
data class CreateTaskPayload(
    @SerializedName("type") val type: String,
    @SerializedName("pid") val pid: String,
    @SerializedName("uid") val uid: String,
    @SerializedName("description") val description: String,
    @SerializedName("scheduled_date") val scheduledDate: String, // Expects "YYYY-MM-DD HH:MM:SS"
    @SerializedName("assignee") val assignee: String,
    @SerializedName("remarks") val remarks: String,
    @SerializedName("tenant_name") val tenantName: String,
    @SerializedName("tenant_phone") val tenantPhone: String,
    @SerializedName("contact_name") val contactName: String = "",
    @SerializedName("contact_phone") val contactPhone: String = "",
    @SerializedName("vendor_name") val vendorName: String = "",
    @SerializedName("vendor_phone") val vendorPhone: String = "",
    @SerializedName("status") val status: String
)