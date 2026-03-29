package com.housewise.feature.dashboard.leads.data.model

import com.google.gson.annotations.SerializedName

data class AddLeadRequest(
    @SerializedName("propertyId") val propertyId: Int,
    @SerializedName("userId") val userId: Int,
    @SerializedName("leadName") val leadName: String,
    @SerializedName("leadEmail") val leadEmail: String?,
    @SerializedName("leadContactNumber") val leadContactNumber: String,
    @SerializedName("alternateContactNumber") val alternateContactNumber: String?,
    @SerializedName("source") val source: String,
    @SerializedName("status") val status: String,
    @SerializedName("reminderDate") val reminderDate: String?,
    @SerializedName("city") val city: String,
    @SerializedName("listingId") val listingId: String,
    @SerializedName("interestedLocation") val interestedLocation: String?,
    @SerializedName("comments") val comments: String,
    @SerializedName("createdBy") val createdBy: String
)