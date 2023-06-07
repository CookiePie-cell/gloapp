package com.salugan.gloapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.salugan.gloapp.data.DiagnoseRepository
import com.salugan.gloapp.di.Injection
import com.salugan.gloapp.ui.activities.camera.UploadViewModel

class ViewModelFactory private constructor(
    private val diagnoseRepository: DiagnoseRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(UploadViewModel::class.java)) {
            return UploadViewModel(diagnoseRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideDiagnoseRepository())
            }.also { instance = it }
    }
}