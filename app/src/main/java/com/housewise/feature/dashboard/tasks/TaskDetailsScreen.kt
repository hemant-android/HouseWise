package com.housewise.feature.dashboard.tasks

// Import responsive utils
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.housewise.R
import com.housewise.core.data.TaskData
import com.housewise.core.theme.HousewiseDarkGreen
import com.housewise.core.theme.HousewiseGreen
import com.housewise.core.utils.sdp
import com.housewise.core.utils.ssp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailsScreen(task: TaskData, onBackClick: () -> Unit, onNavigateToInitiate: () -> Unit) {
    val scrollState = rememberScrollState()
    var showEditSheet by remember { mutableStateOf(false) }

    // Dashed border style for the calendar icon
    val dashedStroke = Stroke(
        width = 3f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "My Tasks",
                        fontSize = 18.ssp, // Scalable text
                        fontWeight = FontWeight.Medium,
                        style = MaterialTheme.typography.titleLarge // Uses Poppins
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.Default.ArrowBackIosNew,
                            contentDescription = "Back",
                            modifier = Modifier.size(24.sdp)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* Share */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_share),
                            contentDescription = "Share",
                            modifier = Modifier.size(24.sdp)
                        )
                    }
                    IconButton(onClick = { showEditSheet = true }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_edit_top),
                            contentDescription = "Edit",
                            modifier = Modifier.size(24.sdp)
                        )
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
                .padding(paddingValues) // Corrected: Padding only applied once here
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(horizontal = 16.sdp, vertical = 24.sdp) // Responsive padding
            ) {
                // 1. Header Section
                Text(
                    text = "Repeat inspection report",
                    fontSize = 22.ssp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyLarge // Uses Poppins
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
                                .size(44.sdp) // Responsive Avatar
                                .background(HousewiseGreen, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "KT",
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
                                "Kushagra Singh Tanwar",
                                fontWeight = FontWeight.Medium,
                                fontSize = 13.ssp,
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.Black
                            )
                        }
                    }

                    // Due Date with Dashed Border
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(44.sdp)
                                .drawBehind { // Custom dashed border matching the mockup
                                    drawRoundRect(
                                        color = Color.LightGray,
                                        style = dashedStroke,
                                        cornerRadius = CornerRadius(100f)
                                    )
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_calendar_due), // Ensure this is a dark green icon
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
                                "22 Mar",
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
                                    fontWeight = FontWeight.W400,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Spacer(modifier = Modifier.height(8.sdp))
                                Box(
                                    modifier = Modifier
                                        .border(
                                            1.sdp,
                                            Color(0xFF26C6DA),
                                            RoundedCornerShape(8.sdp)
                                        ) // Cyan/teal border
                                        .padding(horizontal = 12.sdp, vertical = 8.sdp)
                                ) {
                                    Text(
                                        "ID #4092",
                                        color = Color.Black,
                                        fontWeight = FontWeight.W400,
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
                                    fontWeight = FontWeight.W400,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Spacer(modifier = Modifier.height(8.sdp))
                                // Replaced Material Badge with custom Surface for better text scaling
                                Surface(
                                    color = HousewiseGreen,
                                    shape = RoundedCornerShape(6.sdp)
                                ) {
                                    Text(
                                        text = "New",
                                        color = Color.White,
                                        fontSize = 12.ssp,
                                        fontWeight = FontWeight.Medium,
                                        style = MaterialTheme.typography.labelMedium,
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
                                painter = painterResource(id = R.drawable.ic_calendar_filter_by), // Small calendar icon
                                contentDescription = null,
                                tint = Color.Gray,
                                modifier = Modifier.size(16.sdp)
                            )
                            Spacer(modifier = Modifier.width(8.sdp))
                            Text(
                                "22/03/2025",
                                fontSize = 14.ssp,
                                color = Color.Black,
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Spacer(modifier = Modifier.width(8.sdp))
                            Icon(
                                painter = painterResource(id = R.drawable.ic_edit_small),
                                contentDescription = "Edit",
                                modifier = Modifier.size(14.sdp),
                                tint = Color.Gray
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.sdp))

                // 3. Tenant & Property Details Card
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
                            fontWeight = FontWeight.Normal,
                            style = MaterialTheme.typography.headlineLarge,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(16.sdp))

                        // Tenant Box
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
                                        .background(
                                            Color(0xFF26C6DA),
                                            CircleShape
                                        ), // Cyan to match 'N' avatar
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        "N",
                                        color = Color.White,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 16.ssp
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.sdp))
                                Text(
                                    "N/A",
                                    color = Color.Gray,
                                    fontSize = 14.ssp,
                                    fontStyle = FontStyle.Italic,
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.weight(1f)
                                )
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_call_small),
                                    contentDescription = "Call",
                                    tint = Color(0xFF26C6DA), // Cyan
                                    modifier = Modifier.size(24.sdp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(20.sdp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_location_small),
                                contentDescription = null,
                                tint = Color.Gray,
                                modifier = Modifier.size(16.sdp)
                            )
                            Spacer(modifier = Modifier.width(6.sdp))
                            Text(
                                "Property Address",
                                color = Color.Gray,
                                fontSize = 12.ssp,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        Spacer(modifier = Modifier.height(6.sdp))
                        Text(
                            "Sample house for testing, Perumbakkam, 600092",
                            fontSize = 14.ssp,
                            color = Color.Black,
                            style = MaterialTheme.typography.bodyLarge
                        )

                        Spacer(modifier = Modifier.height(20.sdp))

                        Text(
                            "Apartment",
                            color = Color.Gray,
                            fontSize = 12.ssp,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(8.sdp))
                        TaskDetailTextField(placeholder = "Enter apartment details")
                    }
                }

                Spacer(modifier = Modifier.height(16.sdp))

                // 4. Other Details Card
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
                            fontWeight = FontWeight.Normal,
                            style = MaterialTheme.typography.headlineLarge,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(16.sdp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_add_people), // Replace with user outline icon
                                contentDescription = null,
                                modifier = Modifier.size(16.sdp),
                                tint = Color.Gray
                            )
                            Spacer(modifier = Modifier.width(6.sdp))
                            Text(
                                "Owner Name",
                                color = Color.Gray,
                                fontSize = 12.ssp,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        Spacer(modifier = Modifier.height(6.sdp))
                        Text(
                            "Ms. Ramya Venkat",
                            fontSize = 16.ssp,
                            color = Color.Black,
                            style = MaterialTheme.typography.bodyLarge
                        )

                        Spacer(modifier = Modifier.height(20.sdp))

                        Text(
                            "HW OPM",
                            color = Color.Gray,
                            fontSize = 12.ssp,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(8.sdp))
                        // FIXED: Replaced OutlinedTextField with custom BasicTextField
                        TaskDetailTextField(placeholder = "Enter HW OPM")
                    }
                }

                Spacer(modifier = Modifier.height(32.sdp))
            }

            // Edit Bottom Sheet
            if (showEditSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showEditSheet = false },
                    containerColor = Color.White,
                    dragHandle = null,
                    shape = RoundedCornerShape(topStart = 24.sdp, topEnd = 24.sdp)
                ) {
                    EditTaskSheet (
                        task = task,
                        onCancel = { showEditSheet = false },
                        onUpdate = {
                            showEditSheet = false
                            onNavigateToInitiate()
                        }
                    )
                }
            }
        }
    }
}

