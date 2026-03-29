package com.housewise.feature.dashboard.tasks.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.housewise.HousewiseApp
import com.housewise.core.utils.Resource
import com.housewise.feature.dashboard.tasks.data.model.CreateTaskPayload
import com.housewise.feature.dashboard.tasks.data.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NewTaskViewModel : ViewModel() {
    private val repository = TaskRepository()

    private val _createTaskState = MutableStateFlow<Resource<String>>(Resource.Idle())
    val createTaskState: StateFlow<Resource<String>> = _createTaskState

    fun createTask(
        pid: String, assignee: String, type: String, description: String,
        scheduledDate: String, status: String, tenantName: String, 
        tenantPhone: String, remarks: String
    ) {
        // 1. Mandatory Field Validation
        if (pid.isBlank() || assignee.isBlank() || type.isBlank() || scheduledDate.isBlank() || status.isBlank()) {
            _createTaskState.value = Resource.Error("Please fill in all mandatory fields (*)")
            return
        }

        // 2. Get User ID from Session
        val userId = HousewiseApp.sessionManager.fetchUserId().toString()

        // 3. Format Date (API expects "YYYY-MM-DD HH:MM:SS". If user enters "20 Dec", we need to format it)
        // For simplicity in this example, we append a default time if they just enter a date.
        val formattedDate = if (scheduledDate.contains(":")) scheduledDate else "$scheduledDate 00:00:00"

        // 4. Construct Payload
        val payload = CreateTaskPayload(
            type = type,
            pid = pid,
            uid = userId,
            description = description,
            scheduledDate = formattedDate,
            assignee = assignee,
            remarks = remarks,
            tenantName = tenantName,
            tenantPhone = tenantPhone,
            status = status
        )

        // 5. Hit API
        viewModelScope.launch {
            repository.createTask(payload).onEach { result ->
                _createTaskState.value = result
            }.launchIn(this)
        }
    }

    fun resetState() {
        _createTaskState.value = Resource.Idle()
    }
}