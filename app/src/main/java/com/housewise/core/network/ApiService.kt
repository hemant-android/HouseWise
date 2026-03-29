package com.housewise.core.network

import com.housewise.core.network.model.HWRequest
import com.housewise.core.network.model.HWResponse
import com.housewise.feature.auth.data.model.LoginRequest
import com.housewise.feature.auth.data.model.LoginResponse
import com.housewise.feature.dashboard.leads.data.model.AddLeadRequest
import com.housewise.feature.dashboard.leads.data.model.LeadResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("user")
    suspend fun loginUser(@Body request: LoginRequest): Response<LoginResponse>
    
    // Future APIs go here:
    // @GET("tasks")
    // suspend fun getTasks(...): Response<TaskResponse>

    @POST("api/addlead")
    suspend fun addLead(@Body request: AddLeadRequest): Response<Unit>

    @GET("api/leads")
    suspend fun getLeads(): Response<List<LeadResponse>>

    // The single proxy endpoint for all PHP calls
    @POST("api/send")
    suspend fun proxyRequest(@Body request: HWRequest): Response<HWResponse>
}