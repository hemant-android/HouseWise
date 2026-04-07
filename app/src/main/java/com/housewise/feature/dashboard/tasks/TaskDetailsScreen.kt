package com.housewise.feature.dashboard.tasks

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import com.housewise.R
import com.housewise.core.theme.HousewiseDarkGreen
import com.housewise.core.theme.HousewiseGreen
import com.housewise.core.theme.StatusRed
import com.housewise.core.utils.Resource
import com.housewise.core.utils.sdp
import com.housewise.core.utils.ssp
import com.housewise.feature.dashboard.tasks.presentation.TaskDetailsViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailsScreen(
    taskId: String,
    onBackClick: () -> Unit,
    onNavigateToInitiate: () -> Unit,
    viewModel: TaskDetailsViewModel = viewModel()
) {
    val scrollState = rememberScrollState()
    var showEditSheet by remember { mutableStateOf(false) }

    // 1. STATE TO REMEMBER WHICH BUTTON WAS CLICKED
    var navigateAfterUpdate by remember { mutableStateOf(false) }

    val context = LocalContext.current

    // Observe API States
    val taskState by viewModel.taskState.collectAsState()
    val editTaskState by viewModel.editTaskState.collectAsState()

    // Trigger API call when screen opens
    LaunchedEffect(taskId) {
        viewModel.fetchTaskDetails(taskId)
    }

    // 2. HANDLE THE SUCCESS AND NAVIGATION
    LaunchedEffect(editTaskState) {
        when (editTaskState) {
            is Resource.Success -> {
                Toast.makeText(context, "Task Updated Successfully!", Toast.LENGTH_SHORT).show()
                viewModel.resetEditState()
                showEditSheet = false
                viewModel.fetchTaskDetails(taskId) // Always refresh the current screen to show new data

                // Logic: If they clicked "Update and Initiate", navigate!
                if (navigateAfterUpdate) {
                    navigateAfterUpdate = false // Reset
                    onNavigateToInitiate()
                } else {
                    // Logic: If they just clicked "Update", you said you want it to go "back"
                    // If you mean close the sheet and stay on the details screen, do nothing else.
                    // If you mean literally go back to the Task List, uncomment the line below:
                    // onBackClick()
                }
            }

            is Resource.Error -> {
                val msg = (editTaskState as Resource.Error).message
                Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
                viewModel.resetEditState()
            }

            else -> Unit
        }
    }

    val dashedStroke = Stroke(
        width = 3f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "My Tasks",
                        fontSize = 18.ssp,
                        fontWeight = FontWeight.Medium,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.Default.ArrowBackIosNew,
                            "Back",
                            modifier = Modifier.size(24.sdp)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* Share */ }) {
                        Icon(
                            painterResource(id = R.drawable.ic_share),
                            "Share",
                            modifier = Modifier.size(24.sdp)
                        )
                    }
                    if (taskState is Resource.Success) {
                        IconButton(onClick = {
                            showEditSheet = true
                        }) {
                            Icon(
                                painterResource(id = R.drawable.ic_edit_top),
                                "Edit",
                                modifier = Modifier.size(24.sdp)
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
        ) {
            when (taskState) {
                is Resource.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = HousewiseGreen
                    )
                }

                is Resource.Error -> {
                    val errorMsg = (taskState as Resource.Error).message ?: "Error loading task"
                    Text(
                        text = errorMsg,
                        color = StatusRed,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                is Resource.Success -> {
                    val task = (taskState as Resource.Success).data!!

                    // Format Dates safely
                    var shortDate = "N/A"
                    var fullDate = "N/A"
                    try {
                        val apiFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                        val parsedDate = apiFormat.parse(task.scheduledDate ?: "")
                        if (parsedDate != null) {
                            shortDate =
                                SimpleDateFormat("dd MMM", Locale.getDefault()).format(parsedDate)
                            fullDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(
                                parsedDate
                            )
                        }
                    } catch (e: Exception) {
                        shortDate = task.scheduledDate ?: "N/A"
                    }

                    // Extract Initials
                    val assigneeInitials =
                        task.assignee?.split(" ")?.mapNotNull { it.firstOrNull()?.toString() }
                            ?.take(2)?.joinToString("")?.uppercase() ?: "U"
                    val tenantInitials =
                        task.tenantName?.split(" ")?.mapNotNull { it.firstOrNull()?.toString() }
                            ?.take(2)?.joinToString("")?.uppercase() ?: "U"

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                            .padding(horizontal = 16.sdp, vertical = 24.sdp)
                    ) {
                        // 1. Header Section
                        Text(
                            text = task.description ?: task.type ?: "Task Detail",
                            fontSize = 22.ssp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black,
                            style = MaterialTheme.typography.bodyLarge
                        )

                        Spacer(modifier = Modifier.height(24.sdp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            // Assigned To
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(44.sdp)
                                        .background(HousewiseGreen, CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        assigneeInitials,
                                        color = Color.White,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 16.ssp
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.sdp))
                                Column {
                                    Text(
                                        "Assigned to",
                                        color = Color.Gray,
                                        fontSize = 12.ssp,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    Spacer(modifier = Modifier.height(2.sdp))
                                    Text(
                                        task.assignee ?: "Unassigned",
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 13.ssp,
                                        style = MaterialTheme.typography.titleMedium,
                                        color = Color.Black
                                    )
                                }
                            }

                            // Due Date
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(44.sdp)
                                        .drawBehind {
                                            drawRoundRect(
                                                Color.LightGray,
                                                style = dashedStroke,
                                                cornerRadius = CornerRadius(100f)
                                            )
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        painterResource(id = R.drawable.ic_calendar_due),
                                        contentDescription = null,
                                        tint = HousewiseDarkGreen,
                                        modifier = Modifier.size(20.sdp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.sdp))
                                Column {
                                    Text(
                                        "Due date",
                                        color = Color.Gray,
                                        fontSize = 12.ssp,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    Spacer(modifier = Modifier.height(2.sdp))
                                    Text(
                                        shortDate,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 13.ssp,
                                        style = MaterialTheme.typography.titleMedium,
                                        color = Color.Black
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(32.sdp))

                        // 2. Main Info Card
                        OutlinedCard(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.sdp),
                            colors = CardDefaults.outlinedCardColors(containerColor = Color.White),
                            border = BorderStroke(1.sdp, Color(0xFFEEEEEE))
                        ) {
                            Column(modifier = Modifier.padding(16.sdp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Column {
                                        Text(
                                            "Property ID",
                                            color = Color.Gray,
                                            fontSize = 12.ssp,
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                        Spacer(modifier = Modifier.height(8.sdp))
                                        Box(
                                            modifier = Modifier
                                                .border(
                                                    1.sdp,
                                                    Color(0xFF26C6DA),
                                                    RoundedCornerShape(8.sdp)
                                                )
                                                .padding(horizontal = 12.sdp, vertical = 8.sdp)
                                        ) {
                                            Text(
                                                "ID #${task.pid ?: "N/A"}",
                                                color = Color.Black,
                                                fontSize = 14.ssp,
                                                style = MaterialTheme.typography.titleMedium
                                            )
                                        }
                                    }
                                    Column(horizontalAlignment = Alignment.End) {
                                        Text(
                                            "Status",
                                            color = Color.Gray,
                                            fontSize = 12.ssp,
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                        Spacer(modifier = Modifier.height(8.sdp))
                                        Surface(
                                            color = HousewiseGreen,
                                            shape = RoundedCornerShape(6.sdp)
                                        ) {
                                            Text(
                                                text = task.status ?: "New",
                                                color = Color.White,
                                                fontSize = 12.ssp,
                                                fontWeight = FontWeight.Medium,
                                                modifier = Modifier.padding(
                                                    horizontal = 12.sdp,
                                                    vertical = 2.sdp
                                                )
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(24.sdp))

                                Text(
                                    "Due Date",
                                    color = Color.Gray,
                                    fontSize = 12.ssp,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Spacer(modifier = Modifier.height(4.sdp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        painterResource(id = R.drawable.ic_calendar_filter_by),
                                        null,
                                        tint = Color.Gray,
                                        modifier = Modifier.size(16.sdp)
                                    )
                                    Spacer(modifier = Modifier.width(8.sdp))
                                    Text(
                                        fullDate,
                                        fontSize = 14.ssp,
                                        color = Color.Black,
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.sdp))

                        // 3. Tenant Details Card
                        OutlinedCard(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.sdp),
                            colors = CardDefaults.outlinedCardColors(containerColor = Color.White),
                            border = BorderStroke(1.sdp, Color(0xFFEEEEEE))
                        ) {
                            Column(modifier = Modifier.padding(16.sdp)) {
                                Text(
                                    "Tenant & Property Details",
                                    fontSize = 16.ssp,
                                    style = MaterialTheme.typography.headlineLarge,
                                    color = Color.Black
                                )
                                Spacer(modifier = Modifier.height(16.sdp))

                                OutlinedCard(
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.sdp),
                                    border = BorderStroke(1.sdp, Color(0xFFEEEEEE)),
                                    colors = CardDefaults.outlinedCardColors(containerColor = Color.White)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .padding(12.sdp)
                                            .fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(40.sdp)
                                                .background(Color(0xFF26C6DA), CircleShape),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                tenantInitials,
                                                color = Color.White,
                                                fontWeight = FontWeight.Medium,
                                                fontSize = 16.ssp
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(12.sdp))
                                        Text(
                                            task.tenantName?.takeIf { it.isNotBlank() } ?: "N/A",
                                            color = if (task.tenantName.isNullOrBlank()) Color.Gray else Color.Black,
                                            fontSize = 14.ssp,
                                            fontStyle = if (task.tenantName.isNullOrBlank()) FontStyle.Italic else FontStyle.Normal,
                                            style = MaterialTheme.typography.bodyLarge,
                                            modifier = Modifier.weight(1f)
                                        )

                                        if (!task.tenantPhone.isNullOrBlank()) {
                                            Icon(
                                                painterResource(id = R.drawable.ic_call_small),
                                                contentDescription = "Call",
                                                tint = Color(0xFF26C6DA),
                                                modifier = Modifier
                                                    .size(24.sdp)
                                                    .clickable {
                                                        val intent =
                                                            Intent(Intent.ACTION_DIAL).apply {
                                                                data =
                                                                    Uri.parse("tel:${task.tenantPhone}")
                                                            }
                                                        context.startActivity(intent)
                                                    }
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(20.sdp))
                                Text(
                                    "Remarks",
                                    color = Color.Gray,
                                    fontSize = 12.ssp,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Spacer(modifier = Modifier.height(8.sdp))
                                TaskDetailTextField(
                                    placeholder = "Enter apartment details",
                                    initialValue = task.remarks ?: ""
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.sdp))

                        // 4. Other Details
                        OutlinedCard(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.sdp),
                            colors = CardDefaults.outlinedCardColors(containerColor = Color.White),
                            border = BorderStroke(1.sdp, Color(0xFFEEEEEE))
                        ) {
                            Column(modifier = Modifier.padding(16.sdp)) {
                                Text(
                                    "Other Details",
                                    fontSize = 16.ssp,
                                    style = MaterialTheme.typography.headlineLarge,
                                    color = Color.Black
                                )
                                Spacer(modifier = Modifier.height(16.sdp))

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        painterResource(id = R.drawable.ic_add_people),
                                        null,
                                        modifier = Modifier.size(16.sdp),
                                        tint = Color.Gray
                                    )
                                    Spacer(modifier = Modifier.width(6.sdp))
                                    Text(
                                        "Contact Name",
                                        color = Color.Gray,
                                        fontSize = 12.ssp,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                                Spacer(modifier = Modifier.height(6.sdp))
                                Text(task.contactName?.takeIf { it.isNotBlank() } ?: "N/A",
                                    fontSize = 16.ssp,
                                    color = Color.Black,
                                    style = MaterialTheme.typography.bodyLarge)

                                Spacer(modifier = Modifier.height(20.sdp))
                                Text(
                                    "Vendor Details",
                                    color = Color.Gray,
                                    fontSize = 12.ssp,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Spacer(modifier = Modifier.height(8.sdp))
                                TaskDetailTextField(
                                    placeholder = "Enter HW OPM",
                                    initialValue = task.vendorName ?: ""
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(32.sdp))
                    } // End of Column

                    // Bottom Sheet
                    if (showEditSheet) {
                        ModalBottomSheet(
                            onDismissRequest = { showEditSheet = false },
                            containerColor = Color.White,
                            dragHandle = null,
                            shape = RoundedCornerShape(topStart = 24.sdp, topEnd = 24.sdp)
                        ) {
                            EditTaskScreen(
                                task = task,
                                onCancel = { showEditSheet = false },
                                onUpdate = { payload, navigateToInitiate ->
                                    // 3. CAPTURE THE BOOLEAN FLAG HERE
                                    navigateAfterUpdate = navigateToInitiate
                                    viewModel.editTask(taskId = task.id ?: "", payload = payload)
                                }
                            )
                        }
                    }
                } // End of Resource.Success

                else -> Unit
            }

            // Show loading overlay when edit API is running
            if (editTaskState is Resource.Loading) {
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
}

// Custom Text Field Composable
@Composable
fun TaskDetailTextField(placeholder: String, initialValue: String = "") {
    var textState by remember { mutableStateOf(initialValue) }
    var isFocused by remember { mutableStateOf(false) }

    BasicTextField(
        value = textState, onValueChange = { textState = it },
        textStyle = MaterialTheme.typography.bodyLarge.copy(fontSize = 14.ssp, color = Color.Black),
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .height(36.sdp)
            .onFocusChanged { isFocused = it.isFocused },
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .border(
                        width = if (isFocused) 1.5.sdp else 1.sdp,
                        color = if (isFocused) HousewiseGreen else Color(0xFFEEEEEE),
                        shape = RoundedCornerShape(12.sdp)
                    )
                    .background(Color.White, RoundedCornerShape(12.sdp))
                    .padding(horizontal = 12.sdp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    if (textState.isEmpty()) Text(
                        placeholder,
                        color = Color.LightGray,
                        fontSize = 14.ssp,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    innerTextField()
                }
            }
        }
    )
}