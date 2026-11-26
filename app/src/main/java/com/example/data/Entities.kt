package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "folders")
data class FolderEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val parentId: Long? = null, // null means root
    val color: String = "#0061A4",
    val icon: String = "folder",
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "documents")
data class DocumentEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val content: String, // structured JSON or HTML/text
    val docType: String = "text", // text, note, prompt, edu, code, media
    val folderId: Long? = null,
    val isFavorite: Boolean = false,
    val isTrash: Boolean = false,
    val description: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val modifiedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "media")
data class MediaEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val documentId: Long? = null,
    val mediaType: String, // image, audio, video, attachment
    val filePath: String,
    val originalFileName: String,
    val fileSize: Long = 0,
    val createdAt: Long = System.currentTimeMillis()
)
