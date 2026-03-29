package com.housewise.feature.dashboard.filter

// Import your responsive utils
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.housewise.R
import com.housewise.core.theme.TextPrimary
import com.housewise.core.theme.TextSecondary
import com.housewise.core.utils.sdp
import com.housewise.core.utils.ssp

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun FilterSortScreen(
    contextType: String, // Accepts "tasks", "leads", or "brokers"
    onBackClick: () -> Unit,
    onApplyClick: () -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) } // 0 = Filter, 1 = Sort

    // Generic Status State
    var selectedStatus by remember { mutableStateOf(setOf<String>()) }

    // Dynamic Sort Options based on the screen context
    val sortOptions = remember(contextType) {
        when (contextType) {
            "tasks" -> listOf(
                "Date (Newest First)",
                "Date (Oldest First)",
                "Priority (High to Low)",
                "Priority (Low to High)",
                "Name (A to Z)",
                "Name (Z to A)"
            )

            "leads" -> listOf(
                "Date Added (Newest)",
                "Date Added (Oldest)",
                "Name (A to Z)",
                "Name (Z to A)",
                "Status"
            )

            "brokers" -> listOf("Name (A to Z)", "Name (Z to A)", "Status (Active First)", "City")
            else -> emptyList()
        }
    }
    var selectedSort by remember { mutableStateOf(sortOptions.firstOrNull() ?: "") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    // Dynamic Title
                    val titleText = when (contextType) {
                        "tasks" -> "Filter & Sort Tasks"
                        "leads" -> "Filter & Sort Leads"
                        "brokers" -> "Filter Brokers"
                        else -> "Filter & Sort"
                    }
                    Text(
                        text = titleText,
                        fontSize = 18.ssp,
                        fontWeight = FontWeight.Medium,
                        style = MaterialTheme.typography.titleLarge,
                        color = TextPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.Default.ArrowBackIosNew,
                            contentDescription = "Back",
                            modifier = Modifier.size(20.sdp),
                            tint = TextPrimary
                        )
                    }
                },
                actions = {
                    TextButton(onClick = {
                        selectedStatus = emptySet()
                        selectedSort = sortOptions.firstOrNull() ?: ""
                    }) {
                        Text(
                            "Reset",
                            color = TextSecondary,
                            fontSize = 14.ssp,
                            fontWeight = FontWeight.Medium,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.sdp),
                horizontalArrangement = Arrangement.spacedBy(12.sdp)
            ) {
                OutlinedButton(
                    onClick = { selectedStatus = emptySet() },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.sdp),
                    shape = RoundedCornerShape(8.sdp),
                    border = BorderStroke(1.sdp, Color(0xFFE0E0E0))
                ) {
                    Text(
                        "Clear All",
                        color = Color.Gray,
                        fontSize = 14.ssp,
                        fontWeight = FontWeight.Normal,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                Button(
                    onClick = onApplyClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.sdp),
                    shape = RoundedCornerShape(8.sdp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF38C996))
                ) {
                    Text(
                        "Apply Filters",
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
        ) {
            // Custom Tabs
            Row(modifier = Modifier.fillMaxWidth()) {
                FilterTab(
                    "Filter by",
                    R.drawable.ic_filter_icon,
                    selectedTab == 0,
                    { selectedTab = 0 },
                    Modifier.weight(1f)
                )
                FilterTab(
                    "Sort by",
                    R.drawable.ic_sort,
                    selectedTab == 1,
                    { selectedTab = 1 },
                    Modifier.weight(1f)
                ) // Replace ic_sort with your actual icon
            }
            HorizontalDivider(color = Color(0xFFEEEEEE), thickness = 1.sdp)

            // Content Area
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.sdp)
            ) {
                if (selectedTab == 0) {
                    // DYNAMIC FILTER CONTENT
                    when (contextType) {
                        "tasks" -> {
                            TaskFilters(selectedStatus) {
                                selectedStatus = toggleSet(selectedStatus, it)
                            }
                        }

                        "leads" -> {
                            LeadFilters(selectedStatus) {
                                selectedStatus = toggleSet(selectedStatus, it)
                            }
                        }

                        "brokers" -> {
                            BrokerFilters(selectedStatus) {
                                selectedStatus = toggleSet(selectedStatus, it)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.sdp))
                    Text(
                        "Date Range",
                        fontSize = 14.ssp,
                        color = TextPrimary,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 8.sdp)
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(12.sdp)) {
                        DateBox("Start Date", Modifier.weight(1f))
                        DateBox("End Date", Modifier.weight(1f))
                    }

                } else {
                    // SORT CONTENT
                    Text(
                        "Sort Options",
                        fontSize = 14.ssp,
                        color = TextPrimary,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 12.sdp)
                    )
                    sortOptions.forEach { option ->
                        SortOptionCard(
                            text = option,
                            isSelected = selectedSort == option,
                            onClick = { selectedSort = option }
                        )
                    }
                }
            }
        }
    }
}