// --- CUSTOM TEXT FIELD COMPONENT ---
// Ensures text does not clip at heights below 48dp
@Composable
fun TaskDetailTextField(
    placeholder: String,
    initialValue: String = ""
) {
    var textState by remember { mutableStateOf(initialValue) }
    var isFocused by remember { mutableStateOf(false) }

    BasicTextField(
        value = textState,
        onValueChange = { textState = it },
        textStyle = MaterialTheme.typography.bodyLarge.copy(
            fontSize = 14.ssp,
            color = Color.Black
        ),
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .height(36.sdp) // The slim height requested
            .onFocusChanged { isFocused = it.isFocused },
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .border(
                        width = if (isFocused) 1.5.sdp else 1.sdp,
                        color = if (isFocused) HousewiseGreen else Color(0xFFEEEEEE),
                        shape = RoundedCornerShape(12.sdp) // Match design
                    )
                    .background(Color.White, RoundedCornerShape(12.sdp))
                    .padding(horizontal = 12.sdp), // Pure horizontal padding, NO vertical padding
                verticalAlignment = Alignment.CenterVertically // perfectly centers text vertically
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    if (textState.isEmpty()) {
                        Text(
                            text = placeholder,
                            color = Color.LightGray,
                            fontSize = 14.ssp,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    innerTextField()
                }
            }
        }
    )
}