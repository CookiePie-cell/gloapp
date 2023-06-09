package com.salugan.gloapp.ui.activities.result.skin_disease

import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.salugan.gloapp.R
import com.salugan.gloapp.data.retrofit.responses.DiagnoseResponse
import com.salugan.gloapp.databinding.ActivityDiseaseBinding
import java.io.File
import java.text.DecimalFormat

class DiseaseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDiseaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiseaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val diagnose = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_DISEASE_DATA, DiagnoseResponse::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_DISEASE_DATA)
        }

        val photo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra(EXTRA_DISEASE_PHOTO, File::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra(EXTRA_DISEASE_PHOTO)
        } as File

        diagnose?.let {
            binding.apply {
                tvDiseaseDiagnose.text = it.diseaseName
                tvDiseaseDescription.text = it.description
                tvDiseaseCause.text = it.cause
                tvDiseasePrevention.text = it.prevention
                tvDiseaseDisclaimer.text = it.disclaimer

                val df = DecimalFormat("#.##")
                val accuracy = df.format(it.accuracy.toDouble())

                tvAccuracy.text = getString(R.string.accuracy, accuracy)
            }
        }

        binding.btnHomeScreenFromDisease.setOnClickListener {
            finish()
        }

        photo.let { file ->
            binding.imgDiseasePhoto.setImageBitmap(BitmapFactory.decodeFile(file.path))
        }
    }

    companion object {
        const val EXTRA_DISEASE_DATA = "extra_disease_data"
        const val EXTRA_DISEASE_PHOTO = "extra_disease_photo"
    }
}