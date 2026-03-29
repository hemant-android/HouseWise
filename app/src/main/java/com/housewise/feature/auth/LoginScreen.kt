package com.housewise.feature.auth

import android.widget.Toast
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.lifecycle.viewmodel.compose.viewModel
import com.housewise.R
import com.housewise.core.components.HousewiseButton
import com.housewise.core.theme.HousewiseGreen
import com.housewise.core.theme.TextPrimary
import com.housewise.core.utils.Resource
import com.housewise.core.utils.sdp
import com.housewise.core.utils.ssp
import com.housewise.feature.auth.presentation.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onBackClick: () -> Unit = {},
    viewModel: LoginViewModel = viewModel() // Inject ViewModel here
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val loginState by viewModel.loginState.collectAsState()

    // Handle Side Effects (Success routing and Error toasts)
    LaunchedEffect(loginState) {
        when (loginState) {
            is Resource.Success -> {
                val data = (loginState as Resource.Success).data
                Toast.makeText(context, "Welcome ${data?.firstName}!", Toast.LENGTH_SHORT).show()
                // TODO: Save data?.token to DataStore/SharedPreferences here
                onLoginSuccess()
            }

            is Resource.Error -> {
                val message = (loginState as Resource.Error).message
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                viewModel.resetState() // Reset so it doesn't fire again on recomposition
            }

            else -> Unit
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // 1. Background Image
        Image(
            painter = painterResource(id = R.drawable.bg_onboarding),
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // 2. Top-Left Back Button
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .padding(top = 32.sdp, start = 8.sdp)
                .align(Alignment.TopStart)
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = "Back",
                tint = Color.Gray,
                modifier = Modifier.size(36.sdp)
            )
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 3. Welcome Header
            Column(
                modifier = Modifier.padding(top = 80.sdp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Welcome to",
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 18.ssp,
                    color = Color.Black,
                    fontWeight = FontWeight.Medium
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_onboarding_logo),
                    contentDescription = "Housewise Logo",
                    modifier = Modifier.width(170.sdp),
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // 4. Login Form Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.sdp, end = 16.sdp, bottom = 32.sdp),
                shape = RoundedCornerShape(24.sdp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.sdp)
            ) {
                Column(modifier = Modifier.padding(24.sdp)) {
                    Text(
                        text = "Email",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.ssp,
                        color = TextPrimary,
                        modifier = Modifier.padding(bottom = 8.sdp)
                    )
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        placeholder = {
                            Text(
                                "Email address",
                                color = Color.LightGray,
                                fontSize = 14.ssp
                            )
                        },
                        leadingIcon = {
                            Icon(
                                painterResource(id = R.drawable.ic_mail),
                                null,
                                modifier = Modifier.size(24.sdp),
                                tint = Color.Unspecified
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.sdp),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color.LightGray,
                            focusedBorderColor = HousewiseGreen
                        )
                    )

                    Spacer(modifier = Modifier.height(20.sdp))

                    Text(
                        text = "Password",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.ssp,
                        color = TextPrimary,
                        modifier = Modifier.padding(bottom = 8.sdp)
                    )
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        placeholder = {
                            Text(
                                "Your password",
                                color = Color.LightGray,
                                fontSize = 14.ssp
                            )
                        },
                        leadingIcon = {
                            Icon(
                                painterResource(id = R.drawable.ic_password),
                                null,
                                modifier = Modifier.size(24.sdp),
                                tint = Color.Unspecified
                            )
                        },
                        trailingIcon = {
                            val icon =
                                if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    icon,
                                    null,
                                    tint = Color.Gray,
                                    modifier = Modifier.size(20.sdp)
                                )
                            }
                        },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.sdp),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color.LightGray,
                            focusedBorderColor = HousewiseGreen
                        )
                    )

                    Spacer(modifier = Modifier.height(32.sdp))

                    // Sign In Button
                    HousewiseButton(
                        text = "Sign In",
                        onClick = { viewModel.login(email, password) } // Trigger ViewModel Action
                    )

                    Spacer(modifier = Modifier.height(24.sdp))

                    Text(
                        text = "Terms & Conditions",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        textDecoration = TextDecoration.Underline,
                        color = HousewiseGreen,
                        fontSize = 14.ssp
                    )
                }
            }
        }

        // 5. Loading Overlay Overlay
        if (loginState is Resource.Loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f)), // Dim background
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = HousewiseGreen)
            }
        }
    }
}