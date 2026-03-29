package com.housewise.feature.dashboard

// Import responsive utils
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
    onNavigateToTaskDetails: () -> Unit,
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

    // State to control the New Task Bottom Sheet
    var showNewTaskSheet by remember { mutableStateOf(false) }
    var showNewLeadSheet by remember { mutableStateOf(false) }
    var showNewBrokerSheet by remember { mutableStateOf(false) }
    Scaffold(
        topBar = { TopAppBar(onNavigateToNotifications = onNavigateToNotifications) },
        bottomBar = {
            // 1. Wrap the Bottom Nav and FAB inside a single Box
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopCenter
            ) {
                // 2. Push the Bottom Bar down by half the height of the FAB
                Box(modifier = Modifier.padding(top = 32.sdp)) { // Responsive padding
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

                // 3. Place the FAB over the top-center edge of the Box
                Surface(
                    shape = CircleShape,
                    color = Color.White, // Guarantees pure white, no Material 3 tinting
                    shadowElevation = 8.sdp, // Responsive drop shadow
                    modifier = Modifier
                        .size(64.sdp) // Responsive FAB size
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
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add",
                            tint = Color.Black, // Pure black plus sign
                            modifier = Modifier.size(32.sdp) // Responsive '+' size
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
                        onNavigateToTaskDetails = onNavigateToTaskDetails,
                        onFilterClick = { onNavigateToFilterSort("tasks") })
                }
                composable(BottomNavScreen.Leads.route) {
                    MyLeadsScreen(
                        onNavigateToLeadDetails = onNavigateToLeadDetails,
                        onFilterClick = { onNavigateToFilterSort("leads") }) // PASSED DOWN: Wires up the click action
                }
                composable(BottomNavScreen.Brokers.route) {
                    BrokerManagerScreen(
                        onNavigateToBrokerDetails = onNavigateToBrokerDetails,
                        onFilterClick = { onNavigateToFilterSort("brokers") }
                    )
                }
                composable(BottomNavScreen.More.route) {
                    MoreOptionsScreen(
                        onNavigateToReminders = onNavigateToReminders,
                        onNavigateToNotifications = onNavigateToNotifications // ADDED THIS
                    )
                }
            }

            // BOTTOM SHEET LOGIC ADDED HERE
            if (showNewTaskSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showNewTaskSheet = false },
                    containerColor = Color.White,
                    dragHandle = null, // Hides the default little grey drag handle line
                    shape = RoundedCornerShape(
                        topStart = 24.sdp,
                        topEnd = 24.sdp
                    ) // Responsive corners
                ) {
                    NewTaskScreen(
                        onCancel = { showNewTaskSheet = false },
                        onSave = {
                            /* TODO: Save logic here */
                            showNewTaskSheet = false
                            onNavigateToInitiateTask()
                        }
                    )
                }
            }

            // LEAD BOTTOM SHEET (ADDED HERE)
            if (showNewLeadSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showNewLeadSheet = false },
                    containerColor = Color.White,
                    dragHandle = null,
                    shape = RoundedCornerShape(topStart = 24.sdp, topEnd = 24.sdp)
                ) {
                    // This calls the new composable we just created above!
                    NewLeadSheet(
                        onCancel = { showNewLeadSheet = false },
                        onSave = {
                            /* TODO: Add Save logic */
                            showNewLeadSheet = false
                        }
                    )
                }
            }
        }
    }

    // BROKER BOTTOM SHEET (ADDED HERE)
    if (showNewBrokerSheet) {
        ModalBottomSheet(
            onDismissRequest = { showNewBrokerSheet = false },
            containerColor = Color.White,
            dragHandle = null,
            shape = RoundedCornerShape(topStart = 24.sdp, topEnd = 24.sdp)
        ) {
            NewBrokerSheet(
                onCancel = { showNewBrokerSheet = false },
                onSave = {
                    /* TODO: Add Save Broker logic */
                    showNewBrokerSheet = false
                }
            )
        }
    }
}