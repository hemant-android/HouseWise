package com.housewise.feature.dashboard.brokers

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.housewise.R
import com.housewise.core.theme.BackgroundLight
import com.housewise.core.theme.HousewiseGreen
import com.housewise.core.theme.StatusRed
import com.housewise.core.theme.TextPrimary
import com.housewise.core.theme.TextSecondary
// Import responsive utils
import com.housewise.core.utils.sdp
import com.housewise.core.utils.ssp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrokerManagerScreen(onNavigateToBrokerDetails: () -> Unit,onFilterClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundLight)
            .padding(horizontal = 16.sdp) // Responsive outer padding
    ) {
        Spacer(modifier = Modifier.height(16.sdp))

        // 1. Top Statistics Card (Combined)
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.sdp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.sdp) // Flat appearance in screenshot
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.sdp, horizontal = 20.sdp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left Stat: Total Brokers
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_add_people), // Replace with user outline icon
                            contentDescription = null,
                            tint = TextPrimary,
                            modifier = Modifier.size(16.sdp)
                        )
                        Spacer(modifier = Modifier.width(8.sdp))
                        Text(
                            text = "Total Brokers",
                            color = TextPrimary,
                            fontSize = 14.ssp,
                            fontWeight = FontWeight.Medium,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Spacer(modifier = Modifier.height(4.sdp))
                    Text(
                        text = "132",
                        fontSize = 24.ssp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary,
                        style = MaterialTheme.typography.headlineMedium
                    )
                }

                // Vertical Divider
                Box(
                    modifier = Modifier
                        .width(1.sdp)
                        .height(40.sdp)
                        .background(Color(0xFFEEEEEE))
                )

                Spacer(modifier = Modifier.width(24.sdp))

                // Right Stat: Active
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_check_circle), // Replace with check circle icon
                            contentDescription = null,
                            tint = TextPrimary,
                            modifier = Modifier.size(16.sdp)
                        )
                        Spacer(modifier = Modifier.width(8.sdp))
                        Text(
                            text = "Active",
                            color = TextPrimary,
                            fontSize = 14.ssp,
                            fontWeight = FontWeight.Medium,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Spacer(modifier = Modifier.height(4.sdp))
                    Text(
                        text = "105",
                        fontSize = 24.ssp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary,
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.sdp))

        // 2. Search Bar
        OutlinedTextField(
            value = "",
            onValueChange = {},
            placeholder = {
                Text(
                    "Search brokers(16)",
                    color = Color.Gray,
                    fontSize = 14.ssp,
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            leadingIcon = {
                // FIXED: Wrapped in a Row to add the vertical separator
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 16.sdp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = "Search",
                        tint = Color.Gray,
                        modifier = Modifier.size(25.sdp)
                    )
                    Spacer(modifier = Modifier.width(12.sdp))
                    // Vertical Separator Line
                    Box(
                        modifier = Modifier
                            .width(1.sdp)
                            .height(36.sdp)
                            .background(Color(0xFFEEEEEE))
                    )
                    Spacer(modifier = Modifier.width(12.sdp)) // Buffer before the placeholder text
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = onFilterClick,
                    modifier = Modifier.padding(end = 8.sdp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_filter),
                        contentDescription = "Filter",
                        tint = Color.DarkGray,
                        modifier = Modifier.size(25.sdp)
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.sdp),
            shape = RoundedCornerShape(12.sdp), // FIXED: Pill shape
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedBorderColor = Color(0xFFE0E0E0), // FIXED: Added Border
                unfocusedBorderColor = Color(0xFFE0E0E0) // FIXED: Added Border
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(24.sdp))

        // 3. Broker List
        LazyColumn(
            contentPadding = PaddingValues(bottom = 80.sdp) // Clearance for the FAB
        ) {
            item {
                BrokerCard(
                    name = "Test Broker1",
                    initials = "TB",
                    location = "Hyderabad",
                    status = "Inactive",
                    statusColor = StatusRed,
                    onViewDetailsClick = onNavigateToBrokerDetails
                )
                BrokerCard(
                    name = "Ashish Enterprise",
                    initials = "AE",
                    location = "Kolkata",
                    status = "Active",
                    statusColor = HousewiseGreen,
                    onViewDetailsClick = onNavigateToBrokerDetails
                )
                BrokerCard(
                    name = "Ravi Kumar",
                    initials = "RK",
                    location = "Bengaluru",
                    status = "Active",
                    statusColor = HousewiseGreen,
                    onViewDetailsClick = onNavigateToBrokerDetails
                )
            }
        }
    }
}

@Composable
fun BrokerCard(
    name: String,
    initials: String,
    location: String,
    status: String,
    statusColor: Color,
    onViewDetailsClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.sdp),
        shape = RoundedCornerShape(16.sdp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.sdp) // Subtle shadow
    ) {
        Column(modifier = Modifier.padding(16.sdp)) {

            // Header: Avatar, Info, Status
            Row(
                verticalAlignment = Alignment.Top, // Align top so badge sits nicely with the name
                modifier = Modifier.fillMaxWidth()
            ) {
                // Avatar
                Box(
                    modifier = Modifier
                        .size(48.sdp)
                        .background(Color(0xFF26C6DA), CircleShape), // Cyan color matching design
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = initials,
                        color = Color.White,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.ssp,
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                Spacer(modifier = Modifier.width(12.sdp))

                // Name and Location
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = name,
                        fontWeight = FontWeight.Normal, // Normal weight, but slightly larger
                        fontSize = 16.ssp,
                        color = TextPrimary,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(4.sdp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_location_small),
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(14.sdp)
                        )
                        Spacer(modifier = Modifier.width(4.sdp))
                        Text(
                            text = location,
                            color = TextSecondary,
                            fontSize = 13.ssp,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }

                // Status Badge
                Surface(
                    shape = RoundedCornerShape(6.sdp),
                    color = statusColor
                ) {
                    Text(
                        text = status,
                        color = Color.White,
                        fontSize = 11.ssp,
                        fontWeight = FontWeight.Medium,
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.padding(horizontal = 8.sdp, vertical = 2.sdp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.sdp))

            // Dual Action Buttons
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.sdp),
                modifier = Modifier.fillMaxWidth()
            ) {
                // Call Button (Gray Border)
                OutlinedButton(
                    onClick = {},
                    modifier = Modifier
                        .weight(1f)
                        .height(32.sdp),
                    shape = RoundedCornerShape(12.sdp),
                    border = BorderStroke(1.sdp, Color(0xFFEEEEEE)), // Light gray border
                    contentPadding = PaddingValues(0.sdp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_call_small),
                            contentDescription = null,
                            tint = TextPrimary,
                            modifier = Modifier.size(16.sdp)
                        )
                        Spacer(modifier = Modifier.width(6.sdp))
                        Text(
                            text = "Call",
                            color = TextPrimary,
                            fontSize = 14.ssp,
                            fontWeight = FontWeight.Normal,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }

                // View Details Button (Green Border)
                OutlinedButton(
                    onClick = onViewDetailsClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(32.sdp),
                    shape = RoundedCornerShape(12.sdp),
                    border = BorderStroke(1.sdp, HousewiseGreen), // Green border
                    contentPadding = PaddingValues(0.sdp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "View Details",
                            color = TextPrimary,
                            fontSize = 14.ssp,
                            fontWeight = FontWeight.Normal,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.width(4.sdp))
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_right), // Chevron right
                            contentDescription = null,
                            tint = TextPrimary,
                            modifier = Modifier.size(16.sdp)
                        )
                    }
                }
            }
        }
    }
}