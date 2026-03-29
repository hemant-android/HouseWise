package com.housewise.feature.dashboard.leads.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.housewise.core.utils.Resource
import com.housewise.feature.dashboard.leads.data.model.LeadResponse
import com.housewise.feature.dashboard.leads.data.repository.LeadRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MyLeadsViewModel : ViewModel() {
    private val repository = LeadRepository()

    private val _leadsState = MutableStateFlow<Resource<List<LeadResponse>>>(Resource.Idle())
    val leadsState: StateFlow<Resource<List<LeadResponse>>> = _leadsState

    init {
        // Fetch leads immediately when the ViewModel is created
        fetchLeads()
    }

    fun fetchLeads() {
        viewModelScope.launch {
            repository.getLeads().onEach { result ->
                _leadsState.value = result
            }.launchIn(this)
        }
    }
}