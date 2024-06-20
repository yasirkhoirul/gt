package com.rinaayunabila.growthtrack.ui.notifications

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.rinaayunabila.growthtrack.MainActivity2
import com.rinaayunabila.growthtrack.R

class TidakStunting : AppCompatActivity() {
    private lateinit var balik:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tidak_stunting)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        balik = findViewById(R.id.back)
        balik.setOnClickListener {
            startActivity(Intent(this,MainActivity2::class.java))
        }

    }
}