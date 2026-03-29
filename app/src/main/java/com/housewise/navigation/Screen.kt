package com.housewise.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Onboarding : Screen("onboarding")
    object Login : Screen("login")
    object Dashboard : Screen("dashboard")
    object TaskDetails : Screen("task_details")
    object InitiateTaskEmpty : Screen("initiate_task_empty") // NEW
    object InitiateTaskDetail : Screen("initiate_task_detail") // NEW
    object LeadDetails : Screen("lead_detail") // NEW
    object BrokerDetails : Screen("broker_detail") // NEW
    object RemindersScreen : Screen("reminders_screen") // NEW
    object Notifications : Screen("notifications_screen")

    // ADD THIS NEW ROUTE
    object FilterSort : Screen("filter_sort/{context}") {
        fun createRoute(context: String) = "filter_sort/$context"
    }
}

sealed class BottomNavScreen(val route: String, val title: String) {
    object Tasks : BottomNavScreen("tasks", "My Tasks")
    object Leads : BottomNavScreen("leads", "My Leads")
    object Brokers : BottomNavScreen("brokers", "Broker Manager")
    object More : BottomNavScreen("more", "More")
}