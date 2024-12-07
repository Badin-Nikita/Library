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
import com.example.libraryapp.db.FavoriteBook
import com.example.libraryapp.db.FavoriteBookDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CatalogFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BookAdapter
    private lateinit var bookDao: BookDao
    private lateinit var favoriteBookDao: FavoriteBookDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_catalog, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.catalog_recycler)
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        val db = BookDatabase.getDatabase(requireContext())
        bookDao = db.bookDao()
        favoriteBookDao = db.favoriteBookDao()

        loadBooks()
    }

    private fun loadBooks() {
        CoroutineScope(Dispatchers.Main).launch {
            val books = bookDao.getAllBooks()
            if (books.isEmpty()) {
                // Если книг нет, добавляем тестовую книгу
                val testBooks = listOf(
                    Book(name = "Атомные привычки", author = "Джеймс Клир", price = 199.99, count = 5, image = R.drawable.book1),
                    Book(name = "Кафэ на краю земли", author = "Джон Стрелеки", price = 299.99, count = 3, image = R.drawable.book2),
                    Book(name = "Самый богатый человек", author = "Джорж Сэмюэль", price = 399.99, count = 2, image = R.drawable.book3),
                    Book(name = "1984", author = "Джордж Оруэлл", price = 249.99, count = 7, image = R.drawable.book4),
                    Book(name = "Твоё сердце будет разбито", author = "Анна Джейн", price = 149.99, count = 10, image = R.drawable.book5),
                    Book(name = "Десять негритят", author = "Агата Кристи", price = 359.99, count = 4, image = R.drawable.book6),
                    Book(name = "Опасная игра бабули", author = "Кристен Перрин", price = 279.99, count = 6, image = R.drawable.book7),
                    Book(name = "Нежно-денежно", author = "Ольга Примаченко", price = 319.99, count = 8, image = R.drawable.book8),
                    Book(name = "Круть", author = "Виктор Пелевин", price = 219.99, count = 5, image = R.drawable.book9),
                    Book(name = "Сказать жизни да!", author = "Виктор Франкл", price = 199.99, count = 3, image = R.drawable.book10),
                    Book(name = "Психотрюки", author = "Игорь Рызов", price = 299.99, count = 4, image = R.drawable.book11),
                    Book(name = "Хороших девочек не убивают", author = "Холли Джексон", price = 349.99, count = 7, image = R.drawable.book12),
                    Book(name = "Четвертое крыло", author = "Реббека Ярос", price = 259.99, count = 6, image = R.drawable.book13),
                    Book(name = "Внутри убийцы", author = "Майк Омер", price = 229.99, count = 10, image = R.drawable.book14),
                    Book(name = "Осаму дедзай", author = "Дадзай Осаму", price = 399.99, count = 2, image = R.drawable.book15),
                    Book(name = "Зеленый свет", author = "Мэттью Макконахи", price = 289.99, count = 8, image = R.drawable.book16),
                    Book(name = "Поклонник", author = "Анна Джейн", price = 239.99, count = 4, image = R.drawable.book17),
                    Book(name = "Преступление и наказание", author = "Федор Достоевский", price = 279.99, count = 5, image = R.drawable.book18),
                    Book(name = "Уголовный кодекс", author = "Россия", price = 319.99, count = 9, image = R.drawable.book19),
                    Book(name = "Доказательная психотематика", author = "Тимофей Кармацкий", price = 329.99, count = 3, image = R.drawable.book20),
                    Book(name = "Северус", author = "Лорри Ким", price = 249.99, count = 6, image = R.drawable.book21),
                    Book(name = "Спеши любить", author = "Николас Спаркс", price = 359.99, count = 2, image = R.drawable.book22),
                    Book(name = "Коралина: повесть", author = "Нил Гейман", price = 279.99, count = 7, image = R.drawable.book23),
                    Book(name = "Думай медленно, решай быстро", author = "Даниэль Канеман", price = 199.99, count = 10, image = R.drawable.book24),
                    Book(name = "Дикий зверь", author = "Жоэль Диккер", price = 269.99, count = 4, image = R.drawable.book25),
                    Book(name = "Три товарища", author = "Эрих Ремарк", price = 299.99, count = 3, image = R.drawable.book26),
                    Book(name = "Отцы и дети", author = "Иван Тургенев", price = 219.99, count = 5, image = R.drawable.book27),
                    Book(name = "Мастер и Маргарита", author = "Михаил Булгаков", price = 349.99, count = 6, image = R.drawable.book28),
                    Book(name = "Портрет Дориана Грея", author = "Оскар Уайльд", price = 249.99, count = 4, image = R.drawable.book29),
                    Book(name = "Искусство любить", author = "Эрих Фромм", price = 399.99, count = 2, image = R.drawable.book30)
                )

                bookDao.insertAll(testBooks)
            }
            adapter = BookAdapter(bookDao.getAllBooks()) { book -> onFavoriteClicked(book) }
            withContext(Dispatchers.Main) {
                recyclerView.adapter = adapter
            }
        }
    }

    private fun onFavoriteClicked(book: Book) {
        CoroutineScope(Dispatchers.IO).launch {
            val favoriteBook = FavoriteBook(bookId = book.id)
            favoriteBookDao.insert(favoriteBook)
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "${book.name} добавлена в избранное", Toast.LENGTH_SHORT).show()
            }
        }
    }

}