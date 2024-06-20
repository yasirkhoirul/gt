package com.rinaayunabila.growthtrack.data
import com.google.gson.annotations.SerializedName

data class SignUpResponse(
	@field:SerializedName("msg")
	val msg: String,

	@field:SerializedName("success")
	val success: Boolean
)
