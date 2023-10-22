package com.example.apigram2.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.prefDataStore by preferencesDataStore("settings")

class PreferenceManager constructor(context: Context) {

    private val settingsDataStore = context.prefDataStore
    private val themeKEY = booleanPreferencesKey("theme_setting")

    fun ThemeSettingFlow(): Flow<Boolean> =
        settingsDataStore.data.map { preferences ->
            preferences[themeKEY] ?: false
        }

    suspend fun setThemeSetting(isDarkModeActive: Boolean) {
        settingsDataStore.edit { preferences ->
            preferences[themeKEY] = isDarkModeActive
        }
    }
}