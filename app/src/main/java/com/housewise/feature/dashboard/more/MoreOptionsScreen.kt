package com.housewise.feature.dashboard.more

// Import responsive utils
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.housewise.R
import com.housewise.core.theme.BackgroundLight
import com.housewise.core.theme.HousewiseGreen
import com.housewise.core.theme.TextPrimary
import com.housewise.core.theme.TextSecondary
import com.housewise.core.utils.sdp
import com.housewise.core.utils.ssp

@Composable
fun MoreOptionsScreen(onNavigateToReminders: () -> Unit, onNavigateToNotifications: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundLight)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.sdp, vertical = 16.sdp)
            .padding(bottom = 80.sdp) // Extra padding to ensure bottom items clear the FAB
    ) {

        // 1. Profile Card
        OutlinedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.sdp),
            colors = CardDefaults.outlinedCardColors(containerColor = Color.White),
            border = BorderStroke(1.sdp, Color(0xFFEEEEEE))
        ) {
            Column(modifier = Modifier.padding(16.sdp)) {
                // Top Row: Avatar & Info
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(56.sdp)
                            .background(Color(0xFF26C6DA), CircleShape), // Cyan Background
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "KT",
                            color = Color.White,
                            fontSize = 20.ssp,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }

                    Spacer(modifier = Modifier.width(16.sdp))

                    Column {
                        Text(
                            text = "Kushagra Singh Tanwar",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.ssp,
                            color = TextPrimary,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(2.sdp))
                        Text(
                            text = "kush@example.com",
                            color = TextSecondary,
                            fontSize = 13.ssp,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(6.sdp))
                        Surface(
                            shape = RoundedCornerShape(6.sdp), color = Color(0xFFF5F5F5)
                        ) {
                            Text(
                                text = "Property Manager",
                                color = Color.Gray,
                                fontSize = 11.ssp,
                                fontWeight = FontWeight.Medium,
                                style = MaterialTheme.typography.labelMedium,
                                modifier = Modifier.padding(horizontal = 8.sdp, vertical = 4.sdp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.sdp))
                HorizontalDivider(color = Color(0xFFF5F5F5), thickness = 1.sdp)
                Spacer(modifier = Modifier.height(16.sdp))

                // Bottom Row: Stats
                Row(modifier = Modifier.fillMaxWidth()) {
                    // Tasks Box
                    Surface(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.sdp),
                        color = Color(0xFFE0F7FA) // Light Cyan
                    ) {
                        Column(modifier = Modifier.padding(12.sdp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_task_complete), // Update with badge icon
                                    contentDescription = null,
                                    tint = Color(0xFF00ACC1),
                                    modifier = Modifier.size(16.sdp)
                                )
                                Spacer(modifier = Modifier.width(6.sdp))
                                Text(
                                    "Tasks Completed",
                                    fontSize = 11.ssp,
                                    color = TextPrimary,
                                    fontWeight = FontWeight.Medium,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                            Spacer(modifier = Modifier.height(8.sdp))
                            Text(
                                "24",
                                fontWeight = FontWeight.Bold,
                                fontSize = 22.ssp,
                                color = TextPrimary,
                                style = MaterialTheme.typography.headlineMedium
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.sdp))

                    // Reports Box
                    Surface(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.sdp),
                        color = Color(0xFFE8F5E9) // Light Green
                    ) {
                        Column(modifier = Modifier.padding(12.sdp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_star), // Update with star icon
                                    contentDescription = null,
                                    tint = HousewiseGreen,
                                    modifier = Modifier.size(16.sdp)
                                )
                                Spacer(modifier = Modifier.width(6.sdp))
                                Text(
                                    "Reports Submitted",
                                    fontSize = 11.ssp,
                                    color = TextPrimary,
                                    fontWeight = FontWeight.Medium,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                            Spacer(modifier = Modifier.height(8.sdp))
                            Text(
                                "12",
                                fontWeight = FontWeight.Bold,
                                fontSize = 22.ssp,
                                color = TextPrimary,
                                style = MaterialTheme.typography.headlineMedium
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.sdp))

        // 2. Account Section
        SectionHeader(title = "Account")
        MenuCard {
            MenuItem(
                title = "My Profile", iconResId = R.drawable.ic_profile
            ) // Ensure you have this outline user icon
            MenuDivider()
            MenuItem(
                title = "Notifications",
                iconResId = R.drawable.ic_notification,
                badgeCount = "3",
                onClick = onNavigateToNotifications
            )
            MenuDivider()
            MenuItem(
                title = "Reminders",
                iconResId = R.drawable.ic_document,
                badgeCount = "4",
                onClick = onNavigateToReminders
            )
            MenuDivider()
            MenuItem(title = "Settings", iconResId = R.drawable.ic_settings)
        }

        Spacer(modifier = Modifier.height(24.sdp))

        // 3. Reports & Documents Section
        SectionHeader(title = "Reports & Documents")
        MenuCard {
            MenuItem(title = "Search Submitted Reports", iconResId = R.drawable.ic_document)
            MenuDivider()
            MenuItem(
                title = "Create Sample Report", iconResId = R.drawable.ic_add_document
            ) // Doc with a plus
            MenuDivider()
            MenuItem(
                title = "Inspection Guidelines", iconResId = R.drawable.ic_document
            ) // Open book icon
        }

        Spacer(modifier = Modifier.height(24.sdp))

        // 4. Support Section
        SectionHeader(title = "Support")
        MenuCard {
            MenuItem(title = "Contact Us", iconResId = R.drawable.ic_mail)
            MenuDivider()
            MenuItem(title = "Help Center", iconResId = R.drawable.ic_document)
            MenuDivider()
            MenuItem(title = "Privacy Policy", iconResId = R.drawable.ic_document)
        }

        Spacer(modifier = Modifier.height(24.sdp))

        // 5. Logout Section
        MenuCard {
            MenuItem(
                title = "Logout",
                iconResId = R.drawable.ic_document,
                isDestructive = true,
                onClick = { /* Handle Logout */ })
        }

        Spacer(modifier = Modifier.height(24.sdp))

        // 6. Version Text
        Text(
            text = "Version 1.0.19",
            color = Color.Gray,
            fontSize = 12.ssp,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.sdp))
    }
}

// Helper Composable for Section Headers
@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        color = Color.Gray,
        fontSize = 12.ssp,
        fontWeight = FontWeight.Normal,
        style = MaterialTheme.typography.labelLarge,
        modifier = Modifier.padding(bottom = 8.sdp)
    )
}

