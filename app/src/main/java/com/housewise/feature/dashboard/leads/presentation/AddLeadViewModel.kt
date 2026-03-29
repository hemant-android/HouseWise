package com.housewise.feature.dashboard.leads.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.housewise.HousewiseApp
import com.housewise.core.utils.Resource
import com.housewise.feature.dashboard.leads.data.model.AddLeadRequest
import com.housewise.feature.dashboard.leads.data.repository.LeadRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class AddLeadViewModel : ViewModel() {
    private val repository = LeadRepository()

    private val _addLeadState = MutableStateFlow<Resource<String>>(Resource.Idle())
    val addLeadState: StateFlow<Resource<String>> = _addLeadState

    fun submitLead(
        propertyId: String, leadName: String, city: String, phone: String, altPhone: String,
        email: String, location: String, source: String, listingId: String, status: String,
        remarks: String, reminder: String
    ) {
        // 1. Mandatory Field Validation
        if (propertyId.isBlank() || leadName.isBlank() || city.isBlank() ||
            phone.isBlank() || source.isBlank() || listingId.isBlank() ||
            status.isBlank() || remarks.isBlank()
        ) {
            _addLeadState.value = Resource.Error("Please fill in all mandatory fields (*)")
            return
        }

        // 2. Fetch context from SessionManager
        val session = HousewiseApp.sessionManager
        val userId = session.fetchUserId()
        val createdBy = session.fetchFirstName() ?: "admin"

        if (userId == -1) {
            _addLeadState.value = Resource.Error("User session invalid. Please log in again.")
            return
        }

        // 3. Construct Payload
        val request = AddLeadRequest(
            propertyId = propertyId.toIntOrNull() ?: 0,
            userId = userId,
            leadName = leadName,
            leadEmail = email.ifBlank { null },
            leadContactNumber = phone,
            alternateContactNumber = altPhone.ifBlank { null },
            source = source,
            status = status,
            reminderDate = reminder.ifBlank { null }, // Ensure format is YYYY-MM-DD
            city = city,
            listingId = listingId,
            interestedLocation = location.ifBlank { null },
            comments = remarks,
            createdBy = createdBy
        )

        // 4. API Call
        viewModelScope.launch {
            repository.addLead(request).onEach { result ->
                _addLeadState.value = result
            }.launchIn(this)
        }
    }

    fun resetState() {
        _addLeadState.value = Resource.Idle()
    }
}