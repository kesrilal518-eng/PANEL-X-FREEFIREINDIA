package com.panelx.freefireindia.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "panel_x_prefs")

class PreferencesRepository(private val context: Context) {

    private object Keys {
        val DARK_MODE = booleanPreferencesKey("dark_mode")
        val LANGUAGE = stringPreferencesKey("language")
        val LAST_PLAY_STYLE = stringPreferencesKey("last_play_style")
    }

    val darkMode: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[Keys.DARK_MODE] ?: true
    }

    val language: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[Keys.LANGUAGE] ?: "en"
    }

    val lastPlayStyle: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[Keys.LAST_PLAY_STYLE] ?: "Balanced"
    }

    suspend fun setDarkMode(enabled: Boolean) {
        context.dataStore.edit { it[Keys.DARK_MODE] = enabled }
    }

    suspend fun setLanguage(lang: String) {
        context.dataStore.edit { it[Keys.LANGUAGE] = lang }
    }

    suspend fun setLastPlayStyle(style: String) {
        context.dataStore.edit { it[Keys.LAST_PLAY_STYLE] = style }
    }

    suspend fun resetAll() {
        context.dataStore.edit { it.clear() }
    }
}
