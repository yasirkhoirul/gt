package com.rinaayunabila.growthtrack.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(

    @field:SerializedName("imageURL")
    val imageURL: String,

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("createdBy")
    val createdBy: String,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("content")
    val content: String,

    @field:SerializedName("id")
    val id: String
) : Parcelable
