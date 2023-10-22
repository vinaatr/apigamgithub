package com.example.apigram2

import androidx.lifecycle.*
import com.example.apigram2.data.local.PreferenceManager
import com.example.apigram2.data.remote.ApiClient
import com.example.apigram2.utils.Hasil
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainViewModel(private val preferences: PreferenceManager) : ViewModel() {

    val hasilUser = MutableLiveData<Hasil>()

    fun getTheme() = preferences.ThemeSettingFlow().asLiveData()

    fun getUser() {
        viewModelScope.launch {
            flow {
                val response = ApiClient
                    .apiService
                    .getUserGithub()

                emit(response)
            }.onStart {
                hasilUser.value = Hasil.Loading(true)
            }.onCompletion {
                hasilUser.value = Hasil.Loading(false)
            }.catch {
                it.printStackTrace()
                hasilUser.value = Hasil.Error(it)
            }.collect {
                hasilUser.value = Hasil.Sukses(it)
            }
        }
    }

    fun getUser(username: String) {
        viewModelScope.launch {
            flow {
                val response = ApiClient
                    .apiService
                    .searchUserGithub(
                        mapOf(
                            "q" to username,
                            "per_page" to 10
                        )
                    )

                emit(response)
            }.onStart {
                hasilUser.value = Hasil.Loading(true)
            }.onCompletion {
                hasilUser.value = Hasil.Loading(false)
            }.catch {
                it.printStackTrace()
                hasilUser.value = Hasil.Error(it)
            }.collect {
                hasilUser.value = Hasil.Sukses(it.items)
            }
        }
    }

    class Factory(private val preferences: PreferenceManager) :
        ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            MainViewModel(preferences) as T
    }
}