// --- DYNAMIC FILTER BLOCKS ---

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TaskFilters(selectedStatus: Set<String>, onStatusToggle: (String) -> Unit) {
    Text(
        "Task Type",
        fontSize = 14.ssp,
        color = TextPrimary,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(bottom = 8.sdp)
    )
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.sdp),
        shape = RoundedCornerShape(8.sdp),
        colors = CardDefaults.outlinedCardColors(containerColor = Color(0xFFFFFFFF)),
        border = BorderStroke(1.sdp, Color(0xFFEEEEEE))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.sdp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(32.sdp) // Set the size of the rounded box
                    .background(
                        Color(0xFFF5F5F5),
                        RoundedCornerShape(8.sdp)
                    ), // Light gray background with rounded corners
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_filter_icon),
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(16.sdp) // Slightly smaller so it fits nicely inside the box
                )
            }
            Spacer(modifier = Modifier.width(8.sdp))
            Text(
                "All Tasks",
                fontSize = 14.ssp,
                color = TextPrimary,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyLarge
            )
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                tint = Color.Gray
            )
        }
    }

    Spacer(modifier = Modifier.height(24.sdp))
    Text(
        "Status",
        fontSize = 14.ssp,
        color = TextPrimary,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(bottom = 8.sdp)
    )
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.sdp),
        verticalArrangement = Arrangement.spacedBy(8.sdp),
        modifier = Modifier.fillMaxWidth()
    ) {
        StatusChip(
            "New",
            Color(0xFF38C996),
            selectedStatus.contains("New")
        ) { onStatusToggle("New") }
        StatusChip(
            "In Progress",
            Color(0xFFFFB300),
            selectedStatus.contains("In Progress")
        ) { onStatusToggle("In Progress") }
        StatusChip(
            "Completed",
            Color(0xFF9E9E9E),
            selectedStatus.contains("Completed")
        ) { onStatusToggle("Completed") }
        StatusChip(
            "Cancelled",
            Color(0xFFE57373),
            selectedStatus.contains("Cancelled")
        ) { onStatusToggle("Cancelled") }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LeadFilters(selectedStatus: Set<String>, onStatusToggle: (String) -> Unit) {
    Text(
        "LeadResponse Source",
        fontSize = 14.ssp,
        color = TextPrimary,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(bottom = 8.sdp)
    )
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.sdp),
        shape = RoundedCornerShape(8.sdp),
        colors = CardDefaults.outlinedCardColors(containerColor = Color(0xFFFFFFFF)),
        border = BorderStroke(1.sdp, Color(0xFFEEEEEE))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.sdp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "All Sources",
                fontSize = 14.ssp,
                color = TextPrimary,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyLarge
            )
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                tint = Color.Gray
            )
        }
    }

    Spacer(modifier = Modifier.height(24.sdp))
    Text(
        "Status",
        fontSize = 14.ssp,
        color = TextPrimary,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(bottom = 8.sdp)
    )
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.sdp),
        verticalArrangement = Arrangement.spacedBy(8.sdp),
        modifier = Modifier.fillMaxWidth()
    ) {
        StatusChip(
            "Follow-up Required",
            Color(0xFF38C996),
            selectedStatus.contains("Follow-up Required")
        ) { onStatusToggle("Follow-up Required") }
        StatusChip(
            "Not Interested",
            Color(0xFFE57373),
            selectedStatus.contains("Not Interested")
        ) { onStatusToggle("Not Interested") }
        StatusChip("Closed", Color(0xFF9E9E9E), selectedStatus.contains("Closed")) {
            onStatusToggle(
                "Closed"
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BrokerFilters(selectedStatus: Set<String>, onStatusToggle: (String) -> Unit) {
    Text(
        "Status",
        fontSize = 14.ssp,
        color = TextPrimary,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(bottom = 8.sdp)
    )
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.sdp),
        verticalArrangement = Arrangement.spacedBy(8.sdp),
        modifier = Modifier.fillMaxWidth()
    ) {
        StatusChip("Active", Color(0xFF38C996), selectedStatus.contains("Active")) {
            onStatusToggle(
                "Active"
            )
        }
        StatusChip(
            "Inactive",
            Color(0xFFE57373),
            selectedStatus.contains("Inactive")
        ) { onStatusToggle("Inactive") }
    }
}

// --- HELPER COMPOSABLES ---
@Composable
fun FilterTab(
    title: String,
    iconRes: Int,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val color = if (isSelected) TextPrimary else Color.Gray
    Column(
        modifier = modifier.clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.padding(vertical = 16.sdp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(16.sdp)
            )
            Spacer(modifier = Modifier.width(8.sdp))
            Text(
                text = title,
                color = color,
                fontSize = 14.ssp,
                fontWeight = FontWeight.Medium,
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.sdp)
                .background(if (isSelected) TextPrimary else Color.Transparent)
        )
    }
}

