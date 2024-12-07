package com.example.libraryapp

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.animation.AccelerateInterpolator
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SplashActivity : AppCompatActivity() {

    private lateinit var splashImage: ImageView
    private lateinit var mainLayout: RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        splashImage = findViewById(R.id.splash_photo)
        mainLayout = findViewById(R.id.main)

        startAnimation()
    }

    private fun startAnimation() {
        // Анимация приближения для изображения
        val scaleX = ObjectAnimator.ofFloat(splashImage, "scaleX", 1.0f, 1.5f) // увеличиваем до 1.0
        val scaleY = ObjectAnimator.ofFloat(splashImage, "scaleY", 1.0f, 1.5f)
        val alphaLayout = ObjectAnimator.ofFloat(mainLayout, "alpha", 1f, 0f)
        val alphaImage = ObjectAnimator.ofFloat(splashImage, "alpha", 1f, 0f)

        // Установка длительности анимации
        scaleX.duration = 2000
        scaleY.duration = 2000
        alphaLayout.duration = 2000
        alphaImage.duration = 1000

        // Интерполяция для более плавной анимации
        scaleX.interpolator = AccelerateInterpolator()
        scaleY.interpolator = AccelerateInterpolator()
        alphaLayout.interpolator = AccelerateInterpolator()
        alphaImage.interpolator = AccelerateInterpolator()

        // Объединяем анимации
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(scaleX, scaleY, alphaLayout)
        animatorSet.start()

        // Переход к MainActivity после анимации
        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(intent)
                finish() // Закрываем SplashActivity
            }
        })

        // Запускаем исчезновение изображения после небольшой задержки
        alphaLayout.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                alphaImage.start()
            }
        })
    }
}