package com.salugan.gloapp.ui.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.cardview.widget.CardView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.commitNow
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.salugan.gloapp.R
import com.salugan.gloapp.commons.UploadViewModel
import com.salugan.gloapp.data.Result
import com.salugan.gloapp.databinding.ActivityMainBinding
import com.salugan.gloapp.ui.ViewModelFactory
import com.salugan.gloapp.ui.activities.camera.CameraActivity
import com.salugan.gloapp.ui.activities.doctors.DoctorsActivity
import com.salugan.gloapp.ui.activities.result.skin_disease.DiseaseActivity
import com.salugan.gloapp.ui.activities.store.StoreActivity
import com.salugan.gloapp.ui.fragments.profile.ProfileFragment
import com.salugan.gloapp.ui.fragments.timeline.TimelineFragment
import com.salugan.gloapp.utils.uriToFile

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var bottomSheetDialog: BottomSheetDialog

    private val uploadViewModel by viewModels<UploadViewModel> {
        ViewModelFactory.getInstance()
    }

    private val requestPhotoPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                startActivity(Intent(this@MainActivity, CameraActivity::class.java))
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }

    private val requestGalleryPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                openGallery()
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val menuHost: MenuHost = this

        val timelineFragment = TimelineFragment()
        val profileFragment = ProfileFragment()

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val currentFragment =
                    supportFragmentManager.findFragmentById(R.id.mainFragmentContainer)
                if (currentFragment is ProfileFragment) {
                    switchFragment(timelineFragment)
                    binding.bottomNavView.selectedItemId = R.id.mHome
                } else {
                    isEnabled = false
                    finish()
                }
            }
        }

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.appbar_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return if (menuItem.itemId == R.id.mDoctors) {
                    val intent = Intent(this@MainActivity, DoctorsActivity::class.java)
                    startActivity(intent)
                    true
                } else true
            }

        })

        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        binding.apply {
            bottomNavView.menu.getItem(1).isEnabled = false

            bottomNavView.setOnItemSelectedListener {
                when(it.itemId) {
                    R.id.mHome -> {
                        switchFragment(timelineFragment)
                        true
                    }
                    R.id.mProfile -> {
                        switchFragment(profileFragment)
                        true
                    }
                    else -> false
                }
            }

            fab.setOnClickListener {
                showBottomSheet()
            }
        }
    }

    private fun showProgressBar(show: Boolean) {
        if (show) {
            if (bottomSheetDialog.isShowing) {
                bottomSheetDialog.dismiss()
            }
            binding.progressBar.visibility = View.VISIBLE
            binding.contentContainer.alpha = 0.5f

            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
        } else {
            binding.progressBar.visibility = View.GONE
            binding.contentContainer.alpha = 1f
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
    }


    private fun showBottomSheet() {
        val bottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet, null)
        bottomSheetDialog = BottomSheetDialog(this@MainActivity, R.style.BottomSheetDialogTheme)
        bottomSheetDialog.setContentView(bottomSheetView)

        val takePhoto = bottomSheetView.findViewById<CardView>(R.id.undertoneCheck)
        val gallery = bottomSheetView.findViewById<CardView>(R.id.skinTypeCheck)

        takePhoto.setOnClickListener {
            requestPhotoPermissionLauncher.launch(CAMERA_PERMISSION)
        }

        gallery.setOnClickListener {
            requestGalleryPermissionLauncher.launch(GALLERY_PERMISSION)
        }

        bottomSheetDialog.show()
    }

    private fun openGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private fun switchFragment(fragment: Fragment) {
        supportFragmentManager.commitNow {
            replace(R.id.mainFragmentContainer, fragment, fragment::class.java.simpleName)
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { activityResult ->
        if (activityResult.resultCode == RESULT_OK) {
            val selectedImg: Uri = activityResult.data?.data as Uri
            val myFile = uriToFile(selectedImg, this@MainActivity)
            uploadViewModel.uploadPhoto(myFile).observe(this@MainActivity) { result ->
                when(result) {
                    is Result.Loading -> {
                        Toast.makeText(this@MainActivity, "Processing", Toast.LENGTH_SHORT).show()
                        showProgressBar(true)
                    }
                    is Result.Success -> {
                        showProgressBar(false)
                        Toast.makeText(this@MainActivity, "Success", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@MainActivity, DiseaseActivity::class.java)
                        intent.putExtra(DiseaseActivity.EXTRA_DISEASE_PHOTO, myFile)
                        intent.putExtra(DiseaseActivity.EXTRA_DISEASE_DATA, result.data)
                        startActivity(intent)
                    }
                    is Result.Error -> {
                        showProgressBar(false)
                        Toast.makeText(this@MainActivity, result.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    companion object {
        private const val CAMERA_PERMISSION = android.Manifest.permission.CAMERA
        private const val GALLERY_PERMISSION = android.Manifest.permission.READ_EXTERNAL_STORAGE
    }
}