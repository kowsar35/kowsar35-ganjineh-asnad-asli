package com.example.data

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "settings")

class PreferencesManager(private val context: Context) {
    companion object {
        val THEME_NAME = stringPreferencesKey("theme_name")
        val DARK_MODE = booleanPreferencesKey("dark_mode")
        val FONT_FAMILY = stringPreferencesKey("font_family")
        val FONT_SIZE = floatPreferencesKey("font_size")
        val APP_PASSWORD = stringPreferencesKey("app_password")
    }

    val themeName: Flow<String> = context.dataStore.data.map { prefs -> prefs[THEME_NAME] ?: "Ocean Blue" }
    val darkMode: Flow<Boolean?> = context.dataStore.data.map { prefs -> prefs[DARK_MODE] }
    val fontFamily: Flow<String> = context.dataStore.data.map { prefs -> prefs[FONT_FAMILY] ?: "IRANSans" }
    val fontSize: Flow<Float> = context.dataStore.data.map { prefs -> prefs[FONT_SIZE] ?: 16f }
    val appPassword: Flow<String> = context.dataStore.data.map { prefs -> prefs[APP_PASSWORD] ?: "" }

    suspend fun setThemeName(theme: String) {
        context.dataStore.edit { prefs -> prefs[THEME_NAME] = theme }
    }

    suspend fun setDarkMode(isDark: Boolean?) {
        context.dataStore.edit { prefs ->
            if (isDark == null) prefs.remove(DARK_MODE) else prefs[DARK_MODE] = isDark
        }
    }

    suspend fun setFontFamily(font: String) {
        context.dataStore.edit { prefs -> prefs[FONT_FAMILY] = font }
    }

    suspend fun setFontSize(size: Float) {
        context.dataStore.edit { prefs -> prefs[FONT_SIZE] = size }
    }

    suspend fun setAppPassword(password: String) {
        context.dataStore.edit { prefs -> prefs[APP_PASSWORD] = password }
    }
}
