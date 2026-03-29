package com.housewise.feature.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.housewise.HousewiseApp // ADDED IMPORT
import com.housewise.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onNavigateToOnboarding: () -> Unit,
    onNavigateToLogin: () -> Unit,    // ADDED: For users who skipped/finished onboarding
    onNavigateToDashboard: () -> Unit // ADDED: For users who are already logged in
) {
    // Automatically navigate to the correct screen after 2 seconds
    LaunchedEffect(key1 = true) {
        delay(2000)

        val sessionManager = HousewiseApp.sessionManager

        // Traffic Controller Logic
        when {
            sessionManager.isLoggedIn() -> onNavigateToDashboard()
            sessionManager.hasSeenOnboarding() -> onNavigateToLogin()
            else -> onNavigateToOnboarding()
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.bg_onboarding),
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Logo
        Image(
            painter = painterResource(id = R.drawable.ic_onboarding_logo),
            contentDescription = "Housewise Logo",
            modifier = Modifier
                .padding(top = 40.dp)
                .width(220.dp),
            contentScale = ContentScale.Fit
        )
    }
}