package com.example.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.R

val IRANSansFontFamily = FontFamily(
    Font(R.font.iransans_light, FontWeight.Light),
    Font(R.font.iransans_medium, FontWeight.Medium),
    Font(R.font.iransans_bold, FontWeight.Bold)
)

val IranYekanFontFamily = FontFamily(
    Font(R.font.iranyekan_bold, FontWeight.Bold),
    Font(R.font.iranyekan_extrabold, FontWeight.ExtraBold),
    Font(R.font.iranyekan_black, FontWeight.Black)
)

fun getAppTypography(fontFamily: FontFamily, scale: Float = 1.0f): Typography {
    return Typography(
        displayLarge = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.Bold, fontSize = (32 * scale).sp, lineHeight = (40 * scale).sp),
        displayMedium = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.Bold, fontSize = (28 * scale).sp, lineHeight = (36 * scale).sp),
        displaySmall = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.Bold, fontSize = (24 * scale).sp, lineHeight = (32 * scale).sp),
        headlineLarge = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.Bold, fontSize = (22 * scale).sp, lineHeight = (28 * scale).sp),
        headlineMedium = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.Bold, fontSize = (20 * scale).sp, lineHeight = (26 * scale).sp),
        headlineSmall = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.Medium, fontSize = (18 * scale).sp, lineHeight = (24 * scale).sp),
        titleLarge = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.Bold, fontSize = (18 * scale).sp, lineHeight = (24 * scale).sp),
        titleMedium = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.Medium, fontSize = (16 * scale).sp, lineHeight = (22 * scale).sp),
        titleSmall = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.Medium, fontSize = (14 * scale).sp, lineHeight = (20 * scale).sp),
        bodyLarge = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.Normal, fontSize = (16 * scale).sp, lineHeight = (24 * scale).sp),
        bodyMedium = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.Normal, fontSize = (14 * scale).sp, lineHeight = (20 * scale).sp),
        bodySmall = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.Light, fontSize = (12 * scale).sp, lineHeight = (16 * scale).sp),
        labelLarge = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.Medium, fontSize = (14 * scale).sp, lineHeight = (20 * scale).sp),
        labelMedium = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.Medium, fontSize = (12 * scale).sp, lineHeight = (16 * scale).sp),
        labelSmall = TextStyle(fontFamily = fontFamily, fontWeight = FontWeight.Light, fontSize = (10 * scale).sp, lineHeight = (14 * scale).sp)
    )
}

val Typography = getAppTypography(IRANSansFontFamily)
