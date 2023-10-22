package com.example.apigram2.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.apigram2.data.local.PreferenceManager
import kotlinx.coroutines.launch

class SettingViewModel(private val pref: PreferenceManager) : ViewModel() {

    fun getThemeSettings() = pref.ThemeSettingFlow().asLiveData()

    fun saveThemeSettings(isDark: Boolean) {
        viewModelScope.launch {
            pref.setThemeSetting(isDark)
        }
    }

    class Factory(private val pref: PreferenceManager) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T = SettingViewModel(pref) as T
    }
}