package com.salugan.gloapp.data.retrofit.responses

import com.google.gson.annotations.SerializedName

data class UserResponse(

	@field:SerializedName("mobile")
	val mobile: Long,

	@field:SerializedName("_id")
	val id: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("username")
	val username: String
)
