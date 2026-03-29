package com.housewise.feature.dashboard.tasks.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.housewise.core.utils.Resource
import com.housewise.feature.dashboard.tasks.data.model.TaskModel
import com.housewise.feature.dashboard.tasks.data.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MyTasksViewModel : ViewModel() {
    private val repository = TaskRepository()

    // FIXED: Updated to expect the new 'Succes' data class from your plugin
    private val _tasksState = MutableStateFlow<Resource<List<TaskModel.Response.Succes>>>(Resource.Idle())
    val tasksState: StateFlow<Resource<List<TaskModel.Response.Succes>>> = _tasksState

    init {
        fetchTasks()
    }

    fun fetchTasks() {
        viewModelScope.launch {
            repository.getTasks().onEach { result ->
                _tasksState.value = result
            }.launchIn(this)
        }
    }
}