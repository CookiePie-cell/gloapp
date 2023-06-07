package com.salugan.gloapp

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.commitNow
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.salugan.gloapp.databinding.ActivityMainBinding
import com.salugan.gloapp.ui.activities.camera.CameraActivity
import com.salugan.gloapp.ui.activities.result.undertone.UndertoneActivity
import com.salugan.gloapp.ui.activities.store.StoreActivity
import com.salugan.gloapp.ui.fragments.profile.ProfileFragment
import com.salugan.gloapp.ui.fragments.timeline.TimelineFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

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
                Toast.makeText(this, "Open Gallery", Toast.LENGTH_SHORT).show()
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
                return if (menuItem.itemId == R.id.mStore) {
                    val intent = Intent(this@MainActivity, StoreActivity::class.java)
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

    private fun showBottomSheet() {
        val bottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet, null)
        val bottomSheetDialog = BottomSheetDialog(this@MainActivity,R.style.BottomSheetDialogTheme)
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

    private fun switchFragment(fragment: Fragment) {
        supportFragmentManager.commitNow {
            replace(R.id.mainFragmentContainer, fragment, fragment::class.java.simpleName)
        }
    }

    companion object {
        private const val CAMERA_PERMISSION = android.Manifest.permission.CAMERA
        private const val GALLERY_PERMISSION = android.Manifest.permission.READ_EXTERNAL_STORAGE
    }
}