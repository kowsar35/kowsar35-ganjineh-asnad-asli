package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.data.DocumentEntity
import com.example.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditorScreen(
    viewModel: MainViewModel,
    documentId: Long? = null,
    onBack: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var existingDoc by remember { mutableStateOf<DocumentEntity?>(null) }

    val allDocs by viewModel.allDocuments.collectAsState()

    LaunchedEffect(documentId, allDocs) {
        if (documentId != null) {
            val doc = allDocs.find { it.id == documentId }
            if (doc != null) {
                existingDoc = doc
                title = doc.title
                content = doc.content
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (documentId == null) "سند جدید" else "ویرایش سند") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "بازگشت")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        if (title.isNotBlank()) {
                            if (existingDoc == null) {
                                viewModel.createDocument(title = title, content = content)
                            } else {
                                viewModel.updateDocument(existingDoc!!.copy(title = title, content = content))
                            }
                            onBack()
                        }
                    }) {
                        Icon(Icons.Default.Save, contentDescription = "ذخیره")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("عنوان سند") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text("متن سند (پیشرفته)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
        }
    }
}
