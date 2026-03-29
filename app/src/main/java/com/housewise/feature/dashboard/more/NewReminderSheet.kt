package com.housewise.feature.dashboard.more

// Import responsive utils
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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
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

@Composable
fun NewReminderSheet(
    onCancel: () -> Unit,
    onSave: () -> Unit
) {
    // State for the custom selectors
    val priorities = listOf("Low", "Medium", "High")
    var selectedPriority by remember { mutableStateOf(priorities[1]) } // Default to Medium

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.95f) // Tall sheet for all fields
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
                "New Reminder",
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
                    fontWeight = FontWeight.Normal,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        // 2. Scrollable Form Content
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            // Reminder Type Card
            OutlinedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.sdp),
                shape = RoundedCornerShape(12.sdp),
                colors = CardDefaults.outlinedCardColors(containerColor = Color.White),
                border = BorderStroke(1.sdp, Color(0xFFEEEEEE))
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.sdp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.sdp)
                            .background(HousewiseGreen, RoundedCornerShape(8.sdp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_building_small),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(20.sdp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.sdp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            "Reminder Type",
                            color = Color.Gray,
                            fontSize = 12.ssp,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(2.sdp))
                        Text(
                            "Task Reminder",
                            color = TextPrimary,
                            fontSize = 16.ssp,
                            fontWeight = FontWeight.Medium,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                    OutlinedButton(
                        onClick = { /* Change Type */ },
                        shape = RoundedCornerShape(8.sdp),
                        border = BorderStroke(1.sdp, Color(0xFFE0E0E0)),
                        contentPadding = PaddingValues(horizontal = 12.sdp, vertical = 0.sdp),
                        modifier = Modifier.height(36.sdp)
                    ) {
                        Text(
                            "Change",
                            color = HousewiseDarkGreen,
                            fontSize = 13.ssp,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            // Standard Fields
            SharedFormField(
                label = "Reminder Title*",
                placeholder = "e.g., Complete inspection report"
            )

            SharedFormField(
                label = "Related Task",
                placeholder = "Select a task",
                isDropdown = true,
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(20.sdp)
                    )
                }
            )

            // Side-by-side Date & Time
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.sdp)
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    SharedFormField(
                        label = "Date", placeholder = "",
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_calendar_due),
                                contentDescription = null,
                                tint = Color.Gray,
                                modifier = Modifier.size(20.sdp)
                            )
                        }
                    )
                }
                Box(modifier = Modifier.weight(1f)) {
                    SharedFormField(
                        label = "Time", placeholder = "",
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_clock),
                                contentDescription = null,
                                tint = Color.Gray,
                                modifier = Modifier.size(20.sdp)
                            )
                        }
                    )
                }
            }

            // Priority Level Selector
            Text(
                "Priority Level",
                fontSize = 14.ssp,
                color = TextPrimary,
                fontWeight = FontWeight.Medium,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 8.sdp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.sdp),
                horizontalArrangement = Arrangement.spacedBy(8.sdp)
            ) {
                priorities.forEach { priority ->
                    PriorityButton(
                        text = priority,
                        isSelected = selectedPriority == priority,
                        onClick = { selectedPriority = priority },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Notes Field
            SharedFormField(
                label = "Notes",
                placeholder = "Add any additional details",
                isMultiline = true
            )

            // Quick Select Pills
            Text(
                "Quick Select",
                fontSize = 14.ssp,
                color = TextPrimary,
                fontWeight = FontWeight.Medium,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 8.sdp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.sdp),
                horizontalArrangement = Arrangement.spacedBy(8.sdp)
            ) {
                QuickSelectPill(text = "Today", onClick = { /* logic */ })
                QuickSelectPill(text = "Tomorrow", onClick = { /* logic */ })
                QuickSelectPill(text = "Next Week", onClick = { /* logic */ })
            }
        }

        // 3. Bottom Action Button
        Spacer(modifier = Modifier.height(16.sdp))
        Button(
            onClick = onSave,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.sdp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF38C996)), // Light green matching design
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
                    "Create Reminder",
                    color = Color.White,
                    fontSize = 16.ssp,
                    fontWeight = FontWeight.Normal,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

