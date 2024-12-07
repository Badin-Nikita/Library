package com.example.libraryapp.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class Book(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val author: String,
    val price: Double,
    val count: Int,
    val image: Int // Путь к изображению
)