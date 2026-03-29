package com.housewise.feature.auth.data.repository

import com.housewise.core.network.RetrofitClient
import com.housewise.core.utils.Resource
import com.housewise.feature.auth.data.model.LoginRequest
import com.housewise.feature.auth.data.model.LoginResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class AuthRepository {
    private val api = RetrofitClient.apiService

    suspend fun login(request: LoginRequest): Flow<Resource<LoginResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.loginUser(request)
            if (response.isSuccessful && response.body() != null) {
                emit(Resource.Success(response.body()!!))
            } else {
                emit(Resource.Error(response.message() ?: "An unknown error occurred"))
            }
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}