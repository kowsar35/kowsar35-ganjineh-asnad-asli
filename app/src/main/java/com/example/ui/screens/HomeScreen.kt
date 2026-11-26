package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    onNavigateToEditor: (Long?) -> Unit,
    onNavigateToLibrary: (String) -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToAbout: () -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val documents by viewModel.allDocuments.collectAsState()
    val folders by viewModel.allFolders.collectAsState()
    val favorites by viewModel.favoriteDocuments.collectAsState()
    val trash by viewModel.trashDocuments.collectAsState()

    var showCreateFolderDialog by remember { mutableStateOf(false) }
    var newFolderName by remember { mutableStateOf("") }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "گنجینه اسناد",
                    modifier = Modifier.padding(horizontal = 24.dp),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(8.dp))

                NavigationDrawerItem(
                    label = { Text("خانه") },
                    selected = false,
                    onClick = { scope.launch { drawerState.close() } },
                    icon = { Icon(Icons.Default.Home, contentDescription = null) }
                )
                NavigationDrawerItem(
                    label = { Text("همه اسناد (${documents.size.toPersianDigits()})") },
                    selected = false,
                    onClick = { scope.launch { drawerState.close() }; onNavigateToLibrary("documents") },
                    icon = { Icon(Icons.Default.Description, contentDescription = null) }
                )
                NavigationDrawerItem(
                    label = { Text("پوشه‌ها (${folders.size.toPersianDigits()})") },
                    selected = false,
                    onClick = { scope.launch { drawerState.close() }; showCreateFolderDialog = true },
                    icon = { Icon(Icons.Default.Folder, contentDescription = null) }
                )
                NavigationDrawerItem(
                    label = { Text("علاقه‌مندی‌ها (${favorites.size.toPersianDigits()})") },
                    selected = false,
                    onClick = { scope.launch { drawerState.close() }; onNavigateToLibrary("favorites") },
                    icon = { Icon(Icons.Default.Star, contentDescription = null) }
                )
                NavigationDrawerItem(
                    label = { Text("کتابخانه صوتی") },
                    selected = false,
                    onClick = { scope.launch { drawerState.close() }; onNavigateToLibrary("audio") },
                    icon = { Icon(Icons.Default.AudioFile, contentDescription = null) }
                )
                NavigationDrawerItem(
                    label = { Text("تصاویر") },
                    selected = false,
                    onClick = { scope.launch { drawerState.close() }; onNavigateToLibrary("images") },
                    icon = { Icon(Icons.Default.Image, contentDescription = null) }
                )
                NavigationDrawerItem(
                    label = { Text("ویدیوها") },
                    selected = false,
                    onClick = { scope.launch { drawerState.close() }; onNavigateToLibrary("video") },
                    icon = { Icon(Icons.Default.VideoFile, contentDescription = null) }
                )
                NavigationDrawerItem(
                    label = { Text("سطل زباله (${trash.size.toPersianDigits()})") },
                    selected = false,
                    onClick = { scope.launch { drawerState.close() }; onNavigateToLibrary("trash") },
                    icon = { Icon(Icons.Default.Delete, contentDescription = null) }
                )
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                NavigationDrawerItem(
                    label = { Text("تنظیمات") },
                    selected = false,
                    onClick = { scope.launch { drawerState.close() }; onNavigateToSettings() },
                    icon = { Icon(Icons.Default.Settings, contentDescription = null) }
                )
                NavigationDrawerItem(
                    label = { Text("درباره برنامه") },
                    selected = false,
                    onClick = { scope.launch { drawerState.close() }; onNavigateToAbout() },
                    icon = { Icon(Icons.Default.Info, contentDescription = null) }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("گنجینه اسناد") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "منو")
                        }
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(onClick = { onNavigateToEditor(null) }) {
                    Icon(Icons.Default.Add, contentDescription = "سند جدید")
                }
            }
        ) { padding ->
            if (documents.isEmpty() && folders.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "هنوز سندی ایجاد نکرده‌اید",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { onNavigateToEditor(null) }) {
                            Icon(Icons.Default.Add, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("ایجاد اولین سند")
                        }
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(documents) { doc ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = { onNavigateToEditor(doc.id) }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(text = doc.title, style = MaterialTheme.typography.titleMedium)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = doc.content.take(50),
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                IconButton(onClick = { viewModel.toggleFavorite(doc) }) {
                                    Icon(
                                        imageVector = if (doc.isFavorite) Icons.Default.Star else Icons.Default.StarBorder,
                                        contentDescription = "علاقه‌مندی",
                                        tint = if (doc.isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                IconButton(onClick = { viewModel.moveToTrash(doc) }) {
                                    Icon(Icons.Default.Delete, contentDescription = "حذف به سطل زباله")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showCreateFolderDialog) {
        AlertDialog(
            onDismissRequest = { showCreateFolderDialog = false },
            title = { Text("ایجاد پوشه جدید") },
            text = {
                OutlinedTextField(
                    value = newFolderName,
                    onValueChange = { newFolderName = it },
                    label = { Text("نام پوشه") }
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    if (newFolderName.isNotBlank()) {
                        viewModel.createFolder(newFolderName)
                        newFolderName = ""
                        showCreateFolderDialog = false
                    }
                }) {
                    Text("ایجاد")
                }
            },
            dismissButton = {
                TextButton(onClick = { showCreateFolderDialog = false }) {
                    Text("انصراف")
                }
            }
        )
    }
}

fun Int.toPersianDigits(): String {
    val persianNumbers = arrayOf("۰", "۱", "۲", "۳", "۴", "۵", "۶", "۷", "۸", "۹")
    return this.toString().map { char ->
        if (char.isDigit()) persianNumbers[char.toString().toInt()] else char
    }.joinToString("")
}
