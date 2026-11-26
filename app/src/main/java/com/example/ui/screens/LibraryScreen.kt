package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Restore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.data.DocumentEntity
import com.example.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen(
    viewModel: MainViewModel,
    screenType: String, // "favorites", "trash", "audio", "video", "images"
    onBack: () -> Unit,
    onOpenDoc: (Long) -> Unit
) {
    val documents by viewModel.allDocuments.collectAsState()
    val favorites by viewModel.favoriteDocuments.collectAsState()
    val trash by viewModel.trashDocuments.collectAsState()

    val list = when (screenType) {
        "favorites" -> favorites
        "trash" -> trash
        else -> documents
    }

    val title = when (screenType) {
        "favorites" -> "علاقه‌مندی‌ها"
        "trash" -> "سطل زباله"
        "audio" -> "کتابخانه صوتی"
        "video" -> "ویدیوها"
        "images" -> "تصاویر"
        else -> "اسناد"
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "بازگشت")
                    }
                }
            )
        }
    ) { padding ->
        if (list.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "موردی یافت نشد", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(list) { doc ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { if (screenType != "trash") onOpenDoc(doc.id) }
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
                                Text(text = doc.content.take(50), style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            if (screenType == "trash") {
                                Row {
                                    IconButton(onClick = { viewModel.restoreFromTrash(doc) }) {
                                        Icon(Icons.Default.Restore, contentDescription = "بازیابی")
                                    }
                                    IconButton(onClick = { viewModel.permanentlyDelete(doc) }) {
                                        Icon(Icons.Default.Delete, contentDescription = "حذف دائمی")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
