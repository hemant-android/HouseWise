package com.housewise.core.utils

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Extension properties for Int to provide scalable DP and SP
 */

val Int.sdp: Dp
    @SuppressLint("ConfigurationScreenWidthHeight") @Composable
    get() = (this * (LocalConfiguration.current.screenWidthDp / 360f)).dp

val Int.ssp: TextUnit
    @SuppressLint("ConfigurationScreenWidthHeight") @Composable
    get() = (this * (LocalConfiguration.current.screenWidthDp / 360f)).sp

// Overloads for Double/Float if needed
val Double.sdp: Dp
    @SuppressLint("ConfigurationScreenWidthHeight") @Composable
    get() = (this * (LocalConfiguration.current.screenWidthDp / 360f)).dp