package com.rinaayunabila.growthtrack.ui.history

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.rinaayunabila.growthtrack.MainActivity2
import com.rinaayunabila.growthtrack.R
import com.rinaayunabila.growthtrack.databinding.ActivityDetailHistoryBinding
import com.rinaayunabila.growthtrack.ui.notifications.Stunting

class DetailHistory : AppCompatActivity() {

    private lateinit var binding: ActivityDetailHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val babyName = intent.getStringExtra("babyName")
        val gender = intent.getIntExtra("gender", -1)
        val aa = intent.getIntExtra("berat", 0)
        val a1 = intent.getStringExtra("dibuat")
        val ab = intent.getIntExtra("prediksi", 0)
        val ac = intent.getIntExtra("umur",0)
        val ad = intent.getIntExtra("tinggi",0)
        val ae = intent.getIntExtra("menyusui",-1)
        binding.teks1.text=babyName
        binding.teks2.text=a1
        binding.teks4.text=aa.toString()
        binding.teks5.text=ad.toString()
        if (ab==1){
            binding.teks6.text="indicated to be stunting"
        }else{
            binding.teks6.text="indicated to be good"
        }
        val gambar:ImageView = binding.imageView
        if (gender == 1) {
            gambar.setImageResource(R.drawable.boy)
            binding.teks3.text="Laki laki"
        } else {
            gambar.setImageResource(R.drawable.girl)
            binding.teks3.text="Girl"
        }
        binding.button3.setOnClickListener {
            startActivity(Intent(this@DetailHistory, MainActivity2::class.java))
        }
        binding.button2.setOnClickListener {
            startActivity(Intent(this@DetailHistory,Stunting::class.java))
        }

        Log.d("hasil","$babyName dan $gender berat $aa prediksi $ab dibuat $a1 umurnya $ac tingginya $ad menyusui $ae  " )

    }
}