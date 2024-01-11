package com.payclip.blaze.spike.walkthrough

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.RichTooltip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.payclip.blaze.spike.walkthrough.ui.theme.BlazeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    @ExperimentalMaterial3Api
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BlazeTheme {
                MainContent(viewModel = viewModel)
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
private fun MainContent(viewModel: MainViewModel) {
    val isWalkthroughActive by viewModel.isWalkthroughActive.collectAsState()
    val selectedStep by viewModel.selectedStep.collectAsState()

    if (isWalkthroughActive) {
        OpaqueCarpet()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        repeat(4) { index ->
            val tooltipState = rememberTooltipState(isPersistent = true)

            LaunchedEffect(selectedStep) {
                if (isWalkthroughActive && selectedStep == index) {
                    tooltipState.show()
                } else {
                    tooltipState.dismiss()
                }
            }

            TooltipBox(
                positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
                tooltip = {
                    RichTooltip(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        title = { Text("Title") },
                        action = {
                            TextButton(
                                onClick = { viewModel.goNextStep() }
                            ) {
                                Text("Next")
                            }
                        }
                    ) {
                        Text("Content")
                    }
                },
                state = tooltipState
            ) {
                WalkthroughComponent(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp),
                    isWalkthroughActive = isWalkthroughActive,
                    isSelected = index == selectedStep,
                    shape = RoundedCornerShape(4.dp),
                    shadowElevation = 4.dp
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFFFF5656))
                    )
                }
            }
        }
    }
}

@Composable
private fun OpaqueCarpet() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5F))
    )
}

@Composable
private fun WalkthroughComponent(
    modifier: Modifier = Modifier,
    isWalkthroughActive: Boolean,
    isSelected: Boolean,
    shape: Shape = RectangleShape,
    tonalElevation: Dp = 0.dp,
    shadowElevation: Dp = 0.dp,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier,
        shape = shape,
        tonalElevation = tonalElevation,
        shadowElevation = shadowElevation
    ) {
        content()
        Box(
            modifier = Modifier
                .fillMaxSize()
                .isSelectedStep(
                    isWalkthroughActive = isWalkthroughActive,
                    isSelected = isSelected
                )
        )
    }
}

private fun Modifier.isSelectedStep(
    isWalkthroughActive: Boolean,
    isSelected: Boolean
) = composed {
    val color by animateColorAsState(
        targetValue = if (isWalkthroughActive && !isSelected) {
            Color.Black.copy(alpha = 0.5F)
        } else {
            Color.Transparent
        },
        label = ""
    )

    this.then(
        background(color)
    )
}