// Helper: Custom Priority Segmented Button
@Composable
fun PriorityButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val borderColor = if (isSelected) Color(0xFF26C6DA) else Color(0xFFEEEEEE) // Cyan when active
    val textColor = if (isSelected) TextPrimary else Color.Gray

    Surface(
        modifier = modifier
            .height(44.sdp)
            .clickable { onClick() },
        shape = RoundedCornerShape(8.sdp),
        color = Color.White,
        border = BorderStroke(1.sdp, borderColor)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(16.sdp)
                )
                Spacer(modifier = Modifier.width(4.sdp))
            }
            Text(
                text = text,
                color = textColor,
                fontSize = 14.ssp,
                fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

// Helper: Quick Select Pill
@Composable
fun QuickSelectPill(text: String, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .height(36.sdp)
            .clickable { onClick() },
        shape = RoundedCornerShape(8.sdp),
        color = Color.White,
        border = BorderStroke(1.sdp, Color(0xFFEEEEEE))
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(horizontal = 16.sdp)) {
            Text(
                text = text,
                color = TextSecondary,
                fontSize = 13.ssp,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

// Reusable Upgraded Form Field Component
// Reusable Upgraded Form Field Component
// Reusable Upgraded Form Field Component utilizing BasicTextField to prevent clipping
@Composable
fun SharedFormField(
    label: String,
    placeholder: String,
    initialValue: String = "",
    isDropdown: Boolean = false,
    isMultiline: Boolean = false,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: String? = null
) {
    var textState by remember { mutableStateOf(initialValue) }
    var isFocused by remember { mutableStateOf(false) } // Track focus for the border color

    Column(modifier = Modifier.padding(bottom = 12.sdp)) {
        Text(
            text = label,
            fontSize = 13.ssp,
            color = TextPrimary,
            fontWeight = FontWeight.Medium,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 6.sdp)
        )

        BasicTextField(
            value = textState,
            onValueChange = { textState = it },
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 13.ssp,
                color = TextPrimary
            ),
            singleLine = !isMultiline,
            readOnly = isDropdown,
            modifier = Modifier
                .fillMaxWidth()
                .height(if (isMultiline) 100.sdp else 36.sdp) // Slim height works perfectly here
                .onFocusChanged { isFocused = it.isFocused }, // Listen for focus events
            decorationBox = { innerTextField ->
                // Custom Decoration Box
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .border(
                            width = if (isFocused && !isDropdown) 1.5.sdp else 1.sdp,
                            color = if (isFocused && !isDropdown) HousewiseGreen else Color(0xFFE0E0E0),
                            shape = RoundedCornerShape(8.sdp)
                        )
                        .background(Color.White, RoundedCornerShape(8.sdp))
                        .padding(
                            horizontal = 12.sdp,
                            vertical = if (isMultiline) 12.sdp else 0.sdp // 0 vertical padding ensures centering
                        ),
                    verticalAlignment = if (isMultiline) Alignment.Top else Alignment.CenterVertically
                ) {
                    // Leading Content (Icon or Prefix)
                    if (leadingIcon != null) {
                        leadingIcon()
                        Spacer(modifier = Modifier.width(8.sdp))
                    } else if (prefix != null) {
                        Text(
                            text = prefix,
                            color = Color.Gray,
                            fontSize = 13.ssp,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.width(8.sdp))
                    }

                    // Text Field & Placeholder
                    Box(modifier = Modifier.weight(1f)) {
                        if (textState.isEmpty()) {
                            Text(
                                text = placeholder,
                                color = Color.LightGray,
                                fontSize = 13.ssp,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        innerTextField()
                    }

                    // Trailing Content (Icon or Dropdown Chevron)
                    if (trailingIcon != null) {
                        Spacer(modifier = Modifier.width(8.sdp))
                        trailingIcon()
                    } else if (isDropdown) {
                        Spacer(modifier = Modifier.width(8.sdp))
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "Dropdown",
                            tint = Color.Gray,
                            modifier = Modifier.size(20.sdp)
                        )
                    }
                }
            }
        )
    }
}