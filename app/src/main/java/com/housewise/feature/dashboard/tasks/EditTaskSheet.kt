package com.housewise.feature.dashboard.tasks

// Import responsive utils
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.housewise.core.components.HousewiseButton
import com.housewise.core.data.TaskData
import com.housewise.core.theme.HousewiseDarkGreen
import com.housewise.core.theme.HousewiseGreen
import com.housewise.core.utils.sdp
import com.housewise.core.utils.ssp

@Composable
fun EditTaskSheet(
    task: TaskData,
    onCancel: () -> Unit,
    onUpdate: (TaskData) -> Unit
) {
    // 1. STATE VARIABLES - Initializing with task data so it pre-fills the form!
    var priority by remember { mutableStateOf("") }
    var pid by remember { mutableStateOf(task.propertyId) }
    var assignee by remember { mutableStateOf(task.assignee) }
    var type by remember { mutableStateOf("") }
    var description by remember { mutableStateOf(task.title) }
    var dueDate by remember { mutableStateOf(task.dueDate) }
    var status by remember { mutableStateOf(task.status) }
    var tenantName by remember { mutableStateOf("") }
    var tenantPhone by remember { mutableStateOf("") }
    var tenantRemarks by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.9f)
            .background(Color.White)
            .padding(horizontal = 16.sdp)
    ) {
        // Header Section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.sdp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = onCancel) {
                Text(
                    text = "Cancel",
                    color = Color.Gray,
                    fontSize = 16.ssp,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Text(
                text = "Edit Task",
                fontWeight = FontWeight.Medium,
                fontSize = 18.ssp,
                color = Color.Black,
                style = MaterialTheme.typography.titleLarge
            )
            TextButton(
                onClick = {
                    val updatedTask = task.copy(
                        title = description,
                        dueDate = dueDate,
                        status = status,
                        assignee = assignee,
                        propertyId = pid
                    )
                    onUpdate(updatedTask)
                }
            ) {
                Text(
                    text = "Update",
                    color = HousewiseDarkGreen,
                    fontSize = 16.ssp,
                    fontWeight = FontWeight.Medium,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        // Scrollable Form Section
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            // FIXED: Renamed all calls to EditTaskFormField
            EditTaskFormField(
                label = "Priority*",
                value = priority,
                onValueChange = { priority = it },
                placeholder = "Select priority",
                isDropdown = true
            )

            EditTaskFormField(
                label = "PID*",
                value = pid,
                onValueChange = { pid = it },
                placeholder = "Repeat inspection report"
            )

            EditTaskFormField(
                label = "Assignee*",
                value = assignee,
                onValueChange = { assignee = it },
                placeholder = "Select assignee",
                isDropdown = true
            )

            EditTaskFormField(
                label = "Type*",
                value = type,
                onValueChange = { type = it },
                placeholder = "Select task type",
                isDropdown = true
            )

            EditTaskFormField(
                label = "Description",
                value = description,
                onValueChange = { description = it },
                placeholder = "Enter task description",
                isMultiline = true
            )

            EditTaskFormField(
                label = "Due Date*",
                value = dueDate,
                onValueChange = { dueDate = it },
                placeholder = "20 Dec",
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = null,
                        tint = HousewiseDarkGreen,
                        modifier = Modifier.size(20.sdp)
                    )
                }
            )

            EditTaskFormField(
                label = "Current Status*",
                value = status,
                onValueChange = { status = it },
                placeholder = "Select status",
                isDropdown = true
            )

            EditTaskFormField(
                label = "Tenant Name",
                value = tenantName,
                onValueChange = { tenantName = it },
                placeholder = "Full name"
            )

            EditTaskFormField(
                label = "Tenant Phone",
                value = tenantPhone,
                onValueChange = { tenantPhone = it },
                placeholder = "+91"
            )

            EditTaskFormField(
                label = "Tenant Remarks",
                value = tenantRemarks,
                onValueChange = { tenantRemarks = it },
                placeholder = ""
            )

            Spacer(modifier = Modifier.height(16.sdp))
        }

        // Fixed Bottom CTA: "Update and Initiate"
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.sdp)
        ) {
            HousewiseButton(
                text = "Update and Initiate",
                onClick = {
                    val updatedTask = task.copy(
                        title = description,
                        dueDate = dueDate,
                        status = status,
                        assignee = assignee,
                        propertyId = pid
                    )
                    onUpdate(updatedTask)
                }
            )
        }
    }
}

// --- RENAMED TO EditTaskFormField TO PREVENT CONFLICTS ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTaskFormField(
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