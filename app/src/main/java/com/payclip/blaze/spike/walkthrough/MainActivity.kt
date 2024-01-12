package com.payclip.blaze.spike.walkthrough

import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.ColorInt
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.animateIntSizeAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toOffset
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.zIndex
import androidx.core.graphics.ColorUtils
import com.payclip.blaze.spike.walkthrough.models.DialogBuilder
import com.payclip.blaze.spike.walkthrough.models.SpotlightBuilder
import com.payclip.blaze.spike.walkthrough.ui.theme.BlazeTheme
import com.payclip.blaze.spike.walkthrough.ui.theme.ClipTypography
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt
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
    val isWalkthroughEnabled by viewModel.isWalkthroughEnabled.collectAsState()
    val selectedStep by viewModel.selectedStep.collectAsState()
    val scope = rememberCoroutineScope()
    val lazyState = rememberLazyListState()
    val spotlight = remember { mutableStateOf(SpotlightBuilder()) }

    Spotlight(
        isEnabled = isWalkthroughEnabled,
        spotlight = spotlight.value
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        state = lazyState,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        items(8) { index ->
            WalkthroughComponent(
                spotlight = spotlight,
                spotlightCornerRadius = 4.dp,
                isEnabled = isWalkthroughEnabled,
                isSelected = index == selectedStep,
                dialog = DialogBuilder(
                    title = "Nueva venta",
                    highlight = "${index + 1} de 8",
                    description = "Accede rápidamente a la calculadora para ingresar manualmente el monto a cobrar.",
                    primaryButtonText = "Siguiente",
                    secondaryButtonText = "Omitir",
                    onPrimaryButtonClick = {
                        scope.launch {
                            if (index < 7) {
                                lazyState.animateScrollToItem(index + 1)
                            } else {
                                lazyState.scrollToItem(0)
                            }
                        }
                        viewModel.goNextStep()
                    },
                    onSecondaryButtonClick = {
                        // no-op
                    },
                    onDismiss = {
                        viewModel.onFinishWalkthrough()
                    }
                ),
                state = lazyState
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
    spotlightCornerRadius: Dp = 0.dp,
    isEnabled: Boolean,
    isSelected: Boolean,
    dialog: DialogBuilder,
    state: LazyListState = rememberLazyListState(),
    content: @Composable (modifier: Modifier) -> Unit
) {
    val tooltipState = rememberTooltipState(isPersistent = true)

    LaunchedEffect(isSelected) {
        if (isEnabled && isSelected) {
            tooltipState.show()
        } else {
            tooltipState.dismiss()
        }
    }

    LaunchedEffect(tooltipState.isVisible) {
        if (isEnabled && isSelected && !tooltipState.isVisible && !state.isScrollInProgress) {
            dialog.onDismiss()
        }
    }

    TooltipBox(
        positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
        tooltip = {
            WalkthroughDialog(
                title = dialog.title,
                highlight = dialog.highlight,
                description = dialog.description,
                primaryButtonText = dialog.primaryButtonText,
                secondaryButtonText = dialog.secondaryButtonText,
                onPrimaryButtonClick = {
                    tooltipState.dismiss()
                    dialog.onPrimaryButtonClick()
                },
                onSecondaryButtonClick = {
                    tooltipState.dismiss()
                    dialog.onSecondaryButtonClick()
                }
            )
        },
        state = tooltipState
    ) {
        content(
            Modifier.setSpotlightLayout(
                isEnabled = isEnabled && isSelected,
                spotlight = spotlight,
                spotlightCornerRadius = spotlightCornerRadius,
                state = state
            )
        )
    }
}

@Composable
private fun WalkthroughDialog(
    title: String,
    highlight: String,
    description: String,
    primaryButtonText: String,
    secondaryButtonText: String,
    onPrimaryButtonClick: () -> Unit,
    onSecondaryButtonClick: () -> Unit
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
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    modifier = Modifier.weight(1F),
                    text = title,
                    style = ClipTypography.titleSmall,
                    color = Color(0xFF25282A),
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Left,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = highlight,
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
                verticalAlignment = Alignment.CenterVertically,
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
                        color = Color(0xFF353536),
                        fontWeight = FontWeight.SemiBold
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
                        color = Color(0xFFFFFFFF),
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

private fun Modifier.setSpotlightLayout(
    isEnabled: Boolean,
    spotlight: MutableState<SpotlightBuilder>,
    spotlightCornerRadius: Dp = 0.dp,
    state: LazyListState
) = composed {
    val isPositioned = remember { mutableStateOf(false) }
    var position by remember { mutableStateOf(IntOffset.Zero) }
    var size by remember { mutableStateOf(IntSize.Zero) }

    LaunchedEffect(isEnabled, position, size, state.isScrollInProgress) {
        val hasSpotlightChange = position != IntOffset.Zero && size != IntSize.Zero

        if (isPositioned.value.not() && isEnabled && hasSpotlightChange) {
            if (state.isScrollInProgress) {
                spotlight.value = spotlight.value.copy(size = size)
            } else {
                spotlight.value = SpotlightBuilder(
                    position = position,
                    size = size,
                    cornerRadius = spotlightCornerRadius
                )
                isPositioned.value = true
            }
        } else if (isPositioned.value  && !isEnabled) {
            isPositioned.value = false
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
    isEnabled: Boolean,
    spotlight: SpotlightBuilder
) {
    val context = LocalContext.current
    val statusBarColor = remember { getStatusBarColor(context) }
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

    SideEffect {
        setStatusBarColor(
            context = context,
            default = statusBarColor,
            isEnabled = isEnabled
        )
    }

    AnimatedVisibility(
        modifier = Modifier
            .fillMaxSize()
            .zIndex(Float.MAX_VALUE),
        visible = isEnabled
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = {
                        // no-op
                    }
                ),
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
}

private fun getStatusBarColor(context: Context): Int? {
    return context.getActivity()?.window?.statusBarColor
}

private fun setStatusBarColor(
    context: Context,
    @ColorInt
    default: Int?,
    isEnabled: Boolean,
) {
    context.getActivity()?.let {
        it.window.statusBarColor = if (isEnabled) {
            ColorUtils.blendARGB(
                default ?: android.graphics.Color.WHITE,
                android.graphics.Color.BLACK,
                0.5F
            )
        } else {
            default ?: android.graphics.Color.WHITE
        }
    }
}

private fun Context.getActivity(): ComponentActivity? = when (this) {
    is ComponentActivity -> this
    is ContextWrapper -> baseContext.getActivity()
    else -> null
}
