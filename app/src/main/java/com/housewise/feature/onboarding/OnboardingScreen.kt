package com.housewise.feature.onboarding

// Import your responsive utils
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import com.housewise.HousewiseApp
import com.housewise.R
import com.housewise.core.components.HousewiseButton
import com.housewise.core.theme.HousewiseGreen
import com.housewise.core.theme.TextPrimary
import com.housewise.core.theme.TextSecondary
import com.housewise.core.utils.sdp
import com.housewise.core.utils.ssp
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(onNavigateToLogin: () -> Unit) {
    val pagerState = rememberPagerState(pageCount = { 3 })
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.bg_onboarding),
            contentDescription = "Background Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 24.sdp), // Scalable top padding
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Progress Indicator
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.sdp, vertical = 12.sdp),
                horizontalArrangement = Arrangement.spacedBy(8.sdp)
            ) {
                repeat(3) { index ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(3.sdp) // Scalable indicator thickness
                            .background(
                                color = if (pagerState.currentPage >= index) HousewiseGreen
                                else Color.LightGray.copy(alpha = 0.5f)
                            )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.sdp))

            // Logo
            Image(
                painter = painterResource(id = R.drawable.ic_onboarding_logo),
                contentDescription = "Housewise Logo",
                modifier = Modifier.width(220.sdp), // Scalable logo width
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.weight(1f))

            // Stacked Cards Container
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.sdp),
                contentAlignment = Alignment.BottomCenter
            ) {
                // Layer 3 (Farthest back)
                if (pagerState.currentPage == 0) {
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .padding(horizontal = 48.sdp)
                            .offset(y = (-24).sdp) // Scalable offset
                            .background(
                                color = Color.White.copy(alpha = 0.4f),
                                shape = RoundedCornerShape(32.sdp)
                            )
                    )
                }

                // Layer 2 (Middle)
                if (pagerState.currentPage <= 1) {
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .padding(horizontal = 30.sdp)
                            .offset(y = (-12).sdp)
                            .background(
                                color = Color.White.copy(alpha = 0.7f),
                                shape = RoundedCornerShape(32.sdp)
                            )
                    )
                }

                // Layer 1 (Main Front Card)
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.sdp),
                    shape = RoundedCornerShape(32.sdp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.sdp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.sdp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        HorizontalPager(state = pagerState) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                // Title-1 Style using Poppins and ssp
                                Text(
                                    text = buildAnnotatedString {
                                        append("End-to-End\n")
                                        withStyle(style = SpanStyle(color = HousewiseGreen)) {
                                            append("Property")
                                        }
                                        append(" Management")
                                    },
                                    style = MaterialTheme.typography.bodyLarge, // Automatically uses Poppins
                                    fontSize = 26.ssp, // Responsive font size
                                    color = TextPrimary,
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.W600,
                                    lineHeight = 34.ssp
                                )
                                Spacer(modifier = Modifier.height(16.sdp))
                                // Paragraph Style
                                Text(
                                    text = "Remember to check your spam folder, and if you have still not received it within the next ten minutes, click the resend button.",
                                    style = MaterialTheme.typography.bodyMedium, // Uses Poppins
                                    fontSize = 14.ssp,
                                    color = TextSecondary,
                                    textAlign = TextAlign.Center,
                                    lineHeight = 20.ssp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(32.sdp))

                        HousewiseButton(
                            text = when (pagerState.currentPage) {
                                0 -> "Get Started"
                                1 -> "Next"
                                else -> "Let's start"
                            },
                            onClick = {
                                if (pagerState.currentPage < 2) {
                                    coroutineScope.launch {
                                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                    }
                                } else {
                                    HousewiseApp.sessionManager.setOnboardingSeen(true)
                                    onNavigateToLogin()
                                }
                            }
                        )

                        if (pagerState.currentPage < 2) {
                            Spacer(modifier = Modifier.height(8.sdp))
                            TextButton(onClick = {
                                // FIXED: Save state before skipping
                                HousewiseApp.sessionManager.setOnboardingSeen(true)
                                onNavigateToLogin()
                            }) {
                                Text(
                                    text = "Skip!",
                                    color = TextSecondary,
                                    fontSize = 14.ssp,
                                    fontWeight = FontWeight.W400,
                                    style = MaterialTheme.typography.bodyMedium // Uses Poppins
                                )
                            }
                        } else {
                            Spacer(modifier = Modifier.height(8.sdp))
                        }
                    }
                }
            }
        }
    }
}