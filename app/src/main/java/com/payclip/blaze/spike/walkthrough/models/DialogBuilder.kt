package com.payclip.blaze.spike.walkthrough.models

data class DialogBuilder(
    val title: String,
    val highlight: String,
    val description: String,
    val primaryButtonText: String,
    val secondaryButtonText: String,
    val onPrimaryButtonClick: () -> Unit,
    val onSecondaryButtonClick: () -> Unit,
    val onDismiss: () -> Unit
)
