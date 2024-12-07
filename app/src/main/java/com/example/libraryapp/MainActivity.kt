package com.example.libraryapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_nav)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_frame, CatalogFragment())
                .commit()
        }

        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.catalog -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frame, CatalogFragment())
                        .commit()
                    true
                }

                R.id.cart -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frame, CartFragment())
                        .commit()
                    true
                }

                else -> false
            }
        }
    }
}