package com.housewise.feature.dashboard.tasks

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.SentimentNeutral
import androidx.compose.material.icons.filled.SentimentVeryDissatisfied
import androidx.compose.material.icons.filled.SentimentVerySatisfied
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.housewise.R
import com.housewise.core.components.HousewiseButton
import com.housewise.core.theme.HousewiseDarkGreen
import com.housewise.core.theme.HousewiseGreen
import com.housewise.core.theme.StatusRed
import com.housewise.core.theme.TextPrimary
import com.housewise.core.utils.sdp
import com.housewise.core.utils.ssp
import com.housewise.feature.dashboard.tasks.presentation.InitiateTaskViewModel
import com.housewise.feature.dashboard.tasks.presentation.InventorySubItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InitiateTaskDetailScreen(
    onBackClick: () -> Unit,
    viewModel: InitiateTaskViewModel = viewModel()
) {
    val inventoryData by viewModel.inventoryData.collectAsState()
    var mediaList by remember { mutableStateOf(listOf<Int>()) }

    val categories = listOf("Living Room", "Bedroom", "Kitchen", "Added example")
    var selectedCategory by remember { mutableStateOf(categories[0]) }

    val totalItemCount = viewModel.getTotalItems()
    val isSaveVisible = totalItemCount > 0

    // States for Overlays
    var selectedSubItemForComment by remember { mutableStateOf<Pair<String, InventorySubItem>?>(null) }
    var selectedSubItemForCamera by remember { mutableStateOf<Pair<String, InventorySubItem>?>(null) }

    // Native Gallery Picker
    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickMultipleVisualMedia()) { uris ->
            if (uris.isNotEmpty()) {
                selectedSubItemForCamera?.let { (itemName, subItem) ->
                    viewModel.addPhotosToSubItem(selectedCategory, itemName, subItem.id, uris.size)
                }
                selectedSubItemForCamera = null // Close the custom camera overlay
            }
        }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            "Repeat inspection report",
                            fontSize = 18.ssp,
                            fontWeight = FontWeight.Medium,
                            style = MaterialTheme.typography.titleMedium
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onBackClick) {
                            Icon(
                                Icons.Default.ArrowBackIosNew,
                                "Back",
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
                                Icons.Default.QuestionMark,
                                "Help",
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
                    Box(modifier = Modifier
                        .background(Color.White)
                        .padding(16.sdp)) {
                        HousewiseButton(text = "Save", onClick = { /* Save action to main DB */ })
                    }
                }
            }
        ) { padding ->
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(padding)) {
                // Search Bar
                OutlinedTextField(
                    value = "", onValueChange = {},
                    placeholder = { Text("Search item", color = Color.Gray, fontSize = 14.ssp) },
                    leadingIcon = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(start = 16.sdp)
                        ) {
                            Icon(
                                painterResource(id = R.drawable.ic_search),
                                "Search",
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.sdp)
                        .padding(horizontal = 16.sdp),
                    shape = RoundedCornerShape(12.sdp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        unfocusedBorderColor = Color(0xFFE0E0E0),
                        focusedBorderColor = HousewiseDarkGreen
                    ),
                    singleLine = true
                )

                // Category Tabs
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 16.sdp, vertical = 12.sdp),
                    horizontalArrangement = Arrangement.spacedBy(8.sdp)
                ) {
                    items(categories) { category ->
                        CategoryChip(
                            title = category,
                            isSelected = category == selectedCategory,
                            onClick = { selectedCategory = category }
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
                            "Add Media:",
                            fontSize = 16.ssp,
                            style = MaterialTheme.typography.titleLarge,
                            color = TextPrimary,
                            modifier = Modifier.padding(vertical = 12.sdp)
                        )
                        val stroke = Stroke(
                            width = 3f,
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(72.sdp)
                                .drawBehind {
                                    drawRoundRect(
                                        color = Color(0xFFE0E0E0),
                                        style = stroke,
                                        cornerRadius = CornerRadius(12.dp.toPx())
                                    )
                                }
                                .padding(horizontal = 12.sdp),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                if (mediaList.isEmpty()) {
                                    Icon(
                                        Icons.Default.CameraAlt,
                                        null,
                                        tint = Color.LightGray,
                                        modifier = Modifier.size(20.sdp)
                                    )
                                    Spacer(modifier = Modifier.width(12.sdp))
                                    Text(
                                        "Add photo/video",
                                        color = Color.LightGray,
                                        fontSize = 14.ssp,
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                                Image(
                                    painter = painterResource(id = R.drawable.ic_plus_circle),
                                    contentDescription = "Add Media",
                                    modifier = Modifier
                                        .size(32.sdp)
                                        .clip(CircleShape)
                                        .clickable { /* Logic */ })
                            }
                        }
                        Spacer(modifier = Modifier.height(24.sdp))
                    }

                    item {
                        Text(
                            "Add Inventory:",
                            fontSize = 16.ssp,
                            style = MaterialTheme.typography.titleLarge,
                            color = TextPrimary,
                            modifier = Modifier.padding(bottom = 12.sdp)
                        )
                    }

                    val inventoryBaseItems = listOf(
                        "Chairs" to R.drawable.ic_chair,
                        "Tables" to R.drawable.ic_table,
                        "TV/Monitor" to R.drawable.ic_tv_monitor,
                        "Cabinet/Storage" to R.drawable.ic_cabinate,
                        "Sofa" to R.drawable.ic_sofa,
                        "Fan" to R.drawable.ic_fan
                    )

                    items(inventoryBaseItems) { item ->
                        val categoryData = inventoryData[selectedCategory] ?: emptyMap()
                        val subItemsList = categoryData[item.first] ?: emptyList()

                        InventoryCard(
                            name = item.first,
                            iconResId = item.second,
                            subItems = subItemsList,
                            onAddClick = { viewModel.addItem(selectedCategory, item.first) },
                            onRemoveClick = { viewModel.removeItem(selectedCategory, item.first) },
                            onRemoveSubItem = { subItemId ->
                                viewModel.removeSubItem(
                                    selectedCategory,
                                    item.first,
                                    subItemId
                                )
                            },
                            onUpdateSubItem = { updatedSubItem ->
                                viewModel.updateSubItem(
                                    selectedCategory,
                                    item.first,
                                    updatedSubItem
                                )
                            },
                            onCommentClick = { subItem ->
                                selectedSubItemForComment = Pair(item.first, subItem)
                            },
                            onCameraClick = { subItem ->
                                selectedSubItemForCamera = Pair(item.first, subItem)
                            }
                        )
                    }
                }
            }
        }

        // --- 3. COMMENT BOTTOM SHEET ---
        if (selectedSubItemForComment != null) {
            val (itemName, subItem) = selectedSubItemForComment!!

            ModalBottomSheet(
                onDismissRequest = { selectedSubItemForComment = null },
                containerColor = Color.White,
                dragHandle = null,
                shape = RoundedCornerShape(topStart = 24.sdp, topEnd = 24.sdp)
            ) {
                var commentText by remember { mutableStateOf(subItem.comment) }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.sdp, vertical = 24.sdp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Remarks/Comments",
                            fontSize = 18.ssp,
                            color = Color.Black,
                            fontWeight = FontWeight.Medium
                        )
                        IconButton(onClick = {
                            selectedSubItemForComment = null
                        }) {
                            Icon(
                                Icons.Default.Close,
                                "Close",
                                tint = Color.Black,
                                modifier = Modifier.size(24.sdp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.sdp))

                    OutlinedTextField(
                        value = commentText, onValueChange = { commentText = it },
                        placeholder = {
                            Text(
                                "E.g., Minor scratches on the surface...",
                                color = Color.Gray,
                                fontSize = 14.ssp
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.sdp),
                        shape = RoundedCornerShape(12.sdp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color(0xFFE0E0E0),
                            focusedBorderColor = HousewiseDarkGreen,
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White
                        )
                    )

                    Spacer(modifier = Modifier.height(24.sdp))

                    HousewiseButton(
                        text = "Add or update comment",
                        onClick = {
                            val updatedItem = subItem.copy(comment = commentText)
                            viewModel.updateSubItem(selectedCategory, itemName, updatedItem)
                            selectedSubItemForComment = null
                        }
                    )
                    Spacer(modifier = Modifier.height(16.sdp))
                }
            }
        }

        // --- 4. CUSTOM CAMERA OVERLAY ---
        if (selectedSubItemForCamera != null) {
            val (itemName, subItem) = selectedSubItemForCamera!!

            CustomCameraOverlay(
                onBack = { selectedSubItemForCamera = null },
                onGallerySelect = {
                    galleryLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                },
                onCapture = {
                    // Simulate taking a photo by adding +1
                    viewModel.addPhotosToSubItem(selectedCategory, itemName, subItem.id, 1)
                    selectedSubItemForCamera = null
                }
            )
        }
    }
}

