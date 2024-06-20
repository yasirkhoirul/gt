	package com.rinaayunabila.growthtrack.data
	
	import android.os.Parcelable
	import com.google.gson.annotations.SerializedName
	import kotlinx.parcelize.Parcelize

	data class ArticleResponse(
	
		@field:SerializedName("msg")
		val msg: String,
	
		@field:SerializedName("data")
		val data: List<DataItem>,
	
		@field:SerializedName("success")
		val success: Boolean
	)

	@Parcelize
	data class DataItem(
	
		@field:SerializedName("sourceURL")
		val sourceURL: String,
	
		@field:SerializedName("createdAt")
		val createdAt: String,
	
		@field:SerializedName("createdBy")
		val createdBy: String,
	
		@field:SerializedName("imageURL")
		val imageURL: String,
	
		@field:SerializedName("id")
		val id: String,
	
		@field:SerializedName("title")
		val title: String,
	
		@field:SerializedName("content")
		val content: String
	) : Parcelable
