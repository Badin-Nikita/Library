package com.example.libraryapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteBookDao {

    @Insert
    suspend fun insert(favoriteBook: FavoriteBook)

    @Query("SELECT * FROM favorite_books")
    suspend fun getAllFavoriteBooks(): List<FavoriteBook>

    @Query("DELETE FROM favorite_books WHERE bookId = :bookId")
    suspend fun delete(bookId: Long)
}