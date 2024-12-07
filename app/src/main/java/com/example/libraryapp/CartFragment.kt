package com.example.libraryapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.libraryapp.db.Book
import com.example.libraryapp.db.BookDao
import com.example.libraryapp.db.BookDatabase
import com.example.libraryapp.db.FavoriteBookDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CartFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FavoriteBookAdapter
    private lateinit var favoriteBookDao: FavoriteBookDao
    private lateinit var bookDao: BookDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.cart_recycler)
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        val db = BookDatabase.getDatabase(requireContext())
        favoriteBookDao = db.favoriteBookDao()
        bookDao = db.bookDao()

        loadFavoriteBooks()
    }

    private fun loadFavoriteBooks() {
        CoroutineScope(Dispatchers.Main).launch {
            val favoriteBooks = favoriteBookDao.getAllFavoriteBooks()
            val books = mutableListOf<Book?>()

            // Получаем все книги по id из списка избранных
            favoriteBooks.forEach { favoriteBook ->
                val book = bookDao.getBookById(favoriteBook.bookId)
                books.add(book)
            }

            adapter = FavoriteBookAdapter(books) { book -> onRemoveFromFavorites(book) }
            recyclerView.adapter = adapter
        }
    }

    private fun onRemoveFromFavorites(book: Book) {
        // Удаление книги из базы данных и адаптера
        CoroutineScope(Dispatchers.IO).launch {
            favoriteBookDao.delete(book.id) // Удаляем книгу из избранного
            withContext(Dispatchers.Main) {
                // Удаляем книгу из списка адаптера
                adapter.removeItem(book)
                Toast.makeText(context, "${book.name} удалена из избранного", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
