package com.whiteshade.foodtourist.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.whiteshade.foodtourist.R

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        //Splash screen present for 2 seconds
        Handler().postDelayed({
            val intent = Intent(this@SplashScreen, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)

    }
    }
