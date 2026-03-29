package com.housewise.feature.dashboard.leads

import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import com.housewise.R
import com.housewise.core.theme.HousewiseDarkGreen
import com.housewise.core.theme.HousewiseGreen
import com.housewise.core.theme.TextPrimary
import com.housewise.core.utils.Resource
import com.housewise.core.utils.sdp
import com.housewise.core.utils.ssp
import com.housewise.feature.dashboard.leads.presentation.AddLeadViewModel

@Composable
fun NewLeadSheet(
    onCancel: () -> Unit,
    onSave: () -> Unit,
    viewModel: AddLeadViewModel = viewModel()
) {
    val context = LocalContext.current
    val addLeadState by viewModel.addLeadState.collectAsState()

    // Form States
    var propertyId by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var altPhone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var source by remember { mutableStateOf("") }
    var listingId by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("") }
    var remarks by remember { mutableStateOf("") }
    var reminder by remember { mutableStateOf("") } // Expected format YYYY-MM-DD

    // Handle API State
    LaunchedEffect(addLeadState) {
        when (addLeadState) {
            is Resource.Success -> {
                Toast.makeText(context, "LeadResponse Saved!", Toast.LENGTH_SHORT).show()
                viewModel.resetState()
                onSave() // Close the sheet
            }

            is Resource.Error -> {
                val message = (addLeadState as Resource.Error).message
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                viewModel.resetState()
            }

            else -> Unit
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
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
                    Text("Cancel", color = Color.Gray, fontSize = 16.ssp)
                }
                Text(
                    "New LeadResponse",
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.ssp,
                    color = TextPrimary
                )
                TextButton(
                    onClick = {
                        viewModel.submitLead(
                            propertyId, name, "Jaipur", phone, altPhone, email,
                            location, "Website", listingId, "NEW", remarks, reminder
                        )
                    }
                ) {
                    Text(
                        "Save",
                        color = TextPrimary,
                        fontSize = 16.ssp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // 2. Scrollable Form Fields
            Column(
                modifier = Modifier
                    .weight(1f, fill = false)
                    .verticalScroll(rememberScrollState())
            ) {
                SharedFormField("Housewise Property ID*", propertyId, { propertyId = it }, "XXXXXX")
                SharedFormField("Name*", name, { name = it }, "Complete name of lead")

                SharedFormField(
                    "City*",
                    city,
                    { city = it },
                    "Select city",
                    isDropdown = true,
                    leadingIcon = {
                        Icon(
                            painterResource(id = R.drawable.ic_building_small),
                            null,
                            tint = Color.Gray,
                            modifier = Modifier.size(20.sdp)
                        )
                    })

                SharedFormField(
                    "Phone*",
                    phone,
                    { phone = it },
                    "Enter phone number",
                    leadingIcon = {
                        Icon(
                            painterResource(id = R.drawable.ic_call_small),
                            null,
                            tint = Color.Gray,
                            modifier = Modifier.size(20.sdp)
                        )
                    })

                SharedFormField("Alternate Phone", altPhone, { altPhone = it }, "")

                SharedFormField(
                    "Email ID",
                    email,
                    { email = it },
                    "Enter email address",
                    leadingIcon = {
                        Icon(
                            painterResource(id = R.drawable.ic_mail),
                            null,
                            tint = Color.Gray,
                            modifier = Modifier.size(20.sdp)
                        )
                    })

                SharedFormField("Interested Location", location, { location = it }, "")
                SharedFormField("Source*", source, { source = it }, "", isDropdown = true)
                SharedFormField("Listing ID*", listingId, { listingId = it }, "")
                SharedFormField("Status*", status, { status = it }, "", isDropdown = true)
                SharedFormField("Remarks*", remarks, { remarks = it }, "")

                SharedFormField(
                    "Set Reminder (YYYY-MM-DD)",
                    reminder,
                    { reminder = it },
                    "",
                    trailingIcon = {
                        Icon(
                            painterResource(id = R.drawable.ic_calendar_due),
                            "Calendar",
                            tint = HousewiseDarkGreen,
                            modifier = Modifier.size(20.sdp)
                        )
                    })

                Spacer(modifier = Modifier.height(8.sdp))
            }

            // 3. Sticky Bottom Button
            Spacer(modifier = Modifier.height(16.sdp))
            Button(
                onClick = {
                    viewModel.submitLead(
                        propertyId, name, "Jaipur", phone, altPhone, email,
                        location, "Website", listingId, "NEW", remarks, reminder
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.sdp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF38C996)),
                shape = RoundedCornerShape(12.sdp),
                contentPadding = PaddingValues(0.sdp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painterResource(id = R.drawable.ic_save_sheet),
                        "Save",
                        tint = Color.White,
                        modifier = Modifier.size(18.sdp)
                    )
                    Spacer(modifier = Modifier.width(8.sdp))
                    Text("Save", color = Color.White, fontSize = 16.ssp)
                }
            }
        }

        // Loading Overlay
        if (addLeadState is Resource.Loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = HousewiseGreen)
            }
        }
    }
}

// FIXED: Hoisted state out of this component so the parent can read the data!
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