package com.rinaayunabila.growthtrack.data

import com.google.gson.annotations.SerializedName

data class Postpredict(

    @field:SerializedName("msg")
    val msg: String? = null,

    @field:SerializedName("prediction")
    val prediction: List<Any?>? = null,

    @field:SerializedName("predictionsBinary")
    val predictionsBinary: Int? = null
)

data class PredictRequest(
    val babyName: String,
    val age: Int,
    val weight: Int,
    val height: Int,
    val gender: Int,
    val breastfeeding: Int
)