@Composable
fun StatusChip(text: String, dotColor: Color, isSelected: Boolean, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .height(36.sdp)
            .clickable { onClick() },
        shape = RoundedCornerShape(8.sdp),
        color = if (isSelected) Color(0xFFF0FDF4) else Color.White, // Light green bg when selected
        border = BorderStroke(1.sdp, if (isSelected) dotColor else Color(0xFFEEEEEE))
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.sdp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(8.sdp)
                    .background(dotColor, CircleShape)
            )
            Spacer(modifier = Modifier.width(8.sdp))
            Text(
                text = text,
                color = TextPrimary,
                fontSize = 13.ssp,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun DateBox(label: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = label,
            color = Color.Gray,
            fontSize = 12.ssp,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 6.sdp)
        )
        OutlinedCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.sdp),
            shape = RoundedCornerShape(8.sdp),
            colors = CardDefaults.outlinedCardColors(containerColor = Color.White),
            border = BorderStroke(1.sdp, Color(0xFFEEEEEE))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.sdp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_calendar_filter_by),
                    contentDescription = null,
                    modifier = Modifier.size(18.sdp)
                )
            }
        }
    }
}

@Composable
fun SortOptionCard(text: String, isSelected: Boolean, onClick: () -> Unit) {
    val selectedColor = Color(0xFF00BFA5) // Teal color from the design
    val selectedColorText = Color(0xFF033333) // Teal color from the design
    val unselectedColor = Color(0xFFE0E0E0)

    val borderColor = if (isSelected) selectedColor else unselectedColor
    val textColor = if (isSelected) selectedColorText else TextPrimary
    val iconTint = if (isSelected) selectedColorText else Color.DarkGray

    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.sdp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.sdp),
        colors = CardDefaults.outlinedCardColors(containerColor = Color.White),
        border = BorderStroke(if (isSelected) 1.5.sdp else 1.sdp, borderColor)
    ) {
        Row(
            modifier = Modifier
                .padding(16.sdp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 1. Rounded background for the icon
            Box(
                modifier = Modifier
                    .size(36.sdp) // Box size
                    .background(Color(0xFFF5F5F5), RoundedCornerShape(8.sdp)), // Light gray with rounded corners
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_calendar_filter_type),
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(20.sdp)
                )
            }

            Spacer(modifier = Modifier.width(12.sdp))

            Text(
                text = text,
                color = textColor,
                fontSize = 14.ssp,
                fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyLarge
            )

            // 2. Selected checked box with circle
            if (isSelected) {
                Box(
                    modifier = Modifier
                        .size(20.sdp)
                        .border(1.5.sdp, selectedColor, CircleShape), // Teal circular border
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = selectedColor,
                        modifier = Modifier.size(12.sdp) // Smaller checkmark perfectly centered inside
                    )
                }
            } else {
                Box(
                    modifier = Modifier
                        .size(20.sdp)
                        .border(1.5.sdp, unselectedColor, CircleShape) // Gray circular border
                )
            }
        }
    }
}

fun toggleSet(set: Set<String>, item: String): Set<String> {
    return if (set.contains(item)) set - item else set + item
}