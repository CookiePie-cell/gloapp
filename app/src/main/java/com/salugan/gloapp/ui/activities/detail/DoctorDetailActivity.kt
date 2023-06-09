package com.salugan.gloapp.ui.activities.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.salugan.gloapp.databinding.ActivityDoctorDetailBinding

class DoctorDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDoctorDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoctorDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}