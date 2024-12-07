package com.example.libraryapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.libraryapp.db.Book

class BookAdapter(
    private val books: List<Book>,
    private val onFavoriteClick: (Book) -> Unit
) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name)
        val author: TextView = itemView.findViewById(R.id.avtor)
        val price: TextView = itemView.findViewById(R.id.price)
        val count: TextView = itemView.findViewById(R.id.count)
        val image: ImageView = itemView.findViewById(R.id.book_image)
        val cartButton: ImageButton = itemView.findViewById(R.id.cart_btn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = books[position]
        holder.name.text = book.name
        holder.author.text = book.author
        holder.price.text = "${book.price} ₽"
        holder.count.text = "Количество: ${book.count}"
        // Загрузите изображение с помощью библиотеки, например Glide или Picasso
        // Glide.with(holder.image.context).load(book.image).into(holder.image)
        holder.image.setImageResource(book.image)
        holder.cartButton.setOnClickListener {
            onFavoriteClick(book)
        }
    }

    override fun getItemCount() = books.size
}