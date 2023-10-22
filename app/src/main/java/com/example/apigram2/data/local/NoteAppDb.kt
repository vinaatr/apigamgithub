package com.example.apigram2.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.apigram2.data.model.ResponseUser

@Database(entities = [ResponseUser.Item::class], version = 1, exportSchema = false)
abstract class NoteAppDb : RoomDatabase() {
    abstract fun userDao(): NoteUserDao
}