// Helper Composable for Grouping Menus
@Composable
fun MenuCard(content: @Composable () -> Unit) {
    OutlinedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.sdp),
        colors = CardDefaults.outlinedCardColors(containerColor = Color.White),
        border = BorderStroke(1.sdp, Color(0xFFEEEEEE))
    ) {
        Column {
            content()
        }
    }
}

// Helper Composable for Menu Dividers
@Composable
fun MenuDivider() {
    HorizontalDivider(
        color = Color(0xFFF5F5F5),
        thickness = 1.sdp,
        modifier = Modifier.padding(horizontal = 16.sdp)
    )
}

// Helper Composable for Individual Menu Items
@Composable
fun MenuItem(
    title: String,
    iconResId: Int, // Enforces using drawable resources
    badgeCount: String? = null,
    isDestructive: Boolean = false, // Flag to apply the red styling for Logout
    onClick: () -> Unit = {}
) {
    val contentColor = if (isDestructive) Color(0xFFE57373) else TextPrimary // Light Red for logout
    val iconBgColor =
        if (isDestructive) Color(0xFFFFEBEE) else Color(0xFFF5F5F5) // Reddish BG or Gray BG

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.sdp, vertical = 12.sdp),
        verticalAlignment = Alignment.CenterVertically) {
        // Gray/Red Icon Box
        Box(
            modifier = Modifier
                .size(40.sdp)
                .background(iconBgColor, RoundedCornerShape(8.sdp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = iconResId),
                contentDescription = null,
                tint = contentColor,
                modifier = Modifier.size(20.sdp)
            )
        }

        Spacer(modifier = Modifier.width(16.sdp))

        // Item Title
        Text(
            text = title,
            color = contentColor,
            fontSize = 14.ssp,
            fontWeight = FontWeight.Normal,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )

        // Optional Green Badge
        if (badgeCount != null) {
            Box(
                modifier = Modifier
                    .size(24.sdp)
                    .background(HousewiseGreen, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = badgeCount,
                    color = Color.White,
                    fontSize = 12.ssp,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.labelSmall
                )
            }
            Spacer(modifier = Modifier.width(12.sdp))
        }

        // Right Chevron
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_right),
            contentDescription = null,
            tint = if (isDestructive) contentColor else Color.LightGray,
            modifier = Modifier.size(16.sdp)
        )
    }
}