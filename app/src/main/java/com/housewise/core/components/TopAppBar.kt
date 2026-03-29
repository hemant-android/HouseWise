package com.housewise.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.housewise.R
import com.housewise.core.theme.HousewiseGreen
import com.housewise.core.theme.TextPrimary
import com.housewise.core.theme.TextSecondary
// Import your responsive utils
import com.housewise.core.utils.sdp
import com.housewise.core.utils.ssp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    userName: String = "Kushagra Singh Tanwar",
    location: String = "East enclave, INA, Delhi",
    notificationCount: Int = 32,
    onNavigateToNotifications: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 16.sdp, vertical = 12.sdp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar
        Box(
            modifier = Modifier
                .size(48.sdp)
                .clip(CircleShape)
                .background(HousewiseGreen),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "KT",
                color = Color.White,
                fontWeight = FontWeight.Medium,
                fontSize = 18.ssp,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Spacer(modifier = Modifier.width(12.sdp))

        // User Info
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = userName,
                fontSize = 18.ssp,
                fontWeight = FontWeight.Medium,
                color = TextPrimary,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(2.sdp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_location_small),
                    contentDescription = "Location",
                    tint = TextSecondary,
                    modifier = Modifier.size(12.sdp)
                )
                Spacer(modifier = Modifier.width(4.sdp))
                Text(
                    text = location,
                    fontSize = 12.ssp,
                    color = TextSecondary,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        // FIXED Notification Bell Layout
        IconButton(
            onClick = onNavigateToNotifications,
            modifier = Modifier.size(48.sdp) // Generous touch target
        ) {
            // A Box containing both the bell and the badge
            Box(modifier = Modifier.size(32.sdp)) {

                // Bell Icon sits at the bottom-left of our invisible 32.sdp box
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notifications",
                    tint = TextPrimary,
                    modifier = Modifier
                        .size(24.sdp)
                        .align(Alignment.BottomStart)
                )

                // Custom Badge sits safely at the top-right
                if (notificationCount > 0) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd) // Automatically overhangs the bell without negative offsets
                            .background(Color(0xFF00BFA5), CircleShape) // Teal
                            .padding(horizontal = 4.sdp, vertical = 2.sdp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (notificationCount > 99) "99+" else notificationCount.toString(),
                            color = Color.White,
                            fontSize = 9.ssp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}