package com.rdp.contactapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rdp.contactapp.databinding.ActivitySplashScreenBinding
import java.util.*
import kotlin.concurrent.schedule


class SplashScreen : AppCompatActivity() {
    lateinit var binding : ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        Timer().schedule(2000)
        {
            startActivity(Intent(this@SplashScreen,contactsActivity::class.java))
        }
    }
}