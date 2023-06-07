package com.salugan.gloapp.ui.activities.camera

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.salugan.gloapp.data.DiagnoseRepository
import com.salugan.gloapp.data.retrofit.responses.DiagnoseResponse
import com.salugan.gloapp.data.Result
import com.salugan.gloapp.utils.reduceFileImage
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class UploadViewModel(private val diagnoseRepository: DiagnoseRepository) : ViewModel(){

    fun uploadPhoto(
        file: File
    ) : LiveData<Result<DiagnoseResponse>> {
        val reducedFile = reduceFileImage(file)

        val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "file",
            reducedFile.name,
            requestImageFile
        )
        return diagnoseRepository.getDiagnose(imageMultipart)
    }
}
