package com.mustafaademkayaaslan.planfollowchallenger.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.mustafaademkayaaslan.planfollowchallenger.R

class SplashScreen : AppCompatActivity() {

    private val splashTime:Long=1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


        Handler().postDelayed({
            startActivity(Intent(this,TaskAddActivity::class.java))
            finish()
        },splashTime)
    }
}