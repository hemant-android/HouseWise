package com.housewise

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.housewise.core.data.TaskData
import com.housewise.feature.auth.LoginScreen
import com.housewise.feature.dashboard.DashboardScreen
import com.housewise.feature.dashboard.brokers.BrokerDetailsScreen
import com.housewise.feature.dashboard.filter.FilterSortScreen
import com.housewise.feature.dashboard.leads.LeadDetailsScreen
import com.housewise.feature.dashboard.more.RemindersScreen
import com.housewise.feature.dashboard.notifications.NotificationsScreen // Make sure to import this!
import com.housewise.feature.dashboard.tasks.InitiateTaskDetailScreen
import com.housewise.feature.dashboard.tasks.InitiateTaskEmptyScreen
import com.housewise.feature.dashboard.tasks.TaskDetailsScreen
import com.housewise.feature.onboarding.OnboardingScreen
import com.housewise.feature.onboarding.SplashScreen
import com.housewise.navigation.Screen

@Composable
fun MainView() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(
                onNavigateToOnboarding = {
                    navController.navigate(Screen.Onboarding.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                },
                // NEW: Route to Log in
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                },
                // NEW: Route to Dashboard
                onNavigateToDashboard = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Dashboard.route) {
            DashboardScreen(
                onNavigateToTaskDetails = {
                    navController.navigate(Screen.TaskDetails.route)
                },
                onNavigateToInitiateTask = {
                    navController.navigate(Screen.InitiateTaskEmpty.route)
                },
                onNavigateToLeadDetails = {
                    navController.navigate(Screen.LeadDetails.route)
                },
                onNavigateToBrokerDetails = {
                    navController.navigate(Screen.BrokerDetails.route)
                },
                onNavigateToReminders = {
                    navController.navigate(Screen.RemindersScreen.route)
                },
                // ADDED THIS HOOK
                onNavigateToNotifications = {
                    navController.navigate(Screen.Notifications.route)
                },
                onNavigateToFilterSort = { contextType ->
                    navController.navigate(Screen.FilterSort.createRoute(contextType))
                }
            )
        }

        composable(Screen.TaskDetails.route) {
            val selectedTask = TaskData(
                title = "Repeat inspection report",
                propertyId = "ID #4092",
                dueDate = "22 Mar",
                status = "New",
                assignee = "Kushagra Singh Tanwar"
            )
            TaskDetailsScreen(
                task = selectedTask,
                onBackClick = { navController.popBackStack() },
                onNavigateToInitiate = {
                    navController.navigate(Screen.InitiateTaskEmpty.route)
                }
            )
        }

        composable(Screen.InitiateTaskEmpty.route) {
            InitiateTaskEmptyScreen(
                onBackClick = { navController.popBackStack() },
                onAddNowClick = {
                    navController.navigate(Screen.InitiateTaskDetail.route)
                }
            )
        }

        composable(Screen.InitiateTaskDetail.route) {
            InitiateTaskDetailScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.LeadDetails.route) {
            LeadDetailsScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.BrokerDetails.route) {
            BrokerDetailsScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.RemindersScreen.route) {
            RemindersScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        // ADDED THIS NEW SCREEN
        composable(Screen.Notifications.route) {
            NotificationsScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        // ADD THE NEW DYNAMIC FILTER SCREEN ROUTE
        composable(
            route = Screen.FilterSort.route,
            arguments = listOf(navArgument("context") { type = NavType.StringType })
        ) { backStackEntry ->
            val contextType = backStackEntry.arguments?.getString("context") ?: "tasks"
            FilterSortScreen(
                contextType = contextType,
                onBackClick = { navController.popBackStack() },
                onApplyClick = { navController.popBackStack() }
            )
        }
    }
}