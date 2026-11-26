package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily

@Composable
fun MyApplicationTheme(
    themeName: String = "Ocean Blue",
    darkMode: Boolean? = null,
    fontFamilyName: String = "IRANSans",
    fontSizeScale: Float = 1.0f,
    content: @Composable () -> Unit
) {
    val isDark = darkMode ?: isSystemInDarkTheme()

    val colorScheme = when (themeName) {
        "Emerald Green" -> if (isDark) darkColorScheme(primary = EmeraldGreenDarkPrimary) else lightColorScheme(primary = EmeraldGreenLightPrimary)
        "Modern Purple" -> if (isDark) darkColorScheme(primary = ModernPurpleDarkPrimary) else lightColorScheme(primary = ModernPurpleLightPrimary)
        "Turquoise" -> if (isDark) darkColorScheme(primary = TurquoiseDarkPrimary) else lightColorScheme(primary = TurquoiseLightPrimary)
        "Professional Navy" -> if (isDark) darkColorScheme(primary = ProfessionalNavyDarkPrimary) else lightColorScheme(primary = ProfessionalNavyLightPrimary)
        "Soft Pink" -> if (isDark) darkColorScheme(primary = SoftPinkDarkPrimary) else lightColorScheme(primary = SoftPinkLightPrimary)
        "Warm Orange" -> if (isDark) darkColorScheme(primary = WarmOrangeDarkPrimary) else lightColorScheme(primary = WarmOrangeLightPrimary)
        "Minimal Gray" -> if (isDark) darkColorScheme(primary = MinimalGrayDarkPrimary) else lightColorScheme(primary = MinimalGrayLightPrimary)
        else -> if (isDark) darkColorScheme(primary = OceanBlueDarkPrimary) else lightColorScheme(primary = OceanBlueLightPrimary)
    }

    val selectedFont = when (fontFamilyName) {
        "IranYekan" -> IranYekanFontFamily
        "IRANSans" -> IRANSansFontFamily
        else -> FontFamily.Default
    }

    val typography = getAppTypography(selectedFont, fontSizeScale / 16f)

    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
        content = content
    )
}
