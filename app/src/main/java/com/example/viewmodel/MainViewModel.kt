package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getDatabase(application).ganjinehDao()
    private val prefs = PreferencesManager(application)

    val allFolders: StateFlow<List<FolderEntity>> = dao.getAllFolders()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val rootFolders: StateFlow<List<FolderEntity>> = dao.getRootFolders()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val allDocuments: StateFlow<List<DocumentEntity>> = dao.getAllDocuments()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val favoriteDocuments: StateFlow<List<DocumentEntity>> = dao.getFavoriteDocuments()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val trashDocuments: StateFlow<List<DocumentEntity>> = dao.getTrashDocuments()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val allMedia: StateFlow<List<MediaEntity>> = dao.getAllMedia()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val themeName: StateFlow<String> = prefs.themeName
        .stateIn(viewModelScope, SharingStarted.Lazily, "Ocean Blue")

    val darkMode: StateFlow<Boolean?> = prefs.darkMode
        .stateIn(viewModelScope, SharingStarted.Lazily, null)

    val fontFamily: StateFlow<String> = prefs.fontFamily
        .stateIn(viewModelScope, SharingStarted.Lazily, "IRANSans")

    val fontSize: StateFlow<Float> = prefs.fontSize
        .stateIn(viewModelScope, SharingStarted.Lazily, 16f)

    val appPassword: StateFlow<String> = prefs.appPassword
        .stateIn(viewModelScope, SharingStarted.Lazily, "")

    var searchQuery = MutableStateFlow("")
    var currentFolderId = MutableStateFlow<Long?>(null)

    fun createFolder(name: String, parentId: Long? = null, color: String = "#0061A4") {
        viewModelScope.launch {
            dao.insertFolder(FolderEntity(name = name, parentId = parentId, color = color))
        }
    }

    fun deleteFolder(folder: FolderEntity) {
        viewModelScope.launch {
            dao.deleteFolder(folder)
        }
    }

    fun createDocument(title: String, content: String, docType: String = "text", folderId: Long? = null) {
        viewModelScope.launch {
            dao.insertDocument(DocumentEntity(title = title, content = content, docType = docType, folderId = folderId))
        }
    }

    fun updateDocument(doc: DocumentEntity) {
        viewModelScope.launch {
            dao.updateDocument(doc.copy(modifiedAt = System.currentTimeMillis()))
        }
    }

    fun toggleFavorite(doc: DocumentEntity) {
        viewModelScope.launch {
            dao.updateDocument(doc.copy(isFavorite = !doc.isFavorite))
        }
    }

    fun moveToTrash(doc: DocumentEntity) {
        viewModelScope.launch {
            dao.updateDocument(doc.copy(isTrash = true))
        }
    }

    fun restoreFromTrash(doc: DocumentEntity) {
        viewModelScope.launch {
            dao.updateDocument(doc.copy(isTrash = false))
        }
    }

    fun permanentlyDelete(doc: DocumentEntity) {
        viewModelScope.launch {
            dao.deleteDocument(doc)
        }
    }

    fun addMedia(media: MediaEntity) {
        viewModelScope.launch {
            dao.insertMedia(media)
        }
    }

    fun deleteMedia(media: MediaEntity) {
        viewModelScope.launch {
            dao.deleteMedia(media)
        }
    }

    fun setTheme(theme: String) {
        viewModelScope.launch { prefs.setThemeName(theme) }
    }

    fun setDarkMode(isDark: Boolean?) {
        viewModelScope.launch { prefs.setDarkMode(isDark) }
    }

    fun setFontFamily(font: String) {
        viewModelScope.launch { prefs.setFontFamily(font) }
    }

    fun setFontSize(size: Float) {
        viewModelScope.launch { prefs.setFontSize(size) }
    }

    fun setAppPassword(password: String) {
        viewModelScope.launch { prefs.setAppPassword(password) }
    }
}