// --- 5. INVENTORY CARD COMPOSABLE ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryCard(
    name: String,
    iconResId: Int,
    subItems: List<InventorySubItem>,
    onAddClick: () -> Unit,
    onRemoveClick: () -> Unit,
    onRemoveSubItem: (String) -> Unit,
    onUpdateSubItem: (InventorySubItem) -> Unit,
    onCommentClick: (InventorySubItem) -> Unit,
    onCameraClick: (InventorySubItem) -> Unit
) {
    val count = subItems.size

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
                    .padding(
                        start = 12.sdp,
                        top = 12.sdp,
                        end = 16.sdp,
                        bottom = 12.sdp
                    )
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
                        painterResource(id = iconResId),
                        name,
                        tint = Color.DarkGray,
                        modifier = Modifier.size(24.sdp)
                    )
                }

                Spacer(modifier = Modifier.width(16.sdp))
                Text(
                    name,
                    fontSize = 14.ssp,
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
                        ) { Text("—", color = Color.Gray, fontSize = 16.ssp) }
                        Text(
                            "$count",
                            fontSize = 16.ssp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(horizontal = 12.sdp)
                        )
                        Image(
                            painter = painterResource(id = R.drawable.ic_plus_circle),
                            contentDescription = "Add",
                            modifier = Modifier
                                .size(32.sdp)
                                .clip(CircleShape)
                                .clickable { onAddClick() })
                    }
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.ic_plus_circle),
                        contentDescription = "Add",
                        modifier = Modifier
                            .size(32.sdp)
                            .clip(CircleShape)
                            .clickable { onAddClick() })
                }
            }

            // Sub-items List
            if (count > 0) {
                subItems.forEach { subItem ->
                    HorizontalDivider(
                        color = Color(0xFFF5F5F5),
                        thickness = 1.sdp,
                        modifier = Modifier.padding(horizontal = 12.sdp)
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.sdp, vertical = 12.sdp)
                    ) {
                        // Title, Delete, and Expand Toggle
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = subItem.name,
                                modifier = Modifier.weight(1f),
                                fontSize = 14.ssp,
                                style = MaterialTheme.typography.bodyLarge
                            )

                            Icon(
                                Icons.Default.Delete, "Delete", tint = Color.Black,
                                modifier = Modifier
                                    .size(20.sdp)
                                    .clickable { onRemoveSubItem(subItem.id) }
                            )
                            Spacer(modifier = Modifier.width(8.sdp))
                            IconButton(
                                onClick = { onUpdateSubItem(subItem.copy(isExpanded = !subItem.isExpanded)) },
                                modifier = Modifier.size(24.sdp)
                            ) {
                                Icon(
                                    imageVector = if (subItem.isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                    contentDescription = "Expand", tint = Color.LightGray
                                )
                            }
                        }

                        // Collapsible Action Row
                        if (subItem.isExpanded) {
                            Spacer(modifier = Modifier.height(12.sdp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(16.sdp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Emoji Selector
                                Row(
                                    modifier = Modifier
                                        .border(
                                            1.sdp,
                                            Color(0xFFE0E0E0),
                                            RoundedCornerShape(8.sdp)
                                        )
                                        .padding(4.sdp),
                                    horizontalArrangement = Arrangement.spacedBy(8.sdp)
                                ) {
                                    Icon(
                                        Icons.Default.SentimentVeryDissatisfied,
                                        "Sad",
                                        tint = if (subItem.satisfaction == 1) StatusRed else Color.LightGray,
                                        modifier = Modifier
                                            .size(24.sdp)
                                            .clickable { onUpdateSubItem(subItem.copy(satisfaction = 1)) })
                                    Icon(
                                        Icons.Default.SentimentNeutral,
                                        "Neutral",
                                        tint = if (subItem.satisfaction == 2) Color(0xFFFFA000) else Color.LightGray,
                                        modifier = Modifier
                                            .size(24.sdp)
                                            .clickable { onUpdateSubItem(subItem.copy(satisfaction = 2)) })
                                    Icon(
                                        Icons.Default.SentimentVerySatisfied,
                                        "Happy",
                                        tint = if (subItem.satisfaction == 3) HousewiseGreen else Color.LightGray,
                                        modifier = Modifier
                                            .size(24.sdp)
                                            .clickable { onUpdateSubItem(subItem.copy(satisfaction = 3)) })
                                }

                                // Comment Icon with BADGE
                                BadgedBox(
                                    badge = {
                                        if (subItem.comment.isNotEmpty()) {
                                            Badge(containerColor = HousewiseDarkGreen) {
                                                Icon(
                                                    Icons.Default.Check,
                                                    null,
                                                    tint = Color.White,
                                                    modifier = Modifier.size(10.sdp)
                                                )
                                            }
                                        }
                                    }
                                ) {
                                    Icon(
                                        imageVector = if (subItem.comment.isNotEmpty()) Icons.Default.ChatBubble else Icons.Default.ChatBubbleOutline,
                                        contentDescription = "Comment",
                                        tint = if (subItem.comment.isNotEmpty()) HousewiseDarkGreen else Color.Gray,
                                        modifier = Modifier
                                            .size(24.sdp)
                                            .clickable { onCommentClick(subItem) }
                                    )
                                }

                                // Camera Icon with BADGE
                                BadgedBox(
                                    badge = {
                                        if (subItem.photoCount > 0) {
                                            Badge(containerColor = HousewiseDarkGreen) {
                                                Text(
                                                    "${subItem.photoCount}",
                                                    color = Color.White,
                                                    fontSize = 10.ssp
                                                )
                                            }
                                        }
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.PhotoCamera,
                                        contentDescription = "Photo",
                                        tint = if (subItem.photoCount > 0) HousewiseDarkGreen else Color.Gray,
                                        modifier = Modifier
                                            .size(24.sdp)
                                            .clickable { onCameraClick(subItem) }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// --- 6. CUSTOM INSTAGRAM-STYLE CAMERA OVERLAY ---
@Composable
fun CustomCameraOverlay(
    onBack: () -> Unit,
    onCapture: () -> Unit,
    onGallerySelect: () -> Unit
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)) {
        // Fake Camera Preview
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray))

        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.sdp)
                .padding(top = 24.sdp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.ArrowBackIosNew,
                "Back",
                tint = Color.White,
                modifier = Modifier
                    .size(20.sdp)
                    .clickable { onBack() })
            Spacer(modifier = Modifier.width(8.sdp))
            Text("Back", color = Color.White, fontSize = 16.ssp)
        }

        // Bottom Controls
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = 40.sdp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Hold your phone still as you click the picture, So\nthat pictures stays noiseless and easily verifiable.",
                color = Color.White,
                textAlign = TextAlign.Center,
                fontSize = 12.ssp,
                modifier = Modifier.padding(horizontal = 24.sdp)
            )
            Spacer(modifier = Modifier.height(32.sdp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.sdp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.sdp)
                        .border(1.sdp, Color.White, CircleShape)
                        .clickable { onGallerySelect() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.PhotoLibrary,
                        "Gallery",
                        tint = Color.White,
                        modifier = Modifier.size(20.sdp)
                    )
                }

                Box(
                    modifier = Modifier
                        .size(72.sdp)
                        .border(4.sdp, Color.LightGray, CircleShape)
                        .padding(4.sdp)
                        .background(Color.White, CircleShape)
                        .clickable { onCapture() }
                )

                Box(
                    modifier = Modifier
                        .size(48.sdp)
                        .border(1.sdp, Color.White, CircleShape)
                        .clickable { /* Flip Logic */ },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Sync,
                        "Flip",
                        tint = Color.White,
                        modifier = Modifier.size(20.sdp)
                    )
                }
            }
        }
    }
}

// --- 7. CATEGORY CHIP COMPOSABLE ---
@Composable
fun CategoryChip(title: String, isSelected: Boolean, onClick: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(8.sdp),
        color = if (isSelected) HousewiseDarkGreen else Color.White,
        border = if (!isSelected) BorderStroke(1.sdp, Color(0xFFE0E0E0)) else null,
        modifier = Modifier.clickable { onClick() }
    ) {
        Text(
            text = title,
            color = if (isSelected) Color.White else Color.Gray,
            modifier = Modifier.padding(horizontal = 16.sdp, vertical = 6.sdp),
            fontSize = 12.ssp,
            fontWeight = FontWeight.Normal,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}