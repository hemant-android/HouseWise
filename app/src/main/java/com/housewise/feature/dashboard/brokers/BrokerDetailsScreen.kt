package com.housewise.feature.dashboard.brokers

// Import your responsive utils
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontWeight
import com.housewise.R
import com.housewise.core.theme.BackgroundLight
import com.housewise.core.theme.HousewiseGreen
import com.housewise.core.theme.TextPrimary
import com.housewise.core.utils.sdp
import com.housewise.core.utils.ssp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrokerDetailsScreen(onBackClick: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Broker Manager",
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
                    // Delete Icon
                    IconButton(onClick = { /* Delete Action */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_delete), // Ensure you have this trash icon
                            contentDescription = "Delete",
                            modifier = Modifier.size(24.sdp),
                            tint = TextPrimary // It looks dark green/teal in the mockup
                        )
                    }
                    // Edit Icon
                    IconButton(onClick = { /* Edit Action */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_edit_top),
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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.sdp)
            ) {
                Button(
                    onClick = { /* Edit Broker */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.sdp),
                    shape = RoundedCornerShape(12.sdp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF38C996)), // Light green matching design
                    contentPadding = PaddingValues(0.sdp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_edit_broker), // Small edit pencil
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(16.sdp)
                        )
                        Spacer(modifier = Modifier.width(8.sdp))
                        Text(
                            text = "Edit Broker",
                            color = Color.White,
                            fontSize = 16.ssp,
                            fontWeight = FontWeight.Medium,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        },
        containerColor = BackgroundLight
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.sdp, vertical = 12.sdp)
        ) {

            // 1. Broker Profile Header Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.sdp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.sdp)
            ) {
                Column {
                    // Cyan Top Header
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.sdp)
                            .background(Color(0xFF26C6DA)) // Cyan color
                    )

                    // Bottom White Section with Overlapping Avatar
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.sdp)
                    ) {
                        // Status Badge - Align Top End
                        Surface(
                            shape = RoundedCornerShape(6.sdp),
                            color = HousewiseGreen,
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(top = 12.sdp)
                        ) {
                            Text(
                                text = "Active",
                                color = Color.White,
                                fontSize = 11.ssp,
                                fontWeight = FontWeight.Medium,
                                style = MaterialTheme.typography.labelMedium,
                                modifier = Modifier.padding(horizontal = 12.sdp, vertical = 2.sdp)
                            )
                        }

                        // Overlapping Avatar - Pulled UP using negative offset
                        Box(
                            modifier = Modifier
                                .offset(y = (-32).sdp) // Pulls the circle halfway into the cyan area
                                .size(64.sdp)
                                .background(Color(0xFF26C6DA), CircleShape)
                                .border(3.sdp, Color.White, CircleShape), // Thick white border
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "AE",
                                color = Color.White,
                                fontWeight = FontWeight.Medium,
                                fontSize = 20.ssp,
                                style = MaterialTheme.typography.headlineMedium
                            )
                        }

                        // Name Text
                        Text(
                            text = "Ashish Enterprise",
                            fontSize = 18.ssp,
                            fontWeight = FontWeight.Normal,
                            color = TextPrimary,
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(
                                top = 44.sdp,
                                bottom = 16.sdp
                            ) // Spaced down to clear the avatar
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.sdp))

            // 2. Contact Information Card
            OutlinedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.sdp),
                colors = CardDefaults.outlinedCardColors(containerColor = Color.White),
                border = BorderStroke(1.sdp, Color(0xFFEEEEEE))
            ) {
                Column(modifier = Modifier.padding(16.sdp)) {
                    Text(
                        text = "Contact Information",
                        fontSize = 14.ssp,
                        fontWeight = FontWeight.Normal,
                        color = TextPrimary,
                        style = MaterialTheme.typography.titleLarge
                    )

                    Spacer(modifier = Modifier.height(16.sdp))

                    // Email Row
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(40.sdp)
                                .background(Color(0xFFF0FDF4), CircleShape), // Very light green
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_mail),
                                contentDescription = null,
                                tint = HousewiseGreen,
                                modifier = Modifier.size(18.sdp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.sdp))
                        Column {
                            Text(
                                "Email",
                                color = Color.Gray,
                                fontSize = 12.ssp,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Spacer(modifier = Modifier.height(2.sdp))
                            Text(
                                "knjq@testmail.com",
                                fontSize = 14.ssp,
                                color = TextPrimary,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.sdp))

                    // Phone Row
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.sdp)
                                .background(Color(0xFFF0FDF4), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_call_small),
                                contentDescription = null,
                                tint = HousewiseGreen,
                                modifier = Modifier.size(18.sdp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.sdp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                "Phone Number",
                                color = Color.Gray,
                                fontSize = 12.ssp,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Spacer(modifier = Modifier.height(2.sdp))
                            Text(
                                "9875636402",
                                fontSize = 14.ssp,
                                color = TextPrimary,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        // Quick Call Icon (Cyan)
                        IconButton(onClick = { /* Call */ }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_call_small),
                                contentDescription = "Call",
                                tint = Color(0xFF26C6DA),
                                modifier = Modifier.size(20.sdp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.sdp))

            // 3. Property Details Card
            OutlinedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.sdp),
                colors = CardDefaults.outlinedCardColors(containerColor = Color.White),
                border = BorderStroke(1.sdp, Color(0xFFEEEEEE))
            ) {
                Column(modifier = Modifier.padding(16.sdp)) {
                    Text(
                        text = "Property Details",
                        fontSize = 14.ssp,
                        fontWeight = FontWeight.Normal,
                        color = TextPrimary,
                        style = MaterialTheme.typography.titleLarge
                    )

                    Spacer(modifier = Modifier.height(16.sdp))

                    Text(
                        "Property Address",
                        color = Color.Gray,
                        fontSize = 12.ssp,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(4.sdp))
                    Text(
                        "South Kolkata",
                        fontSize = 14.ssp,
                        color = TextPrimary,
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Spacer(modifier = Modifier.height(16.sdp))

                    Row(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                "City",
                                color = Color.Gray,
                                fontSize = 12.ssp,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Spacer(modifier = Modifier.height(4.sdp))
                            Text(
                                "Kolkata",
                                fontSize = 14.ssp,
                                color = TextPrimary,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                "Pincode",
                                color = Color.Gray,
                                fontSize = 12.ssp,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Spacer(modifier = Modifier.height(4.sdp))
                            Text(
                                "N/A",
                                fontSize = 14.ssp,
                                color = TextPrimary,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.sdp))

            // 4. Other Details Card
            OutlinedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.sdp),
                colors = CardDefaults.outlinedCardColors(containerColor = Color.White),
                border = BorderStroke(1.sdp, Color(0xFFEEEEEE))
            ) {
                Column(modifier = Modifier.padding(16.sdp)) {
                    Text(
                        text = "Other Details",
                        fontSize = 14.ssp,
                        fontWeight = FontWeight.Normal,
                        color = TextPrimary,
                        style = MaterialTheme.typography.titleLarge
                    )

                    Spacer(modifier = Modifier.height(16.sdp))

                    Row(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                "Listing ID",
                                color = Color.Gray,
                                fontSize = 12.ssp,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Spacer(modifier = Modifier.height(4.sdp))
                            Text(
                                "N/A",
                                fontSize = 14.ssp,
                                color = TextPrimary,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                "Created at",
                                color = Color.Gray,
                                fontSize = 12.ssp,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Spacer(modifier = Modifier.height(4.sdp))
                            Text(
                                "23/2/2023",
                                fontSize = 14.ssp,
                                color = TextPrimary,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.sdp))

                    Text(
                        "Comments",
                        color = Color.Gray,
                        fontSize = 12.ssp,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(8.sdp))

                    // Grey Comments Box
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.sdp),
                        color = Color(0xFFF5F5F5)
                    ) {
                        Text(
                            text = "Details shared for PID 2587/2669/2437/2409",
                            color = TextPrimary,
                            fontSize = 13.ssp,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(12.sdp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.sdp)) // Bottom padding buffer
        }
    }
}