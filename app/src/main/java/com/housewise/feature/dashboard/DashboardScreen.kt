package com.housewise.feature.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.housewise.core.components.BottomNavBar
import com.housewise.core.components.TopAppBar
import com.housewise.core.utils.sdp
import com.housewise.feature.dashboard.brokers.BrokerManagerScreen
import com.housewise.feature.dashboard.brokers.NewBrokerSheet
import com.housewise.feature.dashboard.leads.MyLeadsScreen
import com.housewise.feature.dashboard.leads.NewLeadSheet
import com.housewise.feature.dashboard.more.MoreOptionsScreen
import com.housewise.feature.dashboard.tasks.MyTasksScreen
import com.housewise.feature.dashboard.tasks.NewTaskScreen
import com.housewise.navigation.BottomNavScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onNavigateToTaskDetails: (String) -> Unit,
    onNavigateToInitiateTask: () -> Unit,
    onNavigateToLeadDetails: () -> Unit,
    onNavigateToBrokerDetails: () -> Unit,
    onNavigateToReminders: () -> Unit,
    onNavigateToNotifications: () -> Unit,
    onNavigateToFilterSort: (String) -> Unit
) {
    val bottomNavController = rememberNavController()
    val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // State to control Bottom Sheets
    var showNewTaskSheet by remember { mutableStateOf(false) }
    var showNewLeadSheet by remember { mutableStateOf(false) }
    var showNewBrokerSheet by remember { mutableStateOf(false) }

    // 1. FIXED: Added a trigger to refresh the task list
    var refreshTasksCounter by remember { mutableStateOf(0) }

    Scaffold(
        topBar = { TopAppBar(onNavigateToNotifications = onNavigateToNotifications) },
        bottomBar = {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopCenter) {
                Box(modifier = Modifier.padding(top = 32.sdp)) {
                    BottomNavBar(
                        currentRoute = currentRoute,
                        onNavigate = { route ->
                            bottomNavController.navigate(route) {
                                popUpTo(bottomNavController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }

                Surface(
                    shape = CircleShape,
                    color = Color.White,
                    shadowElevation = 8.sdp,
                    modifier = Modifier
                        .size(64.sdp)
                        .clickable {
                            when (currentRoute) {
                                BottomNavScreen.Tasks.route -> showNewTaskSheet = true
                                BottomNavScreen.Leads.route -> showNewLeadSheet = true
                                BottomNavScreen.Brokers.route -> showNewBrokerSheet = true
                            }
                        }
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            Icons.Default.Add,
                            "Add",
                            tint = Color.Black,
                            modifier = Modifier.size(32.sdp)
                        )
                    }
                }
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            NavHost(
                navController = bottomNavController,
                startDestination = BottomNavScreen.Tasks.route
            ) {
                composable(BottomNavScreen.Tasks.route) {
                    MyTasksScreen(
                        refreshTrigger = refreshTasksCounter, // 2. FIXED: Pass trigger down
                        onNavigateToTaskDetails = onNavigateToTaskDetails,
                        onFilterClick = { onNavigateToFilterSort("tasks") }
                    )
                }
                composable(BottomNavScreen.Leads.route) {
                    MyLeadsScreen(
                        onNavigateToLeadDetails = onNavigateToLeadDetails,
                        onFilterClick = { onNavigateToFilterSort("leads") })
                }
                composable(BottomNavScreen.Brokers.route) {
                    BrokerManagerScreen(
                        onNavigateToBrokerDetails = onNavigateToBrokerDetails,
                        onFilterClick = { onNavigateToFilterSort("brokers") })
                }
                composable(BottomNavScreen.More.route) {
                    MoreOptionsScreen(
                        onNavigateToReminders = onNavigateToReminders,
                        onNavigateToNotifications = onNavigateToNotifications
                    )
                }
            }

            // TASK BOTTOM SHEET
            if (showNewTaskSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showNewTaskSheet = false },
                    containerColor = Color.White, dragHandle = null,
                    shape = RoundedCornerShape(topStart = 24.sdp, topEnd = 24.sdp)
                ) {
                    NewTaskScreen(
                        onCancel = { showNewTaskSheet = false },
                        onSave = { navigateToInitiate -> // FIXED: Accepts the boolean!
                            showNewTaskSheet = false
                            refreshTasksCounter++

                            // If they clicked the bottom CTA, navigate!
                            if (navigateToInitiate) {
                                onNavigateToInitiateTask()
                            }
                        }
                    )
                }
            }

            // LEAD BOTTOM SHEET
            if (showNewLeadSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showNewLeadSheet = false },
                    containerColor = Color.White,
                    dragHandle = null,
                    shape = RoundedCornerShape(topStart = 24.sdp, topEnd = 24.sdp)
                ) {
                    NewLeadSheet(
                        onCancel = { showNewLeadSheet = false },
                        onSave = { showNewLeadSheet = false })
                }
            }

            // BROKER BOTTOM SHEET
            if (showNewBrokerSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showNewBrokerSheet = false },
                    containerColor = Color.White,
                    dragHandle = null,
                    shape = RoundedCornerShape(topStart = 24.sdp, topEnd = 24.sdp)
                ) {
                    NewBrokerSheet(
                        onCancel = { showNewBrokerSheet = false },
                        onSave = { showNewBrokerSheet = false })
                }
            }
        }
    }
}