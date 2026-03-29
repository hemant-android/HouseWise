package com.housewise.feature.dashboard.tasks

import android.os.Build
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.housewise.R
import com.housewise.core.theme.BackgroundLight
import com.housewise.core.theme.HousewiseGreen
import com.housewise.core.theme.StatusGray
import com.housewise.core.theme.StatusRed
import com.housewise.core.theme.TextPrimary
import com.housewise.core.utils.Resource
import com.housewise.core.utils.sdp
import com.housewise.core.utils.ssp
import com.housewise.feature.dashboard.tasks.presentation.MyTasksViewModel
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTasksScreen(
    onNavigateToTaskDetails: () -> Unit,
    onFilterClick: () -> Unit,
    viewModel: MyTasksViewModel = viewModel() // INJECT VIEWMODEL
) {
    // 1. Dynamic State using real dates
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var showCalendarPopup by remember { mutableStateOf(false) }

    // Observe API State
    val tasksState by viewModel.tasksState.collectAsState()

    val ribbonDates = remember(selectedDate) {
        val today = LocalDate.now()
        val baseDate =
            if (kotlin.math.abs(today.toEpochDay() - selectedDate.toEpochDay()) > 7) selectedDate else today
        (-2..5).map { baseDate.plusDays(it.toLong()) }
    }

    val monthFormatter = DateTimeFormatter.ofPattern("MMM", Locale.getDefault())
    val displayFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.getDefault())

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundLight)
                .padding(16.sdp)
        ) {
            // Search Bar
            OutlinedTextField(
                value = "",
                onValueChange = {},
                placeholder = { Text("Search tasks(86)", color = Color.Gray, fontSize = 14.ssp) },
                leadingIcon = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(start = 16.sdp)
                    ) {
                        Icon(
                            painterResource(id = R.drawable.ic_search),
                            "Search",
                            tint = Color.Gray,
                            modifier = Modifier.size(25.sdp)
                        )
                        Spacer(modifier = Modifier.width(12.sdp))
                        Box(
                            modifier = Modifier
                                .width(1.sdp)
                                .height(36.sdp)
                                .background(Color(0xFFEEEEEE))
                        )
                        Spacer(modifier = Modifier.width(12.sdp))
                    }
                },
                trailingIcon = {
                    IconButton(onClick = onFilterClick, modifier = Modifier.padding(end = 8.sdp)) {
                        Icon(
                            painterResource(id = R.drawable.ic_filter),
                            "Filter",
                            tint = Color.DarkGray,
                            modifier = Modifier.size(25.sdp)
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.sdp),
                shape = RoundedCornerShape(12.sdp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White, unfocusedContainerColor = Color.White,
                    focusedBorderColor = Color(0xFFE0E0E0), unfocusedBorderColor = Color(0xFFE0E0E0)
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(24.sdp))

            // Ribbon Date Selector
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.sdp)
            ) {
                items(ribbonDates) { date ->
                    val isSelected = date == selectedDate
                    Card(
                        shape = RoundedCornerShape(12.sdp),
                        colors = CardDefaults.cardColors(containerColor = if (isSelected) Color.White else Color.Transparent),
                        border = if (isSelected) BorderStroke(1.sdp, HousewiseGreen) else null,
                        modifier = Modifier
                            .width(64.sdp)
                            .clickable {
                                if (isSelected) showCalendarPopup =
                                    !showCalendarPopup else selectedDate = date
                            }
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(vertical = 12.sdp)
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                date.dayOfMonth.toString(),
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 18.ssp,
                                color = if (isSelected) Color.Black else Color.LightGray
                            )
                            Text(
                                date.format(monthFormatter),
                                fontSize = 16.ssp,
                                color = if (isSelected) Color.Black else Color.LightGray
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.sdp))

            // Task List API Integration
            Box(modifier = Modifier
                .weight(1f)
                .fillMaxWidth()) {
                when (tasksState) {
                    is Resource.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                            color = HousewiseGreen
                        )
                    }

                    is Resource.Error -> {
                        val error = (tasksState as Resource.Error).message
                        Text(
                            text = error ?: "Error loading tasks",
                            color = StatusRed,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    is Resource.Success -> {
                        // FIXED: Uses the new 'Succes' list
                        val filteredTasks = (tasksState as Resource.Success).data ?: emptyList()

                        // Filter tasks locally to only show tasks matching the selectedDate
//                        val filteredTasks = allTasks.filter { task ->
//                            val dateString = task.scheduledDate?.substringBefore(" ") // Extract "YYYY-MM-DD"
//                            if (dateString != null) {
//                                try {
//                                    LocalDate.parse(dateString) == selectedDate
//                                } catch (e: Exception) { false }
//                            } else false
//                        }

                        if (filteredTasks.isEmpty()) {
                            Text(
                                text = "No tasks for ${selectedDate.format(displayFormatter)}",
                                color = Color.Gray,
                                fontSize = 14.ssp,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        } else {
                            LazyColumn(contentPadding = PaddingValues(bottom = 80.sdp)) {
                                items(filteredTasks) { task ->
                                    val status = task.status ?: "New"
                                    val isFaded = status.equals("Complete", ignoreCase = true) || status.equals("Completed", ignoreCase = true)
                                    val statusColor = when (status.lowercase()) {
                                        "complete", "completed" -> StatusGray
                                        "on-hold" -> StatusRed
                                        "new", "active" -> HousewiseGreen
                                        else -> Color.Gray
                                    }

                                    val formattedDate = try {
                                        LocalDate.parse(task.scheduledDate?.substringBefore(" ")).format(displayFormatter)
                                    } catch (e: Exception) {
                                        task.scheduledDate ?: "N/A"
                                    }

                                    TaskCard(
                                        title = task.description ?: task.type ?: "Task",
                                        id = "#${task.id}",
                                        date = formattedDate,
                                        location = "PID: ${task.pid}",
                                        status = status,
                                        statusColor = statusColor,
                                        isFaded = isFaded,
                                        onViewDetailsClick = onNavigateToTaskDetails // Next step: pass task data here!
                                    )
                                }
                            }
                        }
                    }

                    else -> Unit
                }
            }
        }

        // --- CALENDAR POPUP REMAINS UNCHANGED ---
        if (showCalendarPopup) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f))
                    .clickable { showCalendarPopup = false }
            )
            Popup(
                alignment = Alignment.TopCenter,
                onDismissRequest = { showCalendarPopup = false },
                properties = PopupProperties(focusable = true)
            ) {
                Box(
                    modifier = Modifier
                        .padding(top = 180.sdp, start = 16.sdp, end = 16.sdp)
                        .fillMaxWidth()
                        .shadow(16.sdp, RoundedCornerShape(16.sdp))
                        .background(Color.White, RoundedCornerShape(16.sdp))
                        .padding(16.sdp)
                ) {
                    DynamicCalendarView(initialDate = selectedDate, onDateSelected = { newDate ->
                        selectedDate = newDate
                        showCalendarPopup = false
                    })
                }
            }
        }
    }
}

