package com.housewise.feature.dashboard.leads.data.repository

import com.housewise.core.network.RetrofitClient
import com.housewise.core.utils.Resource
import com.housewise.feature.dashboard.leads.data.model.AddLeadRequest
import com.housewise.feature.dashboard.leads.data.model.LeadResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class LeadRepository {
    private val api = RetrofitClient.apiService

    suspend fun addLead(request: AddLeadRequest): Flow<Resource<String>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.addLead(request)
            if (response.isSuccessful) {
                emit(Resource.Success("LeadResponse added successfully!"))
            } else {
                emit(Resource.Error(response.message() ?: "Failed to add lead"))
            }
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }

    suspend fun getLeads(): Flow<Resource<List<LeadResponse>>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.getLeads()
            if (response.isSuccessful && response.body() != null) {
                emit(Resource.Success(response.body()!!))
            } else {
                emit(Resource.Error(response.message() ?: "Failed to fetch leads"))
            }
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }

}