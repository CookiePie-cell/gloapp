package com.salugan.gloapp.ui.activities.detail

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.salugan.gloapp.R
import com.salugan.gloapp.data.Doctor
import com.salugan.gloapp.databinding.ActivityDoctorDetailBinding

class DoctorDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDoctorDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDoctorDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val doctor = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_DOCTOR, Doctor::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_DOCTOR)
        }

        doctor?.let {
            binding.apply {
                profileImageDetail.setImageResource(it.photo)
                tvSpecialist.text = it.specialist
                tvDoctorNameDetail.text = it.name
                tvExperienceDetail.text = getString(R.string.experience_value, it.experience)
                tvAboutDoctor.text = it.about
                tvWorkingHourDetail.text = it.workingTime
                tvConsultationFee.text = getString(R.string.consultation_fee_value, it.consultationFee)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return true
    }

    companion object {
        const val EXTRA_DOCTOR = "extra_doctor"
    }
}