// Keep your exact DynamicCalendarView and TaskCard here!

// --- FULLY DYNAMIC CALENDAR COMPOSABLE ---
@Composable
fun DynamicCalendarView(initialDate: LocalDate, onDateSelected: (LocalDate) -> Unit) {
    // Track the currently displayed month in the calendar (can change via arrows)
    var currentDisplayMonth by remember { mutableStateOf(YearMonth.from(initialDate)) }
    val today = LocalDate.now()

    Column {
        // Month & Year Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.sdp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val monthName =
                currentDisplayMonth.month.getDisplayName(TextStyle.FULL, Locale.getDefault())
            Text(
                text = "$monthName ${currentDisplayMonth.year}",
                fontSize = 16.ssp,
                fontWeight = FontWeight.Medium,
                color = TextPrimary,
                style = MaterialTheme.typography.titleMedium
            )
            Row {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = "Previous Month",
                    modifier = Modifier
                        .size(24.sdp)
                        .clickable {
                            currentDisplayMonth = currentDisplayMonth.minusMonths(1)
                        }
                )
                Spacer(modifier = Modifier.width(16.sdp))
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "Next Month",
                    modifier = Modifier
                        .size(24.sdp)
                        .clickable {
                            currentDisplayMonth = currentDisplayMonth.plusMonths(1)
                        }
                )
            }
        }

        // Days of Week Header
        val daysOfWeek = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            daysOfWeek.forEach { day ->
                Text(
                    text = day,
                    fontSize = 13.ssp,
                    color = Color.Black,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Spacer(modifier = Modifier.height(12.sdp))

        // Dynamic Grid Calculation
        val firstDayOfMonth = currentDisplayMonth.atDay(1)
        val daysInMonth = currentDisplayMonth.lengthOfMonth()

        // Java time sets Monday=1, Sunday=7. We want Sunday=0, Saturday=6 for typical UI
        val offset =
            if (firstDayOfMonth.dayOfWeek.value == 7) 0 else firstDayOfMonth.dayOfWeek.value

        // Calculate weeks needed (usually 5 or 6)
        val totalCells = offset + daysInMonth
        val numWeeks = kotlin.math.ceil(totalCells / 7.0).toInt()

        // Generate Grid
        for (week in 0 until numWeeks) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.sdp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for (dayOfWeek in 0..6) {
                    val cellIndex = (week * 7) + dayOfWeek
                    val dayOfMonth = cellIndex - offset + 1

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        // Check if this cell actually holds a valid date for this month
                        if (dayOfMonth in 1..daysInMonth) {
                            val thisDate = currentDisplayMonth.atDay(dayOfMonth)
                            val isSelected = thisDate == initialDate
                            val isToday = thisDate == today

                            Box(
                                modifier = Modifier
                                    .size(36.sdp)
                                    .background(
                                        if (isSelected) Color(0xFF004D40) else Color.Transparent,
                                        CircleShape
                                    )
                                    .clickable { onDateSelected(thisDate) },
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = dayOfMonth.toString(),
                                        fontSize = 14.ssp,
                                        color = if (isSelected) Color.White
                                        else if (isToday) Color(0xFF00ACC1) // Teal color for "Today"
                                        else Color.Gray,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                    )

                                    // Randomly assign a dot to some days for visual flavor (or tie this to real task data later)
                                    val hasTasks =
                                        dayOfMonth % 5 == 0 // Mock logic: every 5th day has tasks
                                    if (hasTasks || isSelected) {
                                        Spacer(modifier = Modifier.height(2.sdp))
                                        Box(
                                            modifier = Modifier
                                                .size(4.sdp)
                                                .background(
                                                    if (isSelected) Color.White else Color(
                                                        0xFF004D40
                                                    ),
                                                    CircleShape
                                                )
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
// ... (TaskCard Composable remains identical) ...

@Composable
fun TaskCard(
    title: String,
    id: String,
    date: String,
    location: String,
    status: String,
    statusColor: Color,
    isFaded: Boolean = false,
    onViewDetailsClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.sdp), // Scalable padding
        colors = CardDefaults.cardColors(containerColor = if (isFaded) Color.White.copy(alpha = 0.5f) else Color.White),
        shape = RoundedCornerShape(16.sdp),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isFaded) 0.sdp else 2.sdp)
    ) {
        Column(modifier = Modifier.padding(16.sdp)) {

            // Header: Title and Custom Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = title,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.ssp, // Poppins font size
                        style = MaterialTheme.typography.titleMedium,
                        color = if (isFaded) Color.LightGray else Color.Black
                    )
                    Spacer(modifier = Modifier.height(4.sdp))
                    Text(
                        text = "ID $id",
                        color = if (isFaded) Color.LightGray else Color.Gray,
                        fontSize = 12.ssp,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(modifier = Modifier.width(8.sdp))

                // Custom Status Badge (Looks better than default M3 Badge)
                Surface(
                    color = statusColor, shape = RoundedCornerShape(6.sdp)
                ) {
                    Text(
                        text = status,
                        color = if (status == "Completed") Color.Gray else Color.White, // Gray text for completed
                        fontSize = 12.ssp,
                        fontWeight = FontWeight.Medium,
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.padding(horizontal = 8.sdp, vertical = 2.sdp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.sdp))

            // Info row: Calendar
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_calendar_filter_by), // Custom Calendar Icon
                    contentDescription = "Due Date",
                    tint = if (isFaded) Color.LightGray else Color.DarkGray,
                    modifier = Modifier.size(16.sdp)
                )
                Spacer(modifier = Modifier.width(8.sdp))
                Text(
                    "Due: ",
                    fontSize = 13.ssp,
                    color = if (isFaded) Color.LightGray else Color.Gray,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    date,
                    fontSize = 13.ssp,
                    color = if (isFaded) Color.LightGray else Color.Gray,
                    style = MaterialTheme.typography.bodyMedium,
                    textDecoration = TextDecoration.Underline // Underlined date
                )
            }

            Spacer(modifier = Modifier.height(8.sdp))

            // Info row: Location
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_location_small), // Custom Location Icon
                    contentDescription = "Location",
                    tint = if (isFaded) Color.LightGray else Color.DarkGray,
                    modifier = Modifier.size(16.sdp)
                )
                Spacer(modifier = Modifier.width(8.sdp))
                Text(
                    location,
                    fontSize = 13.ssp,
                    color = if (isFaded) Color.LightGray else Color.Gray,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(16.sdp))

            // Dual CTAs matching sizes exactly
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.sdp)
            ) {
                OutlinedButton(
                    onClick = onViewDetailsClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(32.sdp), // Set explicit CTA height
                    shape = RoundedCornerShape(8.sdp),
                    border = BorderStroke(
                        1.sdp, if (isFaded) Color(0xFFEEEEEE) else Color.LightGray
                    ),
                    contentPadding = PaddingValues(0.sdp)
                ) {
                    Text(
                        "View Details",
                        color = if (isFaded) Color.LightGray else Color.Black,
                        fontSize = 13.ssp,
                        fontWeight = FontWeight.Medium,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                // Standard Button used here to ensure absolute height parity with OutlinedButton
                Button(
                    onClick = onViewDetailsClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(32.sdp), // Matches OutlinedButton perfectly
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isFaded) Color(0xFFE0E0E0) else HousewiseGreen
                    ),
                    shape = RoundedCornerShape(8.sdp),
                    contentPadding = PaddingValues(0.sdp)
                ) {
                    Text(
                        text = if (isFaded) "Completed" else "Initiate",
                        color = if (isFaded) Color.White else Color.White,
                        fontSize = 13.ssp,
                        fontWeight = FontWeight.Medium,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}