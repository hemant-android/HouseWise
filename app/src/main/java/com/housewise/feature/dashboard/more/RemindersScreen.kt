package com.housewise.feature.dashboard.more

// Import your responsive utils
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CardDefaults
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.housewise.R
import com.housewise.core.theme.HousewiseDarkGreen
import com.housewise.core.theme.HousewiseGreen
import com.housewise.core.theme.TextPrimary
import com.housewise.core.theme.TextSecondary
import com.housewise.core.utils.sdp
import com.housewise.core.utils.ssp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemindersScreen(onBackClick: () -> Unit) {
    // ADDED: State to manage the visibility of the new sheet
    var showNewReminderSheet by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Reminders",
                        fontSize = 18.ssp,
                        fontWeight = FontWeight.Medium,
                        style = MaterialTheme.typography.titleLarge,
                        color = TextPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "Back",
                            modifier = Modifier.size(20.sdp),
                            tint = TextPrimary
                        )
                    }
                },
                actions = {
                    // Dark Green Add Button
                    IconButton(onClick = { showNewReminderSheet = true }) {
                        Box(
                            modifier = Modifier
                                .size(26.sdp)
                                .background(HousewiseDarkGreen, RoundedCornerShape(8.sdp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add Reminder",
                                tint = Color.White,
                                modifier = Modifier.size(20.sdp)
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = Color.White
    ) { paddingValues ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.sdp, vertical = 16.sdp)
            ) {
                // 1. Stat Cards Row
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.sdp)
                    ) {
                        // Active Reminders Card
                        Surface(
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.sdp),
                            color = Color(0xFFE0F7FA) // Light Cyan
                        ) {
                            Column(modifier = Modifier.padding(16.sdp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_notification), // Replace with bell icon
                                        contentDescription = null,
                                        tint = Color(0xFF00ACC1), // Cyan
                                        modifier = Modifier.size(16.sdp)
                                    )
                                    Spacer(modifier = Modifier.width(6.sdp))
                                    Text(
                                        text = "Active Reminders",
                                        fontSize = 12.ssp,
                                        color = TextPrimary,
                                        fontWeight = FontWeight.Medium,
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.sdp))
                                Text(
                                    text = "4",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 24.ssp,
                                    color = TextPrimary,
                                    style = MaterialTheme.typography.headlineMedium
                                )
                            }
                        }

                        // Completed Card
                        Surface(
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.sdp),
                            color = Color(0xFFE8F5E9) // Light Green
                        ) {
                            Column(modifier = Modifier.padding(16.sdp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_check_circle), // Replace with check icon
                                        contentDescription = null,
                                        tint = HousewiseGreen,
                                        modifier = Modifier.size(16.sdp)
                                    )
                                    Spacer(modifier = Modifier.width(6.sdp))
                                    Text(
                                        text = "Completed",
                                        fontSize = 12.ssp,
                                        color = TextPrimary,
                                        fontWeight = FontWeight.Medium,
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.sdp))
                                Text(
                                    text = "1",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 24.ssp,
                                    color = TextPrimary,
                                    style = MaterialTheme.typography.headlineMedium
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(24.sdp))
                }

                // 2. Active Reminders Section
                item {
                    Text(
                        text = "Active Reminders",
                        color = Color.Gray,
                        fontSize = 14.ssp,
                        fontWeight = FontWeight.Normal,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(bottom = 12.sdp)
                    )
                }

                item {
                    ReminderCard(
                        title = "Follow up with client",
                        contextName = "Rajesh Sharma",
                        contextIcon = R.drawable.ic_profile, // Outline User
                        tag = "LeadResponse",
                        tagColor = Color(0xFFF59E0B), // Orange/Yellow
                        date = "Today",
                        time = "2:30 PM",
                        description = "Discuss property pricing"
                    )
                }

                item {
                    ReminderCard(
                        title = "Complete inspection report",
                        contextName = "Property Inspection #PID-1234",
                        contextIcon = R.drawable.ic_building_small, // Outline Building/Briefcase
                        tag = "Task",
                        tagColor = HousewiseGreen, // Green
                        date = "Today",
                        time = "5:00 PM"
                    )
                }

                item {
                    ReminderCard(
                        title = "Send quotation",
                        contextName = "Priya Patel",
                        contextIcon = R.drawable.ic_profile, // Outline User
                        tag = "LeadResponse",
                        tagColor = Color(0xFFF59E0B), // Orange/Yellow
                        date = "Tomorrow",
                        time = "10:00 AM"
                    )
                }

                item {
                    ReminderCard(
                        title = "Schedule site visit",
                        contextName = "Site Visit Coordination",
                        contextIcon = R.drawable.ic_building_small, // Outline Building/Briefcase
                        tag = "Task",
                        tagColor = HousewiseGreen, // Green
                        date = "Tomorrow" // No time provided in mockup
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(16.sdp))
                    Text(
                        text = "Completed",
                        color = Color.Gray,
                        fontSize = 14.ssp,
                        fontWeight = FontWeight.Normal,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(bottom = 12.sdp)
                    )
                }
            }
            // ADDED: The ModalBottomSheet logic here
            if (showNewReminderSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showNewReminderSheet = false },
                    containerColor = Color.White,
                    dragHandle = null,
                    shape = RoundedCornerShape(topStart = 24.sdp, topEnd = 24.sdp)
                ) {
                    NewReminderSheet(
                        onCancel = { showNewReminderSheet = false },
                        onSave = {
                            /* TODO: Save Logic */
                            showNewReminderSheet = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ReminderCard(
    title: String,
    contextName: String,
    contextIcon: Int,
    tag: String,
    tagColor: Color,
    date: String,
    time: String? = null,
    description: String? = null
) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.sdp)
            .clickable { /* Handle click */ },
        shape = RoundedCornerShape(12.sdp),
        colors = CardDefaults.outlinedCardColors(containerColor = Color.White),
        border = BorderStroke(1.sdp, Color(0xFFEEEEEE))
    ) {
        Row(
            modifier = Modifier.padding(16.sdp),
            verticalAlignment = Alignment.Top
        ) {
            // Unchecked Radio Circle
            Box(
                modifier = Modifier
                    .size(22.sdp)
                    .border(2.sdp, Color(0xFFD1D5DB), CircleShape) // Light gray border
            )

            Spacer(modifier = Modifier.width(16.sdp))

            Column(modifier = Modifier.weight(1f)) {
                // Title and Chevron Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = title,
                        fontSize = 15.ssp,
                        fontWeight = FontWeight.Normal,
                        color = TextPrimary,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_right),
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(16.sdp)
                    )
                }

                Spacer(modifier = Modifier.height(8.sdp))

                // Context Info Row (Icon, Name, Badge)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(24.sdp)
                            .background(
                                Color(0xFF00ACC1),
                                CircleShape
                            ), // Cyan background for the small context icon
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = contextIcon),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(12.sdp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.sdp))

                    // FIXED: Added weight(1f) to the Text so it wraps instead of pushing the badge off-screen
                    Text(
                        text = contextName,
                        fontSize = 13.ssp,
                        color = TextSecondary,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.weight(1f) // <--- THIS IS THE FIX
                    )

                    Spacer(modifier = Modifier.width(8.sdp))

                    // LeadResponse/Task Badge (Will now stay anchored to the right side)
                    Surface(
                        color = tagColor,
                        shape = RoundedCornerShape(12.sdp)
                    ) {
                        Text(
                            text = tag,
                            color = Color.White,
                            fontSize = 11.ssp,
                            fontWeight = FontWeight.Medium,
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier.padding(horizontal = 8.sdp, vertical = 2.sdp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.sdp))

                // Date & Time Row
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_calendar_due),
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(14.sdp)
                    )
                    Spacer(modifier = Modifier.width(4.sdp))
                    Text(
                        text = date,
                        fontSize = 13.ssp,
                        color = TextSecondary,
                        style = MaterialTheme.typography.bodyMedium
                    )

                    if (time != null) {
                        Spacer(modifier = Modifier.width(12.sdp))
                        Icon(
                            painter = painterResource(id = R.drawable.ic_clock),
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(14.sdp)
                        )
                        Spacer(modifier = Modifier.width(4.sdp))
                        Text(
                            text = time,
                            fontSize = 13.ssp,
                            color = TextSecondary,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                // Optional Description
                if (description != null) {
                    Spacer(modifier = Modifier.height(8.sdp))
                    Text(
                        text = description,
                        fontSize = 13.ssp,
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}