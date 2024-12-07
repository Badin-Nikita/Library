package com.example.libraryapp.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_books")
data class FavoriteBook(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val bookId: Long // Внешний ключ для связи с книгой
)