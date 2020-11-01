package com.myproject.rakeshyadavclassnotes.views.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.bumptech.glide.Glide
import com.myproject.rakeshyadavclassnotes.R
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Glide
            .with(this)
            .load(R.drawable.book_cover)
            .error(R.drawable.ic_library_books)
            .placeholder(R.drawable.ic_library_books)
            .into(ivBookCover)


        Glide
            .with(this)
            .load(R.drawable.bg)
            .into(main)

        Handler().postDelayed({
                startActivity(Intent(this,
                    MainActivity::class.java))
                finish()
        },4000)
    }
}
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                