package com.rinaayunabila.growthtrack.data

import com.google.gson.annotations.SerializedName

data class UsersResponse(

    @field:SerializedName("msg")
    val msg: String? = null,

    @field:SerializedName("data")
    val data: Data1? = null,

    @field:SerializedName("success")
    val success: Boolean? = null
)

data class Data1(

    @field:SerializedName("uid")
    val uid: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("email")
    val email: String? = null
)
data class ResetRespon(

    @field:SerializedName("msg")
    val msg: String? = null
)

data class Reset(
    val Email:String
)

