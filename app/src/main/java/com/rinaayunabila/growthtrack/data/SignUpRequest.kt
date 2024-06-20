package com.rinaayunabila.growthtrack.data
import com.google.gson.annotations.SerializedName

data class SignUpRequest(
	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("password")
	val password: String,
)
