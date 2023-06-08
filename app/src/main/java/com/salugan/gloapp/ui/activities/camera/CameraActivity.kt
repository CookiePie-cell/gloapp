package com.salugan.gloapp.ui.activities.camera

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.salugan.gloapp.R
import com.salugan.gloapp.commons.UploadViewModel
import com.salugan.gloapp.databinding.ActivityCameraBinding
import com.salugan.gloapp.utils.createFile
import com.salugan.gloapp.data.Result
import com.salugan.gloapp.ui.ViewModelFactory
import com.salugan.gloapp.ui.activities.result.skin_disease.DiseaseActivity
import com.salugan.gloapp.utils.rotateFile

class CameraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCameraBinding
    private var imageCapture: ImageCapture? = null
    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    private var cameraProvider: ProcessCameraProvider? = null
    private var previewUseCase: Preview? = null

    private val uploadViewModel by viewModels<UploadViewModel> {
        ViewModelFactory.getInstance()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.captureImage.setOnClickListener {
            takePhoto()
        }
        binding.switchCamera.setOnClickListener {
            cameraSelector = if (cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
                CameraSelector.DEFAULT_FRONT_CAMERA
            } else CameraSelector.DEFAULT_BACK_CAMERA
            startCamera()
        }
    }

    override fun onResume() {
        super.onResume()
        hideSystemUI()
        startCamera()
    }

    private fun takePhoto() {
        showProgressBar(true)
        Toast.makeText(this@CameraActivity, "Processing", Toast.LENGTH_SHORT).show()
        val imageCapture = imageCapture ?: return
        val photoFile = createFile(application)
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    rotateFile(photoFile, cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA)
                    uploadViewModel.uploadPhoto(photoFile).observe(this@CameraActivity) { result ->
                        when(result) {
                            is Result.Loading -> {
//                                showProgressBar(true)
                            }
                            is Result.Success -> {
                                showProgressBar(false)
                                Toast.makeText(this@CameraActivity, "Captured", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this@CameraActivity, DiseaseActivity::class.java)
                                intent.putExtra(DiseaseActivity.EXTRA_DISEASE_PHOTO, photoFile)
                                intent.putExtra(DiseaseActivity.EXTRA_DISEASE_DATA, result.data)
                                startActivity(intent)
                                finish()
                            }
                            is Result.Error -> {
                                showProgressBar(false)
                                Toast.makeText(this@CameraActivity, result.error, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }

                override fun onError(exception: ImageCaptureException) {
                    Toast.makeText(
                        this@CameraActivity,
                        getString(R.string.failed_capture),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        )
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }

            previewUseCase = preview

            imageCapture = ImageCapture.Builder().build()

            try {
                cameraProvider?.unbindAll()
                cameraProvider?.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    imageCapture
                )

            } catch (exc: Exception) {
                Toast.makeText(
                    this@CameraActivity,
                    getString(R.string.failed_camera),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun showProgressBar(isLoading: Boolean) {
        if (isLoading) {
            cameraProvider?.unbind(previewUseCase)
            binding.progressBar3.visibility = View.VISIBLE
            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
        } else {
            try {
                cameraProvider?.unbindAll()
                cameraProvider?.bindToLifecycle(
                    this,
                    cameraSelector,
                    previewUseCase,
                    imageCapture
                )

            } catch (exc: Exception) {
                Toast.makeText(
                    this@CameraActivity,
                    getString(R.string.failed_camera),
                    Toast.LENGTH_SHORT
                ).show()
            }
            binding.progressBar3.visibility = View.GONE
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
    }

    private fun hideSystemUI() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

}