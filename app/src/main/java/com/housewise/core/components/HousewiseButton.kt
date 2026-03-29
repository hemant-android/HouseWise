package com.housewise.core.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.housewise.core.theme.HousewiseGreen
// Import your responsive utils
import com.housewise.core.utils.sdp
import com.housewise.core.utils.ssp

@Composable
fun HousewiseButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = HousewiseGreen // Added parameter for flexibility
) {
    Button(
        onClick = onClick,
        // Responsive height and fill
        modifier = modifier
            .fillMaxWidth()
            .height(50.sdp),
        colors = ButtonDefaults.buttonColors(containerColor = containerColor),
        // Responsive corner radius
        shape = RoundedCornerShape(12.sdp)
    ) {
        Text(
            text = text,
            // Scalable Poppins font
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 14.ssp,
            fontWeight = FontWeight.W400,
            color = Color.White
        )
    }
}