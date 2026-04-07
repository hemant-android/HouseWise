package com.housewise.feature.dashboard.tasks

// FIXED: Import the real API Task Model
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.housewise.HousewiseApp
import com.housewise.core.components.HousewiseButton
import com.housewise.core.theme.HousewiseDarkGreen
import com.housewise.core.theme.HousewiseGreen
import com.housewise.core.utils.sdp
import com.housewise.core.utils.ssp
import com.housewise.feature.dashboard.tasks.data.model.EditTaskPayload
import com.housewise.feature.dashboard.tasks.data.model.TaskModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTaskScreen(
    task: TaskModel.Response.Succes,
    onCancel: () -> Unit,
    // 1. FIXED: Added a Boolean to tell the parent screen whether to navigate or not!
    onUpdate: (payload: EditTaskPayload, navigateToInitiate: Boolean) -> Unit
) {
    // STATE VARIABLES
    var priority by remember { mutableStateOf(task.type ?: "") }
    var pid by remember { mutableStateOf(task.pid ?: "") }
    var assignee by remember { mutableStateOf(task.assignee ?: "") }
    var type by remember { mutableStateOf(task.type ?: "") }
    var description by remember { mutableStateOf(task.description ?: "") }
    var status by remember { mutableStateOf(task.status ?: "") }
    var tenantName by remember { mutableStateOf(task.tenantName ?: "") }
    var tenantPhone by remember { mutableStateOf(task.tenantPhone ?: "") }
    var tenantRemarks by remember { mutableStateOf(task.remarks ?: "") }

    // Date Logic
    var scheduledDateApi by remember { mutableStateOf(task.scheduledDate ?: "") }
    var scheduledDateDisplay by remember {
        mutableStateOf(
            try {
                if (!task.scheduledDate.isNullOrEmpty()) {
                    val apiFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                    val parsedDate = apiFormat.parse(task.scheduledDate)
                    if (parsedDate != null) {
                        SimpleDateFormat("dd MMM", Locale.getDefault()).format(parsedDate)
                    } else task.scheduledDate.substringBefore(" ")
                } else ""
            } catch (e: Exception) {
                task.scheduledDate?.substringBefore(" ") ?: ""
            }
        )
    }

    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

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
                    "Cancel",
                    color = Color.Gray,
                    fontSize = 16.ssp,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Text(
                "Edit Task",
                fontWeight = FontWeight.Medium,
                fontSize = 18.ssp,
                color = Color.Black,
                style = MaterialTheme.typography.titleLarge
            )
            TextButton(
                onClick = {
                    val payload = EditTaskPayload(
                        type = type,
                        pid = pid,
                        uid = HousewiseApp.sessionManager.fetchUserId().toString(),
                        description = description,
                        scheduledDate = scheduledDateApi,
                        assignee = assignee,
                        status = status,
                        remarks = tenantRemarks,
                        tenantName = tenantName,
                        tenantPhone = tenantPhone,
                        contactName = task.contactName ?: "",
                        contactPhone = task.contactPhone ?: "",
                        vendorName = task.vendorName ?: "",
                        vendorPhone = task.vendorPhone ?: ""
                    )
                    // 2. FIXED: Top right button -> false (Don't navigate)
                    onUpdate(payload, false)
                }
            ) {
                Text(
                    "Update",
                    color = HousewiseDarkGreen,
                    fontSize = 16.ssp,
                    fontWeight = FontWeight.Medium,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        // Scrollable Form Section
        Column(modifier = Modifier
            .weight(1f)
            .verticalScroll(rememberScrollState())) {
            EditTaskFormField("Priority*", priority, { priority = it }, "Enter priority")
            EditTaskFormField("PID*", pid, { pid = it }, "Enter PID", prefix = "#")
            EditTaskFormField("Assignee*", assignee, { assignee = it }, "Enter assignee name")
            EditTaskFormField("Type*", type, { type = it }, "Enter task type")
            EditTaskFormField(
                "Description",
                description,
                { description = it },
                "Enter task description",
                isMultiline = true
            )

            // SCHEDULED DATE WITH CALENDAR
            Box {
                EditTaskFormField(
                    label = "Due Date*", value = scheduledDateDisplay, onValueChange = {},
                    placeholder = "Select Date", isReadOnly = true,
                    trailingIcon = {
                        Icon(
                            Icons.Default.DateRange,
                            null,
                            tint = HousewiseDarkGreen,
                            modifier = Modifier.size(20.sdp)
                        )
                    }
                )
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(Color.Transparent)
                        .clickable { showDatePicker = true })
            }

            EditTaskFormField("Current Status*", status, { status = it }, "Select status")
            EditTaskFormField("Tenant Name", tenantName, { tenantName = it }, "Full name")
            EditTaskFormField("Tenant Phone", tenantPhone, { tenantPhone = it }, "+91")
            EditTaskFormField("Tenant Remarks", tenantRemarks, { tenantRemarks = it }, "")
            Spacer(modifier = Modifier.height(16.sdp))
        }

        // Fixed Bottom CTA
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.sdp)) {
            HousewiseButton(
                text = "Update and Initiate",
                onClick = {
                    val payload = EditTaskPayload(
                        type = type,
                        pid = pid,
                        uid = HousewiseApp.sessionManager.fetchUserId().toString(),
                        description = description,
                        scheduledDate = scheduledDateApi,
                        assignee = assignee,
                        status = status,
                        remarks = tenantRemarks,
                        tenantName = tenantName,
                        tenantPhone = tenantPhone,
                        contactName = task.contactName ?: "",
                        contactPhone = task.contactPhone ?: "",
                        vendorName = task.vendorName ?: "",
                        vendorPhone = task.vendorPhone ?: ""
                    )
                    // 3. FIXED: Bottom CTA button -> true (Navigate to Initiate screen)
                    onUpdate(payload, true)
                }
            )
        }

        // Calendar Dialog Popup
        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        showDatePicker = false
                        datePickerState.selectedDateMillis?.let { millis ->
                            val date = Date(millis)
                            val apiFormatter =
                                SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                            scheduledDateApi = apiFormatter.format(date)
                            val displayFormatter = SimpleDateFormat("dd MMM", Locale.getDefault())
                            scheduledDateDisplay = displayFormatter.format(date)
                        }
                    }) { Text("OK", color = HousewiseDarkGreen) }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text(
                            "Cancel",
                            color = Color.Gray
                        )
                    }
                }
            ) { DatePicker(state = datePickerState) }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTaskFormField(
    label: String, value: String, onValueChange: (String) -> Unit, placeholder: String,
    isDropdown: Boolean = false, isMultiline: Boolean = false, isReadOnly: Boolean = false,
    trailingIcon: @Composable (() -> Unit)? = null, prefix: String? = null
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
            value = value, onValueChange = onValueChange,
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
            readOnly = isDropdown || isReadOnly, singleLine = !isMultiline
        )
    }
}