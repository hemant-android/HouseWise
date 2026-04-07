package com.housewise.feature.dashboard.tasks.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.housewise.core.utils.Resource
import com.housewise.feature.dashboard.tasks.data.model.EditTaskPayload // ADD IMPORT
import com.housewise.feature.dashboard.tasks.data.model.TaskModel
import com.housewise.feature.dashboard.tasks.data.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class TaskDetailsViewModel : ViewModel() {
    private val repository = TaskRepository()

    // State for Fetching details
    private val _taskState = MutableStateFlow<Resource<TaskModel.Response.Succes>>(Resource.Idle())
    val taskState: StateFlow<Resource<TaskModel.Response.Succes>> = _taskState

    // ADDED: State for Editing a task
    private val _editTaskState = MutableStateFlow<Resource<String>>(Resource.Idle())
    val editTaskState: StateFlow<Resource<String>> = _editTaskState

    fun fetchTaskDetails(taskId: String) {
        viewModelScope.launch {
            repository.getTaskDetails(taskId).onEach { result ->
                _taskState.value = result
            }.launchIn(this)
        }
    }

    // ADDED: Function to trigger the edit API
    fun editTask(taskId: String, payload: EditTaskPayload) {
        viewModelScope.launch {
            repository.editTask(taskId, payload).onEach { result ->
                _editTaskState.value = result
            }.launchIn(this)
        }
    }

    // ADDED: Reset edit state after successful toast
    fun resetEditState() {
        _editTaskState.value = Resource.Idle()
    }
}