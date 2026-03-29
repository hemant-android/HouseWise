package com.housewise.feature.dashboard.notifications

// Import your responsive utils
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
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
import androidx.compose.ui.text.style.TextAlign
import com.housewise.R
import com.housewise.core.theme.BackgroundLight
import com.housewise.core.theme.TextPrimary
import com.housewise.core.theme.TextSecondary
import com.housewise.core.utils.sdp
import com.housewise.core.utils.ssp

// --- Data Model ---
data class NotificationData(
    val id: String,
    val title: String,
    val message: String,
    val time: String,
    val iconResId: Int,
    val isUnread: Boolean,
    val dateGroup: String // e.g., "Today", "Yesterday"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(onBackClick: () -> Unit) {
    // Dummy Data reflecting the screenshot
    var notifications by remember {
        mutableStateOf(
            listOf(
                NotificationData(
                    "1",
                    "New Task Assigned",
                    "Repeat inspection report for property #4092 has been assigned to you. Due date:",
                    "2 hours ago",
                    R.drawable.ic_calendar_due,
                    true,
                    "Today"
                ),
                NotificationData(
                    "2",
                    "LeadResponse Status Updated",
                    "Neeraj from Bengaluru has scheduled a visit. Please prepare the inspection",
                    "5 hours ago",
                    R.drawable.ic_profile,
                    true,
                    "Today"
                ),
                NotificationData(
                    "3",
                    "System Update",
                    "App updated to version 1.0.19. Check out new features and improvements.",
                    "1 day ago",
                    R.drawable.ic_info,
                    false,
                    "Yesterday"
                ),
                NotificationData(
                    "4",
                    "Report Submitted Successfully",
                    "Your move-in report for property #3031 has been submitted and is under review.",
                    "1 day ago",
                    R.drawable.ic_document,
                    false,
                    "Yesterday"
                )
            )
        )
    }

    val isListEmpty = notifications.isEmpty()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Notifications",
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
                    // Clear All Button
                    TextButton(
                        onClick = { notifications = emptyList() },
                        enabled = !isListEmpty
                    ) {
                        Text(
                            text = "Clear All",
                            color = if (isListEmpty) Color.LightGray else Color(0xFFE57373), // Red if active, gray if empty
                            fontSize = 14.ssp,
                            fontWeight = FontWeight.Medium,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = BackgroundLight
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // "Mark all as read" Global Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 16.sdp, vertical = 12.sdp)
            ) {
                Button(
                    onClick = {
                        notifications = notifications.map { it.copy(isUnread = false) }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(36.sdp),
                    shape = RoundedCornerShape(8.sdp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isListEmpty) Color(0xFFE0E0E0) else Color(0xFF00ACC1), // Cyan if active, Gray if empty
                        disabledContainerColor = Color(0xFFE0E0E0)
                    ),
                    enabled = !isListEmpty,
                    contentPadding = PaddingValues(0.sdp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // FIXED: Replaced Default.Check with ic_double_tick
                        Icon(
                            painter = painterResource(id = R.drawable.ic_double_tick),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(16.sdp)
                        )
                        Spacer(modifier = Modifier.width(8.sdp))
                        Text(
                            text = "Mark all as read",
                            color = Color.White,
                            fontSize = 14.ssp,
                            fontWeight = FontWeight.Normal,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }

            if (isListEmpty) {
                // EMPTY STATE
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.sdp)
                            .background(
                                Color(0xFFE0F7FA),
                                CircleShape
                            ), // Very light cyan background
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_notification_empty), // Bell icon
                            contentDescription = "Empty",
                            tint = Color(0xFF04ADCE), // Cyan
                            modifier = Modifier.size(48.sdp)
                        )
                    }
                    Spacer(modifier = Modifier.height(24.sdp))
                    Text(
                        text = "No notifications",
                        fontSize = 16.ssp,
                        fontWeight = FontWeight.Normal,
                        color = TextPrimary,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.height(8.sdp))
                    Text(
                        text = "You're all caught up!\nNo new notifications at the moment.",
                        fontSize = 14.ssp,
                        color = TextSecondary,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(horizontal = 32.sdp),
                        lineHeight = 20.ssp
                    )
                }
            } else {
                // POPULATED STATE
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 16.sdp, vertical = 8.sdp)
                ) {
                    val groupedNotifications = notifications.groupBy { it.dateGroup }

                    groupedNotifications.forEach { (group, items) ->
                        // Date Group Header
                        item {
                            Text(
                                text = group,
                                color = TextSecondary,
                                fontSize = 14.ssp,
                                fontWeight = FontWeight.Medium,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(top = 16.sdp, bottom = 8.sdp)
                            )
                        }

                        // Notifications for this group
                        items(items) { item ->
                            NotificationCard(
                                item = item,
                                onMarkRead = {
                                    notifications = notifications.map {
                                        if (it.id == item.id) it.copy(isUnread = false) else it
                                    }
                                },
                                onDelete = {
                                    notifications = notifications.filter { it.id != item.id }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NotificationCard(
    item: NotificationData,
    onMarkRead: () -> Unit,
    onDelete: () -> Unit
) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.sdp),
        shape = RoundedCornerShape(12.sdp),
        colors = CardDefaults.outlinedCardColors(containerColor = Color.White),
        border = BorderStroke(1.sdp, Color(0xFFEEEEEE))
    ) {
        Column {
            // Content Area
            Row(
                modifier = Modifier.padding(16.sdp),
                verticalAlignment = Alignment.Top
            ) {
                // Icon Box
                Box(
                    modifier = Modifier
                        .size(48.sdp)
                        .background(Color(0xFFF5F5F5), RoundedCornerShape(12.sdp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = item.iconResId),
                        contentDescription = null,
                        tint = TextPrimary,
                        modifier = Modifier.size(24.sdp)
                    )
                }

                Spacer(modifier = Modifier.width(16.sdp))

                // Text Area
                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = item.title,
                            fontSize = 15.ssp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextPrimary,
                            style = MaterialTheme.typography.titleMedium
                        )
                        // Unread Dot
                        if (item.isUnread) {
                            Box(
                                modifier = Modifier
                                    .size(8.sdp)
                                    .background(Color(0xFF00BFA5), CircleShape) // Teal dot
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.sdp))

                    Text(
                        text = item.message,
                        fontSize = 13.ssp,
                        color = TextSecondary,
                        style = MaterialTheme.typography.bodyMedium,
                        lineHeight = 18.ssp
                    )

                    Spacer(modifier = Modifier.height(8.sdp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_clock), // Standard clock icon
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(12.sdp)
                        )
                        Spacer(modifier = Modifier.width(4.sdp))
                        Text(
                            text = item.time,
                            fontSize = 11.ssp,
                            color = Color.Gray,
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            }

            HorizontalDivider(color = Color(0xFFEEEEEE), thickness = 1.sdp)

            // Action Area
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.sdp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (item.isUnread) {
                    // Split actions: Mark as read | Delete
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clickable { onMarkRead() },
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // FIXED: Replaced Default.Check with ic_double_tick
                        Icon(
                            painter = painterResource(id = R.drawable.ic_double_tick),
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(16.sdp)
                        )
                        Spacer(modifier = Modifier.width(6.sdp))
                        Text(
                            "Mark as read",
                            color = TextSecondary,
                            fontSize = 13.ssp,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }

                    VerticalDivider(color = Color(0xFFEEEEEE), thickness = 1.sdp)

                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clickable { onDelete() },
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_delete),
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(16.sdp)
                        )
                        Spacer(modifier = Modifier.width(6.sdp))
                        Text(
                            "Delete",
                            color = TextSecondary,
                            fontSize = 13.ssp,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                } else {
                    // Full width delete action for read notifications
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable { onDelete() },
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_delete),
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(16.sdp)
                        )
                        Spacer(modifier = Modifier.width(6.sdp))
                        Text(
                            "Delete",
                            color = TextSecondary,
                            fontSize = 13.ssp,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}