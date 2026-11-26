package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ui.screens.*
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themeName by viewModel.themeName.collectAsState()
            val darkMode by viewModel.darkMode.collectAsState()
            val fontFamily by viewModel.fontFamily.collectAsState()
            val fontSize by viewModel.fontSize.collectAsState()

            MyApplicationTheme(
                themeName = themeName,
                darkMode = darkMode,
                fontFamilyName = fontFamily,
                fontSizeScale = fontSize
            ) {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "home") {
                    composable("home") {
                        HomeScreen(
                            viewModel = viewModel,
                            onNavigateToEditor = { docId ->
                                if (docId != null) {
                                    navController.navigate("editor/$docId")
                                } else {
                                    navController.navigate("editor")
                                }
                            },
                            onNavigateToLibrary = { type ->
                                navController.navigate("library/$type")
                            },
                            onNavigateToSettings = {
                                navController.navigate("settings")
                            },
                            onNavigateToAbout = {
                                navController.navigate("about")
                            }
                        )
                    }
                    composable("editor") {
                        EditorScreen(
                            viewModel = viewModel,
                            documentId = null,
                            onBack = { navController.popBackStack() }
                        )
                    }
                    composable(
                        route = "editor/{docId}",
                        arguments = listOf(navArgument("docId") { type = NavType.LongType })
                    ) { backStackEntry ->
                        val docId = backStackEntry.arguments?.getLong("docId")
                        EditorScreen(
                            viewModel = viewModel,
                            documentId = docId,
                            onBack = { navController.popBackStack() }
                        )
                    }
                    composable(
                        route = "library/{type}",
                        arguments = listOf(navArgument("type") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val type = backStackEntry.arguments?.getString("type") ?: "documents"
                        LibraryScreen(
                            viewModel = viewModel,
                            screenType = type,
                            onBack = { navController.popBackStack() },
                            onOpenDoc = { docId ->
                                navController.navigate("editor/$docId")
                            }
                        )
                    }
                    composable("settings") {
                        SettingsScreen(
                            viewModel = viewModel,
                            onBack = { navController.popBackStack() }
                        )
                    }
                    composable("about") {
                        AboutScreen(
                            onBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}
