package com.example.currencyexchange

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    //variables
    lateinit var topAnim : Animation
    lateinit var bottomAnim : Animation
    lateinit var logo : ImageView
    lateinit var slogan : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        //Animations
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation)

        //Hooks
        logo = findViewById(R.id.main_background_2)
        slogan = findViewById(R.id.main_background_3)

        logo.setAnimation(topAnim)
        slogan.setAnimation(bottomAnim)

        Handler().postDelayed({
            val intent = Intent(this@MainActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }, 5000)
    }

}