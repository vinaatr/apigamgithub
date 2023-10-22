package com.example.apigram2.data.local

import android.content.Context
import androidx.room.Room

class NoteRoomDb(private val context: Context) {
    private val db = Room.databaseBuilder(context, NoteAppDb::class.java, "usergithub.db")
        .allowMainThreadQueries()
        .build()

    val userDao = db.userDao()
}