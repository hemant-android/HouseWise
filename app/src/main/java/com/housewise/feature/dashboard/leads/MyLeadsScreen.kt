package com.housewise.feature.dashboard.leads

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
import androidx.compose.foundation.lazy.items // ADDED IMPORT
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator // ADDED IMPORT
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
import androidx.compose.runtime.collectAsState // ADDED IMPORT
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel // ADDED IMPORT
import com.housewise.R
import com.housewise.core.theme.BackgroundLight
import com.housewise.core.theme.HousewiseGreen
import com.housewise.core.theme.StatusRed
import com.housewise.core.theme.TextPrimary
import com.housewise.core.theme.TextSecondary
import com.housewise.core.utils.Resource
import com.housewise.core.utils.sdp
import com.housewise.core.utils.ssp
import com.housewise.feature.dashboard.leads.presentation.MyLeadsViewModel // ADDED IMPORT

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyLeadsScreen(
    onNavigateToLeadDetails: () -> Unit,
    onFilterClick: () -> Unit,
    viewModel: MyLeadsViewModel = viewModel() // 1. INJECT VIEWMODEL HERE
) {
    // State to track the active tab
    val tabs = listOf("My leads", "City Leads", "Unassigned")
    var selectedTab by remember { mutableStateOf(tabs[0]) }

    // 2. OBSERVE API STATE
    val leadsState by viewModel.leadsState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundLight)
            .padding(horizontal = 16.sdp) // Responsive padding
    ) {
        Spacer(modifier = Modifier.height(16.sdp))

        // Search Bar with Filter Icon
        OutlinedTextField(
            value = "",
            onValueChange = {},
            placeholder = {
                Text(
                    "Search leads(32)",
                    color = Color.Gray,
                    fontSize = 14.ssp,
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            leadingIcon = {
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
            shape = RoundedCornerShape(12.sdp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedBorderColor = Color(0xFFE0E0E0),
                unfocusedBorderColor = Color(0xFFE0E0E0)
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(24.sdp))

        // Tab Selector with Pill Background
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.sdp),
            shape = RoundedCornerShape(32.sdp),
            color = Color.White
        ) {
            Row(
                modifier = Modifier
                    .padding(4.sdp)
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                tabs.forEach { tabName ->
                    val isSelected = selectedTab == tabName
                    Surface(
                        shape = RoundedCornerShape(24.sdp),
                        color = if (isSelected) Color.White else Color.Transparent,
                        shadowElevation = if (isSelected) 2.sdp else 0.sdp,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clickable { selectedTab = tabName }
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(
                                text = tabName,
                                fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal,
                                color = if (isSelected) Color.Black else Color.Gray,
                                fontSize = 13.ssp,
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.sdp))

        // 3. API DATA HANDLING (Loading, Success, Error)
        Box(modifier = Modifier.fillMaxSize()) {
            when (leadsState) {
                is Resource.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = HousewiseGreen
                    )
                }

                is Resource.Error -> {
                    val message = (leadsState as Resource.Error).message ?: "An error occurred"
                    Text(
                        text = message,
                        color = StatusRed,
                        fontSize = 14.ssp,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                is Resource.Success -> {
                    val leads = (leadsState as Resource.Success).data ?: emptyList()

                    if (leads.isEmpty()) {
                        Text(
                            text = "No leads available.",
                            color = Color.Gray,
                            fontSize = 14.ssp,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    } else {
                        LazyColumn(
                            contentPadding = PaddingValues(bottom = 80.sdp)
                        ) {
                            items(leads) { lead ->
                                // Extract initials from name (e.g., "John Doe" -> "JD")
                                val initials = lead.leadName?.split(" ")
                                    ?.mapNotNull { it.firstOrNull()?.toString() }
                                    ?.take(2)
                                    ?.joinToString("")?.uppercase() ?: "U"

                                // Determine Status Color
                                val statusColor = when (lead.status?.uppercase()) {
                                    "NEW", "CONTACTED" -> HousewiseGreen
                                    "CLOSED", "NOT INTERESTED" -> StatusRed
                                    else -> Color.Gray
                                }

                                LeadCard(
                                    name = lead.leadName ?: "Unknown",
                                    initials = initials,
                                    location = lead.city ?: "N/A",
                                    phone = lead.leadContactNumber ?: "N/A",
                                    status = lead.status ?: "NEW",
                                    statusColor = statusColor,
                                    pid = lead.propertyId?.toString() ?: "N/A",
                                    source = lead.source ?: "N/A",
                                    onViewDetailsClick = onNavigateToLeadDetails
                                )
                            }
                        }
                    }
                }

                else -> Unit
            }
        }
    }
}

@Composable
fun LeadCard(
    name: String,
    initials: String,
    location: String,
    phone: String,
    status: String,
    statusColor: Color,
    pid: String,
    source: String,
    onViewDetailsClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.sdp),
        shape = RoundedCornerShape(16.sdp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.sdp)
    ) {
        Column(modifier = Modifier.padding(16.sdp)) {

            // Header: Avatar, Name, Location, and Call Button
            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Initials Circle
                Box(
                    modifier = Modifier
                        .size(48.sdp)
                        .background(Color(0xFF26C6DA), CircleShape), // Cyan color
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = initials,
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.ssp,
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                Spacer(modifier = Modifier.width(12.sdp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = name,
                        fontWeight = FontWeight.Normal,
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
                            fontWeight = FontWeight.Normal,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }

                // Call Icon
                IconButton(
                    onClick = {},
                    modifier = Modifier.size(24.sdp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_call_small),
                        contentDescription = "Call",
                        tint = Color(0xFF26C6DA), // Cyan
                        modifier = Modifier.size(20.sdp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.sdp))

            // Phone Row
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_call_small),
                    contentDescription = null,
                    tint = TextPrimary,
                    modifier = Modifier.size(16.sdp)
                )
                Spacer(modifier = Modifier.width(8.sdp))
                Text(
                    text = phone,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.ssp,
                    color = TextPrimary,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Spacer(modifier = Modifier.height(12.sdp))

            // Status and Meta Info Row
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    shape = RoundedCornerShape(6.sdp),
                    color = statusColor
                ) {
                    Text(
                        text = status,
                        color = Color.White,
                        fontSize = 11.ssp,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(horizontal = 8.sdp, vertical = 2.sdp),
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.width(8.sdp))

                Text(
                    text = "PID $pid • from $source",
                    color = TextSecondary,
                    fontSize = 11.ssp,
                    fontWeight = FontWeight.Normal,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Spacer(modifier = Modifier.height(16.sdp))

            // View Details Button
            OutlinedButton(
                onClick = onViewDetailsClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(38.sdp),
                shape = RoundedCornerShape(12.sdp),
                border = BorderStroke(1.sdp, HousewiseGreen),
                contentPadding = PaddingValues(0.sdp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "View Details",
                        color = TextPrimary,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.ssp,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.width(6.sdp))
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_right),
                        contentDescription = null,
                        tint = TextPrimary,
                        modifier = Modifier.size(16.sdp)
                    )
                }
            }
        }
    }
}