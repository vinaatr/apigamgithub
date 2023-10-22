package com.example.apigram2.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.apigram2.data.local.NoteRoomDb

class FavoriteViewModel(private val dbModule: NoteRoomDb) : ViewModel() {

    fun getUserFavorite() = dbModule.userDao.loadAll()

    class Factory(private val db: NoteRoomDb) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T = FavoriteViewModel(db) as T
    }
}