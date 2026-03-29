package com.housewise.feature.dashboard.leads

// Import your responsive utils
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.housewise.R
import com.housewise.core.theme.HousewiseGreen
import com.housewise.core.theme.StatusRed
import com.housewise.core.theme.TextPrimary
import com.housewise.core.theme.TextSecondary
import com.housewise.core.utils.sdp
import com.housewise.core.utils.ssp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeadDetailsScreen(
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "My Leads",
                        fontSize = 18.ssp,
                        fontWeight = FontWeight.Medium,
                        style = MaterialTheme.typography.titleLarge, // Poppins
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
                    IconButton(onClick = { /* Share action */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_share), // Ensure you have this
                            contentDescription = "Share",
                            modifier = Modifier.size(24.sdp),
                            tint = TextPrimary
                        )
                    }
                    IconButton(onClick = { /* Edit action */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_edit_top), // Ensure you have this
                            contentDescription = "Edit",
                            modifier = Modifier.size(24.sdp),
                            tint = TextPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            // Fixed Bottom Action Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.sdp),
                horizontalArrangement = Arrangement.spacedBy(12.sdp)
            ) {
                OutlinedButton(
                    onClick = { /* Update Status */ },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.sdp),
                    shape = RoundedCornerShape(12.sdp),
                    border = BorderStroke(1.sdp, Color(0xFFE0E0E0))
                ) {
                    Text(
                        text = "Update Status",
                        color = TextSecondary,
                        fontSize = 14.ssp,
                        fontWeight = FontWeight.Normal,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                Button(
                    onClick = { /* Add Note */ },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.sdp),
                    shape = RoundedCornerShape(12.sdp),
                    colors = ButtonDefaults.buttonColors(containerColor = HousewiseGreen)
                ) {
                    Text(
                        text = "Add Note",
                        color = Color.White,
                        fontSize = 14.ssp,
                        fontWeight = FontWeight.Normal,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        },
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.sdp, vertical = 12.sdp)
        ) {

            // 1. Status Banner
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.sdp),
                color = StatusRed // Red background
            ) {
                Text(
                    text = "LeadResponse Not Interested",
                    color = Color.White,
                    fontSize = 14.ssp,
                    fontWeight = FontWeight.Normal,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(vertical = 8.sdp),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(16.sdp))

            // 2. Property Information Card
            OutlinedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.sdp),
                colors = CardDefaults.outlinedCardColors(containerColor = Color.White),
                border = BorderStroke(1.sdp, Color(0xFFEEEEEE))
            ) {
                Column(modifier = Modifier.padding(16.sdp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_building_small), // Replace with your building icon
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(16.sdp)
                        )
                        Spacer(modifier = Modifier.width(8.sdp))
                        Text(
                            text = "Property Information",
                            fontSize = 14.ssp,
                            color = Color.Gray,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }

                    Spacer(modifier = Modifier.height(16.sdp))

                    Row(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                "Property ID",
                                color = Color.Gray,
                                fontSize = 12.ssp,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Spacer(modifier = Modifier.height(6.sdp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_tag),
                                    contentDescription = null,
                                    tint = Color.Gray,
                                    modifier = Modifier.size(14.sdp)
                                )
                                Spacer(modifier = Modifier.width(4.sdp))
                                Text(
                                    "# 1656",
                                    fontSize = 14.ssp,
                                    fontWeight = FontWeight.Normal,
                                    color = TextPrimary,
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                "Listing ID",
                                color = Color.Gray,
                                fontSize = 12.ssp,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Spacer(modifier = Modifier.height(6.sdp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_tag),
                                    contentDescription = null,
                                    tint = Color.Gray,
                                    modifier = Modifier.size(14.sdp)
                                )
                                Spacer(modifier = Modifier.width(4.sdp))
                                Text(
                                    "# —",
                                    fontSize = 14.ssp,
                                    fontWeight = FontWeight.Normal,
                                    color = TextPrimary,
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.sdp))

            // 3. LeadResponse Profile Card
            OutlinedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.sdp),
                colors = CardDefaults.outlinedCardColors(containerColor = Color.White),
                border = BorderStroke(1.sdp, Color(0xFFEEEEEE))
            ) {
                Column(modifier = Modifier.padding(16.sdp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Top
                    ) {
                        // Avatar
                        Box(
                            modifier = Modifier
                                .size(48.sdp)
                                .background(Color(0xFF26C6DA), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "M",
                                color = Color.White,
                                fontWeight = FontWeight.Medium,
                                fontSize = 20.ssp,
                                style = MaterialTheme.typography.headlineMedium
                            )
                        }

                        Spacer(modifier = Modifier.width(12.sdp))

                        // Name and Action Buttons
                        Column {
                            Text(
                                text = "Mahendra Singh",
                                fontSize = 16.ssp,
                                fontWeight = FontWeight.Normal,
                                color = TextPrimary,
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Spacer(modifier = Modifier.height(8.sdp))
                            Row(horizontalArrangement = Arrangement.spacedBy(8.sdp)) {
                                // Call Button (Cyan Outline)
                                OutlinedButton(
                                    onClick = { /* Call */ },
                                    modifier = Modifier
                                        .height(32.sdp)
                                        .weight(1f),
                                    shape = RoundedCornerShape(8.sdp),
                                    border = BorderStroke(1.sdp, Color(0xFF26C6DA)),
                                    contentPadding = PaddingValues(0.sdp)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_call_small),
                                        contentDescription = null,
                                        tint = Color(0xFF26C6DA),
                                        modifier = Modifier.size(14.sdp)
                                    )
                                    Spacer(modifier = Modifier.width(6.sdp))
                                    Text(
                                        "Call",
                                        color = TextPrimary,
                                        fontSize = 13.ssp,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }

                                // Email Button (Gray Filled)
                                Button(
                                    onClick = { /* Email */ },
                                    modifier = Modifier
                                        .height(32.sdp)
                                        .weight(1f),
                                    shape = RoundedCornerShape(8.sdp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(
                                            0xFFF5F5F5
                                        )
                                    ),
                                    contentPadding = PaddingValues(0.sdp)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_mail),
                                        contentDescription = null,
                                        tint = Color.DarkGray,
                                        modifier = Modifier.size(14.sdp)
                                    )
                                    Spacer(modifier = Modifier.width(6.sdp))
                                    Text(
                                        "Email",
                                        color = TextPrimary,
                                        fontSize = 13.ssp,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.sdp))

                    // Phone Number
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_call_small),
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(14.sdp)
                        )
                        Spacer(modifier = Modifier.width(8.sdp))
                        Text(
                            "9822665405",
                            fontSize = 14.ssp,
                            color = TextPrimary,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }

                    Spacer(modifier = Modifier.height(12.sdp))

                    // Email Details
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_mail),
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(14.sdp)
                        )
                        Spacer(modifier = Modifier.width(8.sdp))
                        Text(
                            "No email provided",
                            fontSize = 14.ssp,
                            color = Color.Gray,
                            fontStyle = FontStyle.Italic,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.sdp))

            // 4. Details Grid Card
            OutlinedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.sdp),
                colors = CardDefaults.outlinedCardColors(containerColor = Color.White),
                border = BorderStroke(1.sdp, Color(0xFFEEEEEE))
            ) {
                Column(modifier = Modifier.padding(16.sdp)) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_location_small),
                                    contentDescription = null,
                                    tint = Color.Gray,
                                    modifier = Modifier.size(14.sdp)
                                )
                                Spacer(modifier = Modifier.width(6.sdp))
                                Text(
                                    "City",
                                    color = Color.Gray,
                                    fontSize = 12.ssp,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                            Spacer(modifier = Modifier.height(6.sdp))
                            Text(
                                "Pune",
                                fontSize = 14.ssp,
                                color = TextPrimary,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_building_small),
                                    contentDescription = null,
                                    tint = Color.Gray,
                                    modifier = Modifier.size(14.sdp)
                                )
                                Spacer(modifier = Modifier.width(6.sdp))
                                Text(
                                    "Source",
                                    color = Color.Gray,
                                    fontSize = 12.ssp,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                            Spacer(modifier = Modifier.height(6.sdp))
                            Text(
                                "HO",
                                fontSize = 14.ssp,
                                color = TextPrimary,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.sdp))

                    Row(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_calendar_filter_by),
                                    contentDescription = null,
                                    tint = Color.Gray,
                                    modifier = Modifier.size(14.sdp)
                                )
                                Spacer(modifier = Modifier.width(6.sdp))
                                Text(
                                    "Created",
                                    color = Color.Gray,
                                    fontSize = 12.ssp,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                            Spacer(modifier = Modifier.height(6.sdp))
                            Text(
                                "2023-07-07",
                                fontSize = 14.ssp,
                                color = TextPrimary,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_calendar_filter_by),
                                    contentDescription = null,
                                    tint = Color.Gray,
                                    modifier = Modifier.size(14.sdp)
                                )
                                Spacer(modifier = Modifier.width(6.sdp))
                                Text(
                                    "Reminder",
                                    color = Color.Gray,
                                    fontSize = 12.ssp,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                            Spacer(modifier = Modifier.height(6.sdp))
                            Text(
                                "2023-07-07",
                                fontSize = 14.ssp,
                                color = TextPrimary,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.sdp))

            // 5. Comments Card
            OutlinedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.sdp),
                colors = CardDefaults.outlinedCardColors(containerColor = Color.White),
                border = BorderStroke(1.sdp, Color(0xFFEEEEEE))
            ) {
                Column(modifier = Modifier.padding(16.sdp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_chat), // Message bubble icon
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(16.sdp)
                        )
                        Spacer(modifier = Modifier.width(8.sdp))
                        Text(
                            text = "Comments",
                            fontSize = 16.ssp,
                            fontWeight = FontWeight.Normal,
                            color = TextPrimary,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }

                    Spacer(modifier = Modifier.height(12.sdp))

                    // Comment Body Box
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.sdp),
                        color = Color(0xFFF5F5F5) // Light gray background
                    ) {
                        Text(
                            text = "HO given the lead details, token also received by owner, but than lead is not interested to continue the property.",
                            color = TextPrimary,
                            fontSize = 13.ssp,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(12.sdp),
                            lineHeight = 20.ssp
                        )
                    }
                }
            }
        }
    }
}