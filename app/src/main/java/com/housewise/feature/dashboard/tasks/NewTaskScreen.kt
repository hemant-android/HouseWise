package com.housewise.feature.dashboard.tasks

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import com.housewise.core.components.HousewiseButton
import com.housewise.core.theme.HousewiseDarkGreen
import com.housewise.core.theme.HousewiseGreen
import com.housewise.core.utils.Resource
import com.housewise.core.utils.sdp
import com.housewise.core.utils.ssp
import com.housewise.feature.dashboard.tasks.presentation.NewTaskViewModel

@Composable
fun NewTaskScreen(
    onCancel: () -> Unit,
    onSave: () -> Unit,
    viewModel: NewTaskViewModel = viewModel() // INJECT VIEWMODEL
) {
    val context = LocalContext.current
    val createTaskState by viewModel.createTaskState.collectAsState()

    // Form States
    var priority by remember { mutableStateOf("repeatInspectionReport") }
    var pid by remember { mutableStateOf("301") }
    var assignee by remember { mutableStateOf("Hemant Sharma") }
    var type by remember { mutableStateOf("repeatInspectionReport") }
    var description by remember { mutableStateOf("Period inspection due for property ID\n" +
            "301") }
    var scheduledDate by remember { mutableStateOf("2026-03-03 00:00:00") } // Use YYYY-MM-DD
    var status by remember { mutableStateOf("Complete") }
    var tenantName by remember { mutableStateOf("Anuj") }
    var tenantPhone by remember { mutableStateOf("9876543210") }
    var tenantRemarks by remember { mutableStateOf("123") }

    // Handle API Responses
    LaunchedEffect(createTaskState) {
        when (createTaskState) {
            is Resource.Success -> {
                Toast.makeText(context, "Task Created!", Toast.LENGTH_SHORT).show()
                viewModel.resetState()
                onSave() // Close the bottom sheet
            }

            is Resource.Error -> {
                val msg = (createTaskState as Resource.Error).message
                Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
                viewModel.resetState()
            }

            else -> Unit
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 16.sdp)
                .padding(bottom = 24.sdp)
        ) {
            // 1. Header Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.sdp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = onCancel) {
                    Text(
                        "Cancel",
                        color = Color.Gray,
                        fontSize = 16.ssp,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                Text(
                    "New Task",
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.ssp,
                    color = Color.Black,
                    style = MaterialTheme.typography.titleLarge
                )
                TextButton(
                    onClick = {
                        viewModel.createTask(
                            pid = pid, assignee = assignee, type = type, description = description,
                            scheduledDate = scheduledDate, status = status, tenantName = tenantName,
                            tenantPhone = tenantPhone, remarks = tenantRemarks
                        )
                    }
                ) {
                    Text(
                        "Save",
                        color = HousewiseDarkGreen,
                        fontSize = 16.ssp,
                        fontWeight = FontWeight.Medium,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            // 2. Scrollable Form Fields
            Column(
                modifier = Modifier
                    .weight(1f, fill = false)
                    .verticalScroll(rememberScrollState())
            ) {
                NewTaskFormField(
                    "Priority*",
                    priority,
                    { priority = it },
                    "Select priority",
                    isDropdown = true
                )
                NewTaskFormField("PID*", pid, { pid = it }, "", prefix = "#")
                NewTaskFormField(
                    "Assignee*",
                    assignee,
                    { assignee = it },
                    "Select assignee",
                    isDropdown = true
                )
                NewTaskFormField(
                    "Type*",
                    type,
                    { type = it },
                    "Select task type",
                    isDropdown = true
                )
                NewTaskFormField(
                    "Description",
                    description,
                    { description = it },
                    "Enter task description",
                    isMultiline = true
                )

                NewTaskFormField(
                    label = "Scheduled Date (YYYY-MM-DD)*",
                    value = scheduledDate,
                    onValueChange = { scheduledDate = it },
                    placeholder = "2024-12-20",
                    trailingIcon = {
                        Icon(
                            Icons.Default.DateRange,
                            "Calendar",
                            tint = HousewiseDarkGreen,
                            modifier = Modifier.size(20.sdp)
                        )
                    }
                )

                NewTaskFormField(
                    "Current Status*",
                    status,
                    { status = it },
                    "Select status",
                    isDropdown = true
                )
                NewTaskFormField("Tenant Name", tenantName, { tenantName = it }, "Full name")
                NewTaskFormField("Tenant Phone", tenantPhone, { tenantPhone = it }, "+91")
                NewTaskFormField("Tenant Remarks", tenantRemarks, { tenantRemarks = it }, "")

                Spacer(modifier = Modifier.height(8.sdp))
            }

            // 3. Sticky Bottom Button
            Spacer(modifier = Modifier.height(16.sdp))
            HousewiseButton(
                text = "Save and Initiate",
                onClick = {
                    viewModel.createTask(
                        pid = pid, assignee = assignee, type = type, description = description,
                        scheduledDate = scheduledDate, status = status, tenantName = tenantName,
                        tenantPhone = tenantPhone, remarks = tenantRemarks
                    )
                }
            )
        }

        // 4. Loading Overlay
        if (createTaskState is Resource.Loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White.copy(alpha = 0.6f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = HousewiseGreen)
            }
        }
    }
}

// Renamed to NewTaskFormField to avoid ANY conflicting overloads
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewTaskFormField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isDropdown: Boolean = false,
    isMultiline: Boolean = false,
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: String? = null
) {
    Column(modifier = Modifier.padding(bottom = 16.sdp)) {
        Text(
            text = label,
            fontSize = 14.ssp,
            color = Color.Black,
            fontWeight = FontWeight.Medium,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 8.sdp)
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder, color = Color.LightGray, fontSize = 14.ssp) },
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 14.ssp,
                color = Color.Black
            ),
            modifier = Modifier
                .fillMaxWidth()
                .then(if (isMultiline) Modifier.height(120.sdp) else Modifier.height(54.sdp)),
            shape = RoundedCornerShape(8.sdp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xFFE0E0E0),
                focusedBorderColor = HousewiseGreen
            ),
            trailingIcon = trailingIcon ?: if (isDropdown) {
                {
                    Icon(
                        Icons.Default.KeyboardArrowDown,
                        "Dropdown",
                        tint = Color.Gray,
                        modifier = Modifier.size(24.sdp)
                    )
                }
            } else null,
            leadingIcon = if (prefix != null) {
                {
                    Text(
                        prefix,
                        color = Color.Gray,
                        fontSize = 14.ssp,
                        modifier = Modifier.padding(start = 12.sdp)
                    )
                }
            } else null,
            readOnly = isDropdown,
            singleLine = !isMultiline
        )
    }
}