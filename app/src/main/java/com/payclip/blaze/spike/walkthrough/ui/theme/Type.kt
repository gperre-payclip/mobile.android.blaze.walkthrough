package com.payclip.blaze.spike.walkthrough.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.payclip.blaze.spike.walkthrough.R

val interFontFamily = FontFamily(
    Font(resId = R.font.inter_black, weight = FontWeight.Black),
    Font(resId = R.font.inter_bold, weight = FontWeight.Bold),
    Font(resId = R.font.inter_extrabold, weight = FontWeight.ExtraBold),
    Font(resId = R.font.inter_extralight, weight = FontWeight.ExtraLight),
    Font(resId = R.font.inter_light, weight = FontWeight.Light),
    Font(resId = R.font.inter_medium, weight = FontWeight.Medium),
    Font(resId = R.font.inter_regular, weight = FontWeight.Normal),
    Font(resId = R.font.inter_semibold, weight = FontWeight.SemiBold),
    Font(resId = R.font.inter_thin, weight = FontWeight.Thin),
)

val ClipTypography = Typography(
    displayLarge = TextStyle(
        fontSize = 64.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = interFontFamily,
        lineHeight = 96.sp,
        letterSpacing = (-0.25).sp,
    ),
    displayMedium = TextStyle(
        fontSize = 56.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = interFontFamily,
        lineHeight = 84.sp,
        letterSpacing = 0.sp,
    ),
    displaySmall = TextStyle(
        fontSize = 48.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = interFontFamily,
        lineHeight = 72.sp,
        letterSpacing = 0.sp,
    ),
    headlineLarge = TextStyle(
        fontSize = 44.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = interFontFamily,
        lineHeight = 66.sp,
        letterSpacing = 0.sp,
    ),
    headlineMedium = TextStyle(
        fontSize = 36.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = interFontFamily,
        lineHeight = 54.sp,
        letterSpacing = 0.sp,
    ),
    headlineSmall = TextStyle(
        fontSize = 32.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = interFontFamily,
        lineHeight = 48.sp,
        letterSpacing = 0.sp,
    ),
    titleLarge = TextStyle(
        fontSize = 28.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = interFontFamily,
        lineHeight = 42.sp,
        letterSpacing = 0.sp,
    ),
    titleMedium = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = interFontFamily,
        lineHeight = 36.sp,
        letterSpacing = 0.1.sp,
    ),
    titleSmall = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = interFontFamily,
        lineHeight = 30.sp,
        letterSpacing = 0.1.sp,
    ),
    bodyLarge = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = interFontFamily,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
    ),
    bodyMedium = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = interFontFamily,
        lineHeight = 21.sp,
        letterSpacing = 0.25.sp,
    ),
    bodySmall = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = interFontFamily,
        lineHeight = 18.sp,
        letterSpacing = 0.4.sp,
    ),
    labelLarge = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = interFontFamily,
        lineHeight = 27.sp,
        letterSpacing = 0.1.sp,
    ),
    labelMedium = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = interFontFamily,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
    ),
    labelSmall = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = interFontFamily,
        lineHeight = 20.sp,
        letterSpacing = 0.sp,
    ),
)

val DLSemiBold = TextStyle(
    fontSize = 64.sp,
    fontWeight = FontWeight.SemiBold,
    fontFamily = interFontFamily,
    lineHeight = 96.sp,
    letterSpacing = 0.5.sp,
)

val DLBold = TextStyle(
    fontSize = 64.sp,
    fontWeight = FontWeight.Bold,
    fontFamily = interFontFamily,
    lineHeight = 96.sp,
    letterSpacing = 0.5.sp,
)

val DLExtraBold = TextStyle(
    fontSize = 64.sp,
    fontWeight = FontWeight.ExtraBold,
    fontFamily = interFontFamily,
    lineHeight = 96.sp,
    letterSpacing = 0.5.sp,
)

val DMSemiBold = TextStyle(
    fontSize = 56.sp,
    fontWeight = FontWeight.SemiBold,
    fontFamily = interFontFamily,
    lineHeight = 84.sp,
    letterSpacing = 0.sp,
)

val DMBold = TextStyle(
    fontSize = 56.sp,
    fontWeight = FontWeight.Bold,
    fontFamily = interFontFamily,
    lineHeight = 84.sp,
    letterSpacing = 0.sp,
)

val DMExtraBold = TextStyle(
    fontSize = 56.sp,
    fontWeight = FontWeight.ExtraBold,
    fontFamily = interFontFamily,
    lineHeight = 84.sp,
    letterSpacing = 0.sp,
)

val DSSemiBold = TextStyle(
    fontSize = 48.sp,
    fontWeight = FontWeight.SemiBold,
    fontFamily = interFontFamily,
    lineHeight = 72.sp,
    letterSpacing = 0.sp,
)

val DSBold = TextStyle(
    fontSize = 48.sp,
    fontWeight = FontWeight.Bold,
    fontFamily = interFontFamily,
    lineHeight = 72.sp,
    letterSpacing = 0.sp,
)

val DSExtraBold = TextStyle(
    fontSize = 48.sp,
    fontWeight = FontWeight.ExtraBold,
    fontFamily = interFontFamily,
    lineHeight = 72.sp,
    letterSpacing = 0.sp,
)

val HLSemiBold = TextStyle(
    fontSize = 44.sp,
    fontWeight = FontWeight.SemiBold,
    fontFamily = interFontFamily,
    lineHeight = 66.sp,
    letterSpacing = 0.sp,
)

val HLBold = TextStyle(
    fontSize = 44.sp,
    fontWeight = FontWeight.Bold,
    fontFamily = interFontFamily,
    lineHeight = 66.sp,
    letterSpacing = 0.sp,
)

val HLExtraBold = TextStyle(
    fontSize = 44.sp,
    fontWeight = FontWeight.ExtraBold,
    fontFamily = interFontFamily,
    lineHeight = 66.sp,
    letterSpacing = 0.sp,
)

val HMSemiBold = TextStyle(
    fontSize = 36.sp,
    fontWeight = FontWeight.SemiBold,
    fontFamily = interFontFamily,
    lineHeight = 54.sp,
    letterSpacing = 0.sp,
)

val HMBold = TextStyle(
    fontSize = 36.sp,
    fontWeight = FontWeight.Bold,
    fontFamily = interFontFamily,
    lineHeight = 54.sp,
    letterSpacing = 0.sp,
)

val HMExtraBold = TextStyle(
    fontSize = 36.sp,
    fontWeight = FontWeight.ExtraBold,
    fontFamily = interFontFamily,
    lineHeight = 54.sp,
    letterSpacing = 0.sp,
)

val HSSemiBold = TextStyle(
    fontSize = 32.sp,
    fontWeight = FontWeight.SemiBold,
    fontFamily = interFontFamily,
    lineHeight = 48.sp,
    letterSpacing = 0.sp,
)

val HSBold = TextStyle(
    fontSize = 32.sp,
    fontWeight = FontWeight.Bold,
    fontFamily = interFontFamily,
    lineHeight = 48.sp,
    letterSpacing = 0.sp,
)

val HSExtraBold = TextStyle(
    fontSize = 32.sp,
    fontWeight = FontWeight.ExtraBold,
    fontFamily = interFontFamily,
    lineHeight = 48.sp,
    letterSpacing = 0.sp,
)

val TLSemiBold = TextStyle(
    fontSize = 28.sp,
    fontWeight = FontWeight.SemiBold,
    fontFamily = interFontFamily,
    lineHeight = 42.sp,
    letterSpacing = 0.sp,
)

val TLBold = TextStyle(
    fontSize = 28.sp,
    fontWeight = FontWeight.Bold,
    fontFamily = interFontFamily,
    lineHeight = 42.sp,
    letterSpacing = 0.sp,
)

val TLExtraBold = TextStyle(
    fontSize = 28.sp,
    fontWeight = FontWeight.ExtraBold,
    fontFamily = interFontFamily,
    lineHeight = 42.sp,
    letterSpacing = 0.sp,
)

val TMSemiBold = TextStyle(
    fontSize = 24.sp,
    fontWeight = FontWeight.SemiBold,
    fontFamily = interFontFamily,
    lineHeight = 36.sp,
    letterSpacing = 0.1.sp,
)

val TMBold = TextStyle(
    fontSize = 24.sp,
    fontWeight = FontWeight.Bold,
    fontFamily = interFontFamily,
    lineHeight = 36.sp,
    letterSpacing = 0.1.sp,
)

val TMExtraBold = TextStyle(
    fontSize = 24.sp,
    fontWeight = FontWeight.ExtraBold,
    fontFamily = interFontFamily,
    lineHeight = 36.sp,
    letterSpacing = 0.1.sp,
)

val TSSemiBold = TextStyle(
    fontSize = 20.sp,
    fontWeight = FontWeight.SemiBold,
    fontFamily = interFontFamily,
    lineHeight = 30.sp,
    letterSpacing = 0.1.sp,
)

val TSBold = TextStyle(
    fontSize = 20.sp,
    fontWeight = FontWeight.Bold,
    fontFamily = interFontFamily,
    lineHeight = 30.sp,
    letterSpacing = 0.1.sp,
)

val TSExtraBold = TextStyle(
    fontSize = 20.sp,
    fontWeight = FontWeight.ExtraBold,
    fontFamily = interFontFamily,
    lineHeight = 30.sp,
    letterSpacing = 0.1.sp,
)

val LGSemiBold = TextStyle(
    fontSize = 18.sp,
    fontWeight = FontWeight.SemiBold,
    fontFamily = interFontFamily,
    lineHeight = 27.sp,
    letterSpacing = 0.1.sp,
)

val LGBold = TextStyle(
    fontSize = 18.sp,
    fontWeight = FontWeight.Bold,
    fontFamily = interFontFamily,
    lineHeight = 27.sp,
    letterSpacing = 0.1.sp,
)

val LMSemiBold = TextStyle(
    fontSize = 16.sp,
    fontWeight = FontWeight.SemiBold,
    fontFamily = interFontFamily,
    lineHeight = 24.sp,
    letterSpacing = 0.5.sp,
)

val LMBold = TextStyle(
    fontSize = 16.sp,
    fontWeight = FontWeight.Bold,
    fontFamily = interFontFamily,
    lineHeight = 24.sp,
    letterSpacing = 0.5.sp,
)

val BMSemiBold = TextStyle(
    fontSize = 14.sp,
    fontWeight = FontWeight.SemiBold,
    fontFamily = interFontFamily,
    lineHeight = 21.sp,
    letterSpacing = 0.25.sp,
)

val BMBold = TextStyle(
    fontSize = 14.sp,
    fontWeight = FontWeight.Bold,
    fontFamily = interFontFamily,
    lineHeight = 21.sp,
    letterSpacing = 0.25.sp,
)

val BSSemiBold = TextStyle(
    fontSize = 12.sp,
    fontWeight = FontWeight.SemiBold,
    fontFamily = interFontFamily,
    lineHeight = 18.sp,
    letterSpacing = 0.4.sp,
)

val BSBold = TextStyle(
    fontSize = 12.sp,
    fontWeight = FontWeight.Bold,
    fontFamily = interFontFamily,
    lineHeight = 18.sp,
    letterSpacing = 0.4.sp,
)

val BXSRegular = TextStyle(
    fontSize = 11.sp,
    fontWeight = FontWeight.Normal,
    fontFamily = interFontFamily,
    lineHeight = 16.5.sp,
    letterSpacing = 0.4.sp,
)

val BXSSemiBold = TextStyle(
    fontSize = 12.sp,
    fontWeight = FontWeight.SemiBold,
    fontFamily = interFontFamily,
    lineHeight = 18.sp,
    letterSpacing = 0.4.sp,
)

val BXSBold = TextStyle(
    fontSize = 12.sp,
    fontWeight = FontWeight.Bold,
    fontFamily = interFontFamily,
    lineHeight = 18.sp,
    letterSpacing = 0.4.sp,
)

val CaptionRegular = TextStyle(
    fontSize = 10.sp,
    fontWeight = FontWeight.Normal,
    fontFamily = interFontFamily,
    lineHeight = 15.sp,
    letterSpacing = 0.4.sp,
)

val CaptionSemiBold = TextStyle(
    fontSize = 10.sp,
    fontWeight = FontWeight.SemiBold,
    fontFamily = interFontFamily,
    lineHeight = 15.sp,
    letterSpacing = 0.4.sp,
)

val CaptionBold = TextStyle(
    fontSize = 10.sp,
    fontWeight = FontWeight.Bold,
    fontFamily = interFontFamily,
    lineHeight = 15.sp,
    letterSpacing = 0.4.sp,
)
