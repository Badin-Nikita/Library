package com.example.libraryapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.libraryapp.db.Book

class FavoriteBookAdapter(
    private val books: MutableList<Book?>,
    private val onRemoveFromFavorites: (Book) -> Unit // Функция для удаления из избранного
) : RecyclerView.Adapter<FavoriteBookAdapter.FavoriteBookViewHolder>() {

    class FavoriteBookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name)
        val author: TextView = itemView.findViewById(R.id.avtor)
        val price: TextView = itemView.findViewById(R.id.price)
        val count: TextView = itemView.findViewById(R.id.count)
        val image: ImageView = itemView.findViewById(R.id.book_image)
        val deleteButton: ImageButton = itemView.findViewById(R.id.cart_btn) // Кнопка удаления
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteBookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_book_cart, parent, false)
        return FavoriteBookViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteBookViewHolder, position: Int) {
        val book = books[position]
        holder.name.text = book?.name
        holder.author.text = book?.author
        holder.price.text = "${book?.price} ₽"
        holder.count.text = "Количество: ${book?.count}"
        holder.image.setImageResource(book?.image ?: 0) // Установите изображение книги

        // Обработчик нажатия на кнопку удаления
        holder.deleteButton.setOnClickListener {
            book?.let { onRemoveFromFavorites(it) }
        }
    }

    override fun getItemCount() = books.size

    // Функция для удаления книги из списка
    fun removeItem(book: Book) {
        val position = books.indexOf(book)
        if (position != -1) {
            books.removeAt(position)
            notifyItemRemoved(position)
        }
    }
}
