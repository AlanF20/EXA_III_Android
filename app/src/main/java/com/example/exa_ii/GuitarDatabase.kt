package com.example.exa_ii

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Guitar::class], version = 1, exportSchema = false)
abstract class GuitarDatabase: RoomDatabase() {
    abstract fun guitarDao(): GuitarDao
}