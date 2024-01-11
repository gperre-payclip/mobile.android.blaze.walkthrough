package com.payclip.blaze.spike.walkthrough.models

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

data class SpotlightBuilder(
    val position: IntOffset = IntOffset.Zero,
    val size: IntSize = IntSize.Zero,
    val cornerRadius: Dp = 0.dp
)
