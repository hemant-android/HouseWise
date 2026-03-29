package com.housewise.core.data

data class TaskData(
    val title: String,
    val propertyId: String,
    val dueDate: String,
    val status: String,
    val assignee: String,
    val tenantName: String = "",
    val tenantPhone: String = "",
    val tenantRemarks: String = ""
)