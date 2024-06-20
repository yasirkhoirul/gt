package com.rinaayunabila.growthtrack.data

import com.google.gson.annotations.SerializedName

data class Response(

    @field:SerializedName("data")
    val data: List<DataaItem?>? = null
)

data class DataaItem(

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("gender")
    val gender: Int? = null,

    @field:SerializedName("weight")
    val weight: Int? = null,

    @field:SerializedName("babyName")
    val babyName: String? = null,

    @field:SerializedName("predictionsBinary")
    val predictionsBinary: Int? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("breastfeeding")
    val breastfeeding: Int? = null,

    @field:SerializedName("userId")
    val userId: String? = null,

    @field:SerializedName("age")
    val age: Int? = null,

    @field:SerializedName("height")
    val height: Int? = null
)