package com.example.exa_ii

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface GuitarDao {
    @Insert
    suspend fun insert(guitar: Guitar)

    @Update
    suspend fun update(guitar: Guitar)

    @Delete
    suspend fun delete(guitar: Guitar)

    @Query("SELECT * FROM guitar WHERE id = :id")
    fun getGuitar(id: Int): Flow<Guitar>

    @Query("SELECT * FROM guitar")
    fun getAllGuitars(): Flow<List<Guitar>>
}