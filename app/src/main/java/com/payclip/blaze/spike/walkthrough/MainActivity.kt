package com.payclip.blaze.spike.walkthrough

import android.graphics.Paint.Align
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.animateIntSizeAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toOffset
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.zIndex
import com.payclip.blaze.spike.walkthrough.models.SpotlightBuilder
import com.payclip.blaze.spike.walkthrough.ui.theme.BlazeTheme
import com.payclip.blaze.spike.walkthrough.ui.theme.ClipTypography
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
    val spotlight = remember { mutableStateOf(SpotlightBuilder()) }

    if (isWalkthroughActive) {
        Spotlight(spotlight = spotlight.value)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        repeat(4) { index ->
            WalkthroughComponent(
                spotlight = spotlight,
                isSelected = index == selectedStep,
                cornerRadius = 4.dp,
                title = "Nueva venta",
                description = "Accede rápidamente a la calculadora para ingresar manualmente el monto a cobrar.",
                primaryButtonText = "Siguiente",
                secondaryButtonText = "Omitir",
                onPrimaryButtonClick = {
                    viewModel.goNextStep()
                },
                onSecondaryButtonClick = {
                     viewModel.onFinishWalkthrough()
                },
                actualStep = index + 1,
                totalSteps = 4
            ) { modifier ->
                Surface(
                    modifier = modifier
                        .fillMaxWidth(if (index % 2 == 0) 1F else 0.5F)
                        .height(160.dp),
                    shape = RoundedCornerShape(4.dp),
                    color = Color(0xFFFF5656),
                    shadowElevation = 4.dp
                ) {
                    // no-content
                }
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
private fun WalkthroughComponent(
    spotlight: MutableState<SpotlightBuilder>,
    isSelected: Boolean,
    cornerRadius: Dp = 0.dp,
    title: String,
    description: String,
    primaryButtonText: String,
    secondaryButtonText: String,
    onPrimaryButtonClick: () -> Unit,
    onSecondaryButtonClick: () -> Unit,
    actualStep: Int,
    totalSteps: Int,
    content: @Composable (modifier: Modifier) -> Unit
) {
    val tooltipState = rememberTooltipState(isPersistent = true)

    LaunchedEffect(isSelected) {
        if (isSelected) {
            tooltipState.show()
        } else {
            tooltipState.dismiss()
        }
    }

    TooltipBox(
        positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
        tooltip = {
            WalkthroughDialog(
                title = title,
                description = description,
                primaryButtonText = primaryButtonText,
                secondaryButtonText = secondaryButtonText,
                onPrimaryButtonClick = {
                    tooltipState.dismiss()
                    onPrimaryButtonClick()
                },
                onSecondaryButtonClick = {
                    tooltipState.dismiss()
                    onSecondaryButtonClick()
                },
                actualStep = actualStep,
                totalSteps = totalSteps
            )
        },
        state = tooltipState
    ) {
        content(
            Modifier.setSpotlightLayout(
                spotlight = spotlight,
                isSelected = isSelected,
                cornerRadius = cornerRadius
            )
        )
    }
}

@Composable
private fun WalkthroughDialog(
    title: String,
    description: String,
    primaryButtonText: String,
    secondaryButtonText: String,
    onPrimaryButtonClick: () -> Unit,
    onSecondaryButtonClick: () -> Unit,
    actualStep: Int,
    totalSteps: Int
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    modifier = Modifier.weight(1F),
                    text = title,
                    style = ClipTypography.titleMedium,
                    color = Color(0xFF25282A),
                    textAlign = TextAlign.Left,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "$actualStep de $totalSteps",
                    style = ClipTypography.bodySmall,
                    color = Color(0xFF63666A)
                )
            }
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = description,
                style = ClipTypography.bodyMedium,
                color = Color(0xFF63666A),
                maxLines = 3
            )
            Row(
                modifier = Modifier.align(Alignment.End),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    shape = RoundedCornerShape(8.dp),
                    colors =  ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFFFFFF)
                    ),
                    onClick = onSecondaryButtonClick
                ) {
                    Text(
                        text = secondaryButtonText,
                        style = ClipTypography.labelLarge,
                        color = Color(0xFF353536)
                    )
                }
                Button(
                    shape = RoundedCornerShape(8.dp),
                    colors =  ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFC4C02)
                    ),
                    onClick = onPrimaryButtonClick
                ) {
                    Text(
                        text = primaryButtonText,
                        style = ClipTypography.labelLarge,
                        color = Color(0xFFFFFFFF)
                    )
                }
            }
        }
    }
}

private fun Modifier.setSpotlightLayout(
    spotlight: MutableState<SpotlightBuilder>,
    isSelected: Boolean,
    cornerRadius: Dp = 0.dp
) = composed {
    var position by remember { mutableStateOf(IntOffset.Zero) }
    var size by remember { mutableStateOf(IntSize.Zero) }

    LaunchedEffect(isSelected, position, size) {
        if (isSelected) {
            spotlight.value = SpotlightBuilder(
                position = position,
                size = size,
                cornerRadius = cornerRadius
            )
        }
    }

    onGloballyPositioned {
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
    }
}

@Composable
private fun Spotlight(
    spotlight: SpotlightBuilder
) {
    val size by animateIntSizeAsState(
        targetValue = spotlight.size,
        label = "Size Animation"
    )
    val position by animateIntOffsetAsState(
        targetValue = spotlight.position,
        label = "Position Animation"
    )
    val cornerRadius by animateDpAsState(
        targetValue = spotlight.cornerRadius,
        label = "Corner Radius Animation"
    )

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
                        cornerRadius = CornerRadius(cornerRadius.toPx())
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
