package com.salugan.gloapp.data.retrofit.responses

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class DiagnoseResponse(

	@field:SerializedName("diseaseName")
	val diseaseName: String,

	@field:SerializedName("accuracy")
	val accuracy: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("cause")
	val cause: String,

	@field:SerializedName("prevention")
	val prevention: String,

	@field:SerializedName("disclaimer")
	val disclaimer: String
) : Parcelable
