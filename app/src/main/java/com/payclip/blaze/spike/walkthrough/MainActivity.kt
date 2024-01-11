package com.payclip.blaze.spike.walkthrough

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RichTooltip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toOffset
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.zIndex
import com.payclip.blaze.spike.walkthrough.ui.theme.BlazeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt

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
    var spotlightSize by remember { mutableStateOf(IntSize(0, 0)) }
    var spotlightPosition by remember { mutableStateOf(IntOffset(0, 0)) }

    if (isWalkthroughActive) {
        Spotlight(
            position = spotlightPosition,
            size = spotlightSize
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        repeat(4) { index ->
            var size by remember { mutableStateOf(IntSize(0, 0)) }
            var position by remember { mutableStateOf(IntOffset(0, 0)) }
            val tooltipState = rememberTooltipState(isPersistent = true)

            LaunchedEffect(selectedStep, position, size) {
                if (isWalkthroughActive && selectedStep == index) {
                    spotlightSize = size
                    spotlightPosition = position
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
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .onGloballyPositioned {
                            position = if (it.isAttached) {
                                with(it.positionInRoot()) {
                                    IntOffset(x.roundToInt(), y.roundToInt())
                                }
                            } else {
                                IntOffset(0, 0)
                            }

                            size = if (it.isAttached) {
                                it.size
                            } else {
                                IntSize(0, 0)
                            }
                        },
                    shape = RoundedCornerShape(4.dp),
                    color = Color(0xFFFF5656),
                    shadowElevation = 4.dp,
                    tonalElevation = 4.dp
                ) {
                    // no-content
                }
            }
        }
    }
}

@Composable
private fun Spotlight(
    position: IntOffset,
    size: IntSize
) {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .zIndex(Float.MAX_VALUE),
        onDraw = {
            val spotLightPath = Path().apply {
                addRoundRect(
                    roundRect = RoundRect(
                        rect = Rect(
                            offset = position.toOffset(),
                            size = size.toSize()
                        ),
                        cornerRadius = CornerRadius(4.dp.toPx())
                    )
                )
            }
            clipPath(
                path = spotLightPath,
                clipOp = ClipOp.Difference
            ) {
                drawRect(SolidColor(Color.Black.copy(alpha = 0.5F)))
            }
        }
    )
}
