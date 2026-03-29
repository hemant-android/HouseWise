package com.housewise.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.housewise.R
import com.housewise.core.theme.HousewiseDarkGreen
import com.housewise.navigation.BottomNavScreen
// Import your responsive utils
import com.housewise.core.utils.sdp
import com.housewise.core.utils.ssp

@Composable
fun BottomNavBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    val items = listOf(
        BottomNavScreen.Tasks to R.drawable.ic_tab_my_task,
        BottomNavScreen.Leads to R.drawable.ic_tab_my_lead,
        BottomNavScreen.Brokers to R.drawable.ic_tab_brocker,
        BottomNavScreen.More to R.drawable.ic_tab_more
    )

    NavigationBar(
        containerColor = HousewiseDarkGreen, // Dark green background
        contentColor = Color.White,
        modifier = Modifier.height(72.sdp) // Responsive height to match the design proportions
    ) {
        items.forEach { (screen, iconResId) ->
            val isSelected = currentRoute == screen.route

            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = iconResId),
                        contentDescription = screen.title,
                        modifier = Modifier.size(24.sdp) // Scalable icon size
                    )
                },
                label = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = screen.title,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = 10.ssp, // Scalable text
                            fontWeight = FontWeight.Medium,
                            style = MaterialTheme.typography.labelMedium // Uses Poppins
                        )

                        // Custom active indicator underline matching the design
                        if (isSelected) {
                            Spacer(modifier = Modifier.height(4.sdp))
                            Box(
                                modifier = Modifier
                                    .height(2.sdp)
                                    .width(20.sdp)
                                    .background(Color.White, RoundedCornerShape(1.sdp))
                            )
                        } else {
                            // Invisible spacer to keep the text vertically aligned identically
                            Spacer(modifier = Modifier.height(6.sdp))
                        }
                    }
                },
                selected = isSelected,
                onClick = { onNavigate(screen.route) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White, // Selected items are white
                    selectedTextColor = Color.White,
                    indicatorColor = Color.Transparent, // Completely removes the default Material pill background
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray
                ),
                alwaysShowLabel = true
            )
        }
    }
}