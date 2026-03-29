package com.housewise.feature.dashboard.brokers

// Import your responsive utils
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.housewise.core.theme.HousewiseGreen
import com.housewise.core.theme.TextPrimary
import com.housewise.core.theme.TextSecondary
import com.housewise.core.utils.sdp
import com.housewise.core.utils.ssp

@Composable
fun NewBrokerSheet(
    onCancel: () -> Unit, onSave: () -> Unit
) {
    // 1. ADDED STATE VARIABLES FOR THE FORM
    var brokerName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var pincode by remember { mutableStateOf("") }
    var locationArea by remember { mutableStateOf("") }
    var brokerStatus by remember { mutableStateOf(true) } // State for the toggle switch

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.9f) // Allows the sheet to expand comfortably
            .background(Color.White)
            .padding(horizontal = 16.sdp)
            .padding(bottom = 24.sdp)
    ) {
        // 1. Header Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.sdp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = onCancel) {
                Text(
                    "Cancel",
                    color = Color.Gray,
                    fontSize = 16.ssp,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Text(
                "New Broker",
                fontWeight = FontWeight.Medium,
                fontSize = 18.ssp,
                color = TextPrimary,
                style = MaterialTheme.typography.titleLarge
            )
            TextButton(onClick = onSave) {
                Text(
                    "Save",
                    color = TextPrimary,
                    fontSize = 16.ssp,
                    fontWeight = FontWeight.Medium,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        // 2. Scrollable Form Sections
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            // Section 1: Basic Information
            BrokerSectionCard(
                title = "Basic Information",
                iconResId = R.drawable.ic_add_people // Use your user outline icon
            ) {
                // FIXED: Passed value and onValueChange
                SharedFormField(
                    label = "Name*",
                    value = brokerName,
                    onValueChange = { brokerName = it },
                    placeholder = "Enter broker name"
                )

                SharedFormField(
                    label = "Phone Number*",
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    placeholder = "Enter phone number",
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_call_small),
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(20.sdp)
                        )
                    }
                )

                SharedFormField(
                    label = "Email",
                    value = email,
                    onValueChange = { email = it },
                    placeholder = "Enter email address",
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_mail),
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(20.sdp)
                        )
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.sdp))

            // Section 2: Location Details
            BrokerSectionCard(
                title = "Location Details",
                iconResId = R.drawable.ic_location_small // Use your map pin icon
            ) {
                SharedFormField(
                    label = "City*",
                    value = city,
                    onValueChange = { city = it },
                    placeholder = "Select city",
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_building_small),
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(20.sdp)
                        )
                    }
                )

                SharedFormField(
                    label = "Pincode",
                    value = pincode,
                    onValueChange = { pincode = it },
                    placeholder = "Enter pincode",
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_tag),
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(20.sdp)
                        )
                    }
                )

                SharedFormField(
                    label = "Location/Area",
                    value = locationArea,
                    onValueChange = { locationArea = it },
                    placeholder = "Enter detailed location",
                    isMultiline = true, // Taller field for address
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_location_small),
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(20.sdp)
                        )
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.sdp))

            // Section 3: Broker Status Toggle
            OutlinedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.sdp),
                colors = CardDefaults.outlinedCardColors(containerColor = Color.White),
                border = BorderStroke(1.sdp, Color(0xFFEEEEEE))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.sdp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            "Broker Status",
                            fontSize = 14.ssp,
                            color = TextPrimary,
                            fontWeight = FontWeight.Medium,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.height(4.sdp))
                        Text(
                            "Set broker as active or inactive",
                            fontSize = 12.ssp,
                            color = TextSecondary,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Switch(
                        checked = brokerStatus,
                        onCheckedChange = { brokerStatus = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = Color.Black, // Black track when active
                            uncheckedThumbColor = Color.White,
                            uncheckedTrackColor = Color.LightGray
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.sdp))
        }

        // 3. Sticky Bottom Button
        Spacer(modifier = Modifier.height(16.sdp))
        Button(
            onClick = {
                // TODO: When you integrate API, you can use these variables:
                // brokerName, phoneNumber, email, city, pincode, locationArea, brokerStatus
                onSave()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.sdp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF38C996)), // Light green
            shape = RoundedCornerShape(12.sdp),
            contentPadding = PaddingValues(0.sdp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_save_sheet),
                    contentDescription = "Save",
                    tint = Color.White,
                    modifier = Modifier.size(18.sdp)
                )
                Spacer(modifier = Modifier.width(8.sdp))
                Text(
                    text = "Save Broker",
                    color = Color.White,
                    fontSize = 16.ssp,
                    fontWeight = FontWeight.Normal,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

// Helper component for the outlined sections
@Composable
fun BrokerSectionCard(
    title: String, iconResId: Int, content: @Composable ColumnScope.() -> Unit
) {
    OutlinedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.sdp),
        colors = CardDefaults.outlinedCardColors(containerColor = Color.White),
        border = BorderStroke(1.sdp, Color(0xFFEEEEEE))
    ) {
        Column(modifier = Modifier.padding(16.sdp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = iconResId),
                    contentDescription = null,
                    tint = Color.DarkGray,
                    modifier = Modifier.size(18.sdp)
                )
                Spacer(modifier = Modifier.width(8.sdp))
                Text(
                    text = title,
                    fontSize = 16.ssp,
                    fontWeight = FontWeight.Normal,
                    color = TextPrimary,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Spacer(modifier = Modifier.height(16.sdp))
            // Render the fields inside
            content()
        }
    }
}

// FIXED: Added value and onValueChange parameters
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SharedFormField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isDropdown: Boolean = false,
    isMultiline: Boolean = false,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: String? = null
) {
    Column(modifier = Modifier.padding(bottom = 16.sdp)) {
        Text(
            text = label,
            fontSize = 14.ssp,
            color = TextPrimary,
            fontWeight = FontWeight.Medium,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 8.sdp)
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder, color = Color.LightGray, fontSize = 14.ssp) },
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 14.ssp,
                color = TextPrimary
            ),
            modifier = Modifier
                .fillMaxWidth()
                .then(if (isMultiline) Modifier.height(120.sdp) else Modifier.height(54.sdp)),
            shape = RoundedCornerShape(8.sdp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xFFE0E0E0),
                focusedBorderColor = HousewiseGreen
            ),
            trailingIcon = trailingIcon ?: if (isDropdown) {
                {
                    Icon(
                        Icons.Default.KeyboardArrowDown,
                        "Dropdown",
                        tint = Color.Gray,
                        modifier = Modifier.size(24.sdp)
                    )
                }
            } else null,
            leadingIcon = leadingIcon ?: if (prefix != null) {
                {
                    Text(
                        prefix,
                        color = Color.Gray,
                        fontSize = 14.ssp,
                        modifier = Modifier.padding(start = 12.sdp)
                    )
                }
            } else null,
            readOnly = isDropdown,
            singleLine = !isMultiline
        )
    }
}