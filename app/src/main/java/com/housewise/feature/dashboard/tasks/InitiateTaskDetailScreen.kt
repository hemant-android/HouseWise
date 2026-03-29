package com.housewise.feature.dashboard.tasks

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SentimentNeutral
import androidx.compose.material.icons.filled.SentimentVeryDissatisfied
import androidx.compose.material.icons.filled.SentimentVerySatisfied
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.housewise.R
import com.housewise.core.components.HousewiseButton
import com.housewise.core.theme.HousewiseDarkGreen
import com.housewise.core.theme.HousewiseGreen
import com.housewise.core.theme.TextPrimary
// Import responsive utils
import com.housewise.core.utils.sdp
import com.housewise.core.utils.ssp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InitiateTaskDetailScreen(onBackClick: () -> Unit) {
    var inventoryCounts by remember { mutableStateOf(mapOf<String, Int>()) }
    var mediaList by remember { mutableStateOf(listOf<Int>()) }

    // FIXED: Added state to track the currently selected category tab
    val categories = listOf("Living Room", "Bedroom", "Kitchen", "Added example")
    var selectedCategory by remember { mutableStateOf(categories[0]) }

    val totalItemCount = inventoryCounts.values.sum()
    val isSaveVisible = totalItemCount > 0

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Repeat inspection report",
                        fontSize = 18.ssp,
                        fontWeight = FontWeight.Medium,
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "Back",
                            modifier = Modifier.size(24.sdp)
                        )
                    }
                },
                actions = {
                    Box(
                        modifier = Modifier
                            .padding(end = 16.sdp)
                            .size(28.sdp)
                            .background(HousewiseDarkGreen, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.QuestionMark,
                            contentDescription = "Help",
                            tint = Color.White,
                            modifier = Modifier.size(16.sdp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = Color.White,
        bottomBar = {
            if (isSaveVisible) {
                Box(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(16.sdp)
                ) {
                    HousewiseButton(
                        text = "Save",
                        onClick = { /* Save action */ }
                    )
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Search Bar
            OutlinedTextField(
                value = "",
                onValueChange = {},
                placeholder = {
                    Text(
                        "Search item",
                        color = Color.Gray,
                        fontSize = 14.ssp,
                        style = MaterialTheme.typography.bodyLarge
                    )
                },
                leadingIcon = {
                    // Wrapped in a Row to add the vertical separator
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
                // Intentionally left out trailingIcon here!
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.sdp)
                    .padding(horizontal = 16.sdp),
                shape = RoundedCornerShape(12.sdp), // Pill shape to match other screens
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    unfocusedBorderColor = Color(0xFFE0E0E0),
                    focusedBorderColor = HousewiseDarkGreen
                ),
                singleLine = true
            )

            // Category Tabs - FIXED to be dynamic and clickable
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.sdp, vertical = 12.sdp),
                horizontalArrangement = Arrangement.spacedBy(8.sdp)
            ) {
                items(categories) { category ->
                    CategoryChip(
                        title = category,
                        isSelected = category == selectedCategory,
                        onClick = { selectedCategory = category } // Updates state when clicked
                    )
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    start = 16.sdp,
                    end = 16.sdp,
                    bottom = if (isSaveVisible) 80.sdp else 16.sdp
                )
            ) {
                item {
                    Text(
                        text = "Add Media:",
                        fontSize = 16.ssp,
                        fontWeight = FontWeight.Normal,
                        style = MaterialTheme.typography.titleLarge,
                        color = TextPrimary,
                        modifier = Modifier.padding(vertical = 12.sdp)
                    )

                    val stroke = Stroke(
                        width = 3f,
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                    )

                    val dashedCornerRadius = 12.sdp

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(72.sdp)
                            .drawBehind {
                                drawRoundRect(
                                    color = Color(0xFFE0E0E0),
                                    style = stroke,
                                    cornerRadius = CornerRadius(dashedCornerRadius.toPx())
                                )
                            }
                            .padding(horizontal = 12.sdp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (mediaList.isEmpty()) {
                                Icon(
                                    imageVector = Icons.Default.CameraAlt,
                                    contentDescription = null,
                                    tint = Color.LightGray,
                                    modifier = Modifier.size(20.sdp)
                                )
                                Spacer(modifier = Modifier.width(12.sdp))
                                Text(
                                    text = "Add photo/video",
                                    color = Color.LightGray,
                                    fontSize = 14.ssp,
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.weight(1f)
                                )
                            } else {
                                mediaList.forEach { resId ->
                                    MediaThumbnail(resId)
                                    Spacer(modifier = Modifier.width(8.sdp))
                                }
                                Spacer(modifier = Modifier.weight(1f))
                            }

                            Image(
                                painter = painterResource(id = R.drawable.ic_plus_circle),
                                contentDescription = "Add Media",
                                modifier = Modifier
                                    .size(32.sdp)
                                    .clip(CircleShape)
                                    .clickable { /* Logic to add media */ }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(24.sdp))
                }

                item {
                    Text(
                        text = "Add Inventory:",
                        fontSize = 16.ssp,
                        fontWeight = FontWeight.Normal,
                        style = MaterialTheme.typography.titleLarge,
                        color = TextPrimary,
                        modifier = Modifier.padding(bottom = 12.sdp)
                    )
                }

                val inventoryItems = listOf(
                    "Chairs" to R.drawable.ic_chair,
                    "Tables" to R.drawable.ic_table,
                    "TV/Monitor" to R.drawable.ic_tv_monitor,
                    "Cabinet/Storage" to R.drawable.ic_cabinate,
                    "Sofa" to R.drawable.ic_sofa,
                    "Fan" to R.drawable.ic_fan
                )

                items(inventoryItems) { item ->
                    val count = inventoryCounts[item.first] ?: 0
                    InventoryCard(
                        name = item.first,
                        iconResId = item.second,
                        count = count,
                        onAddClick = {
                            inventoryCounts = inventoryCounts.toMutableMap()
                                .apply { this[item.first] = count + 1 }
                        },
                        onRemoveClick = {
                            if (count > 0) {
                                inventoryCounts = inventoryCounts.toMutableMap()
                                    .apply { this[item.first] = count - 1 }
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun InventoryCard(
    name: String,
    iconResId: Int,
    count: Int,
    onAddClick: () -> Unit,
    onRemoveClick: () -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }

    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.sdp),
        shape = RoundedCornerShape(12.sdp),
        colors = CardDefaults.outlinedCardColors(containerColor = Color.White),
        border = BorderStroke(1.sdp, Color(0xFFE0E0E0))
    ) {
        Column {
            Row(
                modifier = Modifier
                    .padding(start = 12.sdp, top = 12.sdp, end = 16.sdp, bottom = 12.sdp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.sdp)
                        .background(Color(0xFFF5F5F5), RoundedCornerShape(8.sdp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = iconResId),
                        contentDescription = name,
                        tint = Color.DarkGray,
                        modifier = Modifier.size(24.sdp)
                    )
                }

                Spacer(modifier = Modifier.width(16.sdp))

                Text(
                    text = name,
                    fontSize = 14.ssp,
                    fontWeight = FontWeight.W400,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f)
                )

                if (count > 0) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(end = 4.sdp)
                    ) {
                        IconButton(
                            onClick = onRemoveClick,
                            modifier = Modifier.size(24.sdp)
                        ) {
                            Text("—", color = Color.Gray, fontSize = 16.ssp)
                        }

                        Text(
                            text = "$count",
                            fontSize = 16.ssp,
                            fontWeight = FontWeight.Medium,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(horizontal = 12.sdp)
                        )

                        Image(
                            painter = painterResource(id = R.drawable.ic_plus_circle),
                            contentDescription = "Add",
                            modifier = Modifier
                                .size(32.sdp)
                                .clip(CircleShape)
                                .clickable { onAddClick() }
                        )
                    }
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.ic_plus_circle),
                        contentDescription = "Add",
                        modifier = Modifier
                            .size(32.sdp)
                            .clip(CircleShape)
                            .clickable { onAddClick() }
                    )
                }
            }

            if (count > 0) {
                repeat(count) { i ->
                    HorizontalDivider(
                        color = Color(0xFFF5F5F5),
                        thickness = 1.sdp,
                        modifier = Modifier.padding(horizontal = 12.sdp)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.sdp, vertical = 8.sdp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${name.dropLast(1)}-0${i + 1}",
                            modifier = Modifier.weight(1f),
                            fontSize = 14.ssp,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = Color.Black,
                            modifier = Modifier.size(20.sdp)
                        )
                        Spacer(modifier = Modifier.width(8.sdp))
                        IconButton(
                            onClick = { isExpanded = !isExpanded },
                            modifier = Modifier.size(24.sdp)
                        ) {
                            Icon(
                                imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                contentDescription = null,
                                tint = Color.LightGray
                            )
                        }
                    }

                    if (isExpanded) {
                        Row(
                            modifier = Modifier
                                .padding(start = 12.sdp, end = 12.sdp, bottom = 12.sdp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.sdp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                modifier = Modifier
                                    .border(1.sdp, Color(0xFFE0E0E0), RoundedCornerShape(8.sdp))
                                    .padding(4.sdp),
                                horizontalArrangement = Arrangement.spacedBy(8.sdp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.SentimentVeryDissatisfied,
                                    contentDescription = null,
                                    tint = Color.LightGray,
                                    modifier = Modifier.size(24.sdp)
                                )
                                Icon(
                                    imageVector = Icons.Default.SentimentNeutral,
                                    contentDescription = null,
                                    tint = Color.LightGray,
                                    modifier = Modifier.size(24.sdp)
                                )
                                Icon(
                                    imageVector = Icons.Default.SentimentVerySatisfied,
                                    contentDescription = null,
                                    tint = HousewiseGreen,
                                    modifier = Modifier.size(24.sdp)
                                )
                            }

                            BadgedBox(
                                badge = {
                                    Badge(containerColor = HousewiseDarkGreen) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = null,
                                            tint = Color.White,
                                            modifier = Modifier.size(10.sdp)
                                        )
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ChatBubbleOutline,
                                    contentDescription = null,
                                    tint = Color.Gray,
                                    modifier = Modifier.size(24.sdp)
                                )
                            }

                            BadgedBox(
                                badge = {
                                    Badge(containerColor = HousewiseDarkGreen) {
                                        Text(
                                            text = "2",
                                            color = Color.White,
                                            fontSize = 10.ssp,
                                            style = MaterialTheme.typography.labelSmall
                                        )
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PhotoCamera,
                                    contentDescription = null,
                                    tint = Color.Gray,
                                    modifier = Modifier.size(24.sdp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// FIXED: Added an onClick parameter to handle user interaction
@Composable
fun CategoryChip(title: String, isSelected: Boolean, onClick: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(8.sdp),
        color = if (isSelected) HousewiseDarkGreen else Color.White,
        border = if (!isSelected) BorderStroke(1.sdp, Color(0xFFE0E0E0)) else null,
        modifier = Modifier.clickable { onClick() } // Enables the tap action
    ) {
        Text(
            text = title,
            color = if (isSelected) Color.White else Color.Gray,
            modifier = Modifier.padding(horizontal = 16.sdp, vertical = 6.sdp),
            fontSize = 12.ssp,
            fontWeight = if (isSelected) FontWeight.Normal else FontWeight.Normal,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun MediaThumbnail(resId: Int) {
    Box(modifier = Modifier.size(60.sdp)) {
        Image(
            painter = painterResource(id = resId),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(8.sdp))
        )

        Surface(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = 4.sdp, y = 4.sdp)
                .size(20.sdp),
            shape = CircleShape,
            color = Color.Red,
            border = BorderStroke(1.sdp, Color.White)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Remove",
                tint = Color.White,
                modifier = Modifier.padding(2.sdp)
            )
        }
    }
}