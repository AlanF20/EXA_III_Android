package com.example.exa_ii

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Guitar(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo
    val brand: String,
    @ColumnInfo
    val price: Double,
    @ColumnInfo
    val model: String,
    @ColumnInfo
    val type: GuitarType
)
enum class GuitarType{
    SIX_STRING,
    TWELVE_STRING
}