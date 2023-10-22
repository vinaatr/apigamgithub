package com.example.apigram2.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.apigram2.data.model.ResponseUser

@Dao
interface NoteUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: ResponseUser.Item)

    @Query("SELECT * FROM User")
    fun loadAll(): LiveData<MutableList<ResponseUser.Item>>

    @Query("SELECT * FROM User WHERE id LIKE :id LIMIT 1")
    fun findById(id: Int): ResponseUser.Item

    @Delete
    fun delete(user: ResponseUser.Item)
}