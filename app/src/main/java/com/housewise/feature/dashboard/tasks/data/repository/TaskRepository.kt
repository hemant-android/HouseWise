package com.housewise.feature.dashboard.tasks.data.repository

import com.google.gson.Gson
import com.housewise.HousewiseApp
import com.housewise.core.network.RetrofitClient
import com.housewise.core.network.model.HWRequest
import com.housewise.core.utils.Resource
import com.housewise.feature.dashboard.tasks.data.model.CreateTaskPayload
import com.housewise.feature.dashboard.tasks.data.model.EditTaskPayload
import com.housewise.feature.dashboard.tasks.data.model.TaskModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class TaskRepository {
    private val api = RetrofitClient.apiService
    private val gson = Gson()

    suspend fun createTask(payload: CreateTaskPayload): Flow<Resource<String>> = flow {
        emit(Resource.Loading())
        try {
            // 1. Convert the payload object into a JSON String
            val stringifiedBody = gson.toJson(payload)
            val token = HousewiseApp.sessionManager.fetchAuthToken() ?: ""
            // 2. Wrap it in the HWRequest
            val request = HWRequest(
                method = "POST",
                urlPath = "create_task",
                body = stringifiedBody,
                header = mapOf(
                    "Content-Type" to "application/json",
                    "x-api-key" to "Sagar@12",
                )
            )

            // 3. Send via Proxy
            val response = api.proxyRequest(request)
            val innerResponse = response.body()?.response
            // 4. FIXED: Check for BOTH the old format AND the new format
            val isSuccess = innerResponse?.status == "Success" || innerResponse?.success != null

            if (response.isSuccessful && isSuccess) {
                emit(Resource.Success("Task created successfully!"))
            } else {
                emit(Resource.Error("Failed to create task"))
            }
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Unexpected error"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check internet connection."))
        }
    }

    // FIXED: Changed Flow<Resource<List<TaskModel>>> to Flow<Resource<List<TaskModel.Response.Succes>>>
    suspend fun getTasks(): Flow<Resource<List<TaskModel.Response.Succes>>> = flow {
        emit(Resource.Loading())
        try {
            val userId = HousewiseApp.sessionManager.fetchUserId()

            // Wrap the GET request for the Proxy
            val request = HWRequest(
                method = "GET",
                urlPath = "get_tasks/$userId", // Dynamically adds the User ID
                body = null,                   // Explicitly set to null
                params = emptyMap(),           // Translates to "params": {}
                header = mapOf(
                    "Content-Type" to "application/json",
                    "x-api-key" to "Sagar@12",
                )
            )

            val response = api.proxyRequest(request)

            if (response.isSuccessful && response.body() != null) {
                // 1. Convert the generic proxy response back to a JSON string
                val jsonString = gson.toJson(response.body())

                // 2. Parse it perfectly into your newly generated TaskModel
                val taskModelResponse = gson.fromJson(jsonString, TaskModel::class.java)

                // 3. Extract the list of tasks, filtering out any null items just in case
                val tasksList = taskModelResponse.response?.success?.filterNotNull() ?: emptyList()

                // 4. Now this emit perfectly matches the return type!
                emit(Resource.Success(tasksList))
            } else {
                emit(Resource.Error("Failed to fetch tasks"))
            }
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Unexpected error"))
        } catch (e: Exception) {
            emit(Resource.Error("Couldn't reach server or parse data."))
        }
    }

    suspend fun getTaskDetails(taskId: String): Flow<Resource<TaskModel.Response.Succes>> = flow {
        emit(Resource.Loading())
        try {

            val request = HWRequest(
                method = "GET",
                urlPath = "get_task/$taskId",
                body = null,
                params = emptyMap(),
                header = mapOf(
                    "Content-Type" to "application/json",
                    "x-api-key" to "Sagar@12",
                )
            )

            val response = api.proxyRequest(request)

            if (response.isSuccessful && response.body() != null) {
                val jsonString = gson.toJson(response.body())
                val taskModelResponse = gson.fromJson(jsonString, TaskModel::class.java)

                // The API returns the single task inside an array, so we grab the first item
                val taskDetails = taskModelResponse.response?.success?.firstOrNull()

                if (taskDetails != null) {
                    emit(Resource.Success(taskDetails))
                } else {
                    emit(Resource.Error("Task details not found"))
                }
            } else {
                emit(Resource.Error("Failed to fetch task details"))
            }
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Unexpected error"))
        } catch (e: Exception) {
            emit(Resource.Error("Couldn't reach server or parse data."))
        }
    }

    // Add this inside TaskRepository.kt
    suspend fun editTask(taskId: String, payload: EditTaskPayload): Flow<Resource<String>> = flow {
        emit(Resource.Loading())
        try {
            val stringifiedBody = gson.toJson(payload)

            // Wrap it perfectly for the Proxy
            val request = HWRequest(
                method = "POST",
                urlPath = "edit_task/$taskId", // Appends the ID dynamically
                body = stringifiedBody,
                params = emptyMap(),
                header = mapOf(
                    "Content-Type" to "application/json",
                    "x-api-key" to "Sagar@12"
                )
            )

            val response = api.proxyRequest(request)
            val innerResponse = response.body()?.response

            // Checking both formats just to be safe
            val isSuccess = innerResponse?.status == "Success" || innerResponse?.success != null

            if (response.isSuccessful && isSuccess) {
                emit(Resource.Success("Task updated successfully!"))
            } else {
                emit(Resource.Error("Failed to update task"))
            }
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Unexpected error"))
        } catch (e: Exception) {
            emit(Resource.Error("Couldn't reach server. Check internet connection."))
        }
    }
}