package com.salugan.gloapp.ui.activities.doctors

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.salugan.gloapp.adapter.ListDoctorAdapter
import com.salugan.gloapp.data.skinSpecialistDoctors
import com.salugan.gloapp.databinding.ActivityDoctorsBinding

class DoctorsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDoctorsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoctorsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showRecyclerView()
    }

    private fun showRecyclerView() {
        binding.apply {
            rvDoctors.layoutManager = LinearLayoutManager(this@DoctorsActivity)
            val adapter = ListDoctorAdapter(skinSpecialistDoctors)
            rvDoctors.adapter = adapter
        }
    }
}