package com.housewise.feature.dashboard.tasks

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew // Usually looks better than default ArrowBack
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.housewise.R
import com.housewise.core.components.HousewiseButton
import com.housewise.core.theme.HousewiseDarkGreen
import com.housewise.core.theme.TextPrimary
import com.housewise.core.theme.TextSecondary
// Import responsive utils
import com.housewise.core.utils.sdp
import com.housewise.core.utils.ssp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InitiateTaskEmptyScreen(
    onBackClick: () -> Unit,
    onAddNowClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Repeat inspection report",
                        fontSize = 18.ssp, // Responsive font
                        fontWeight = FontWeight.Medium,
                        style = MaterialTheme.typography.titleMedium, // Poppins font
                        color = TextPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "Back",
                            modifier = Modifier.size(24.sdp), // Responsive icon
                            tint = TextPrimary
                        )
                    }
                },
                actions = {
                    // Help Icon Box
                    Box(
                        modifier = Modifier
                            .padding(end = 16.sdp) // Responsive padding
                            .size(28.sdp) // Responsive box size
                            .background(HousewiseDarkGreen, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.QuestionMark,
                            contentDescription = "Help",
                            tint = Color.White,
                            modifier = Modifier.size(16.sdp) // Responsive icon inside box
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = Color.White
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.sdp, vertical = 32.sdp), // Responsive outer padding
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))

            // Empty State Illustration
            Image(
                painter = painterResource(id = R.drawable.img_initiate_task_empty),
                contentDescription = "No areas added illustration",
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(200.sdp) // Responsive image scaling
            )

            Spacer(modifier = Modifier.height(32.sdp)) // Responsive spacer

            // Empty State Text
            Text(
                text = "No area added yet",
                fontSize = 18.ssp, // Responsive font
                fontWeight = FontWeight.Medium,
                color = TextPrimary,
                style = MaterialTheme.typography.titleLarge // Poppins font
            )

            Spacer(modifier = Modifier.height(8.sdp))

            Text(
                text = "Add areas to start your inspection report",
                fontSize = 12.ssp,
                color = TextSecondary,
                style = MaterialTheme.typography.bodySmall // Poppins font
            )

            Spacer(modifier = Modifier.weight(1f))

            // Bottom Actions
            TextButton(
                onClick = { /* Skip Action */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Skip & add overall status",
                    color = TextSecondary,
                    fontSize = 14.ssp,
                    style = MaterialTheme.typography.bodySmall // Poppins font
                )
            }

            Spacer(modifier = Modifier.height(10.sdp))

            HousewiseButton(
                text = "Add now",
                onClick = onAddNowClick
            ) // HousewiseButton is already responsive based on your earlier updates
        }
    }
}