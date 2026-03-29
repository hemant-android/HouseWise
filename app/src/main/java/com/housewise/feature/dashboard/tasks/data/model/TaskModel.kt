package com.housewise.feature.dashboard.tasks.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class TaskModel(
    @SerializedName("response")
    val response: Response? = null,
    @SerializedName("statusCode")
    val statusCode: Int? = null // 200
) {
    @Keep
    data class Response(
        @SerializedName("Success")
        val success: List<Succes?>? = null
    ) {
        @Keep
        data class Succes(
            @SerializedName("assignee")
            val assignee: String? = null, // Vivek Mittal
            @SerializedName("contact_name")
            val contactName: String? = null,
            @SerializedName("contact_phone")
            val contactPhone: String? = null,
            @SerializedName("create_date")
            val createDate: String? = null,
            @SerializedName("create_datetime")
            val createDatetime: String? = null,
            @SerializedName("created_by")
            val createdBy: String? = null,
            @SerializedName("description")
            val description: String? = null, // Period inspection due for property ID 2003
            @SerializedName("id")
            val id: String? = null, // 42
            @SerializedName("last_modified")
            val lastModified: String? = null, // 2024-06-20 01:12:26
            @SerializedName("pid")
            val pid: String? = null, // 2003
            @SerializedName("remarks")
            val remarks: String? = null, // Period inspection due for property ID 2003
            @SerializedName("scheduled_date")
            val scheduledDate: String? = null, // 2024-07-01 00:00:00
            @SerializedName("status")
            val status: String? = null, // Complete
            @SerializedName("tenant_name")
            val tenantName: String? = null,
            @SerializedName("tenant_phone")
            val tenantPhone: String? = null,
            @SerializedName("type")
            val type: String? = null, // repeatInspectionReport
            @SerializedName("uid")
            val uid: String? = null, // 4744
            @SerializedName("update_datetime")
            val updateDatetime: String? = null,
            @SerializedName("updated_by")
            val updatedBy: String? = null,
            @SerializedName("vendor_name")
            val vendorName: String? = null,
            @SerializedName("vendor_phone")
            val vendorPhone: String? = null
        )
    }
}