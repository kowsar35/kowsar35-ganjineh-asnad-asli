package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(viewModel: MainViewModel, onBack: () -> Unit) {
    val currentTheme by viewModel.themeName.collectAsState()
    val currentFont by viewModel.fontFamily.collectAsState()
    val currentSize by viewModel.fontSize.collectAsState()
    val darkMode by viewModel.darkMode.collectAsState()

    val themes = listOf("Ocean Blue", "Emerald Green", "Modern Purple", "Turquoise", "Professional Navy", "Soft Pink", "Warm Orange", "Minimal Gray")
    val fonts = listOf("IRANSans", "IranYekan", "Default")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("تنظیمات") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "بازگشت")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text("انتخاب تم رنگی", style = MaterialTheme.typography.titleMedium)
            themes.forEach { theme ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(theme)
                    RadioButton(
                        selected = currentTheme == theme,
                        onClick = { viewModel.setTheme(theme) }
                    )
                }
            }

            HorizontalDivider()

            Text("فونت برنامه و ویرایشگر", style = MaterialTheme.typography.titleMedium)
            fonts.forEach { font ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(font)
                    RadioButton(
                        selected = currentFont == font,
                        onClick = { viewModel.setFontFamily(font) }
                    )
                }
            }

            HorizontalDivider()

            Text("اندازه قلم: ${currentSize.toInt()}", style = MaterialTheme.typography.titleMedium)
            Slider(
                value = currentSize,
                onValueChange = { viewModel.setFontSize(it) },
                valueRange = 10f..24f,
                steps = 14
            )

            HorizontalDivider()

            Text("حالت تاریک / روشن", style = MaterialTheme.typography.titleMedium)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("حالت تاریک")
                Switch(
                    checked = darkMode == true,
                    onCheckedChange = { viewModel.setDarkMode(if (it) true else false) }
                )
            }
        }
    }
}
