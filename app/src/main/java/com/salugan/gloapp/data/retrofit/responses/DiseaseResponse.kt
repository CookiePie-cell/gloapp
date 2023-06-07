package com.salugan.gloapp.data.retrofit.responses

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class DiseaseResponse(

	@field:SerializedName("DiseaseName")
	val diseaseName: String,

	@field:SerializedName("accuracy")
	val accuracy: String
) : Parcelable
