package com.rinaayunabila.growthtrack.data
import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("msg")
	val msg: String,

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("token")
val token: String
)

data class Data(

	@field:SerializedName("uid")
	val uid: String,

	@field:SerializedName("email")
	val email: String
)