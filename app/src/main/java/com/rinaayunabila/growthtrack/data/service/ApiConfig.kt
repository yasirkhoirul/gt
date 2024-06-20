package com.rinaayunabila.growthtrack.data.service

import androidx.viewbinding.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object  ApiConfig {
    fun getApiService(): ApiService {
        val loggingInterceptor = if (BuildConfig.DEBUG)
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        else
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor).build()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://healthifynow-db.et.r.appspot.com/")
                .addConverterFactory(GsonConverterFactory.create()).client(client).build()
            return retrofit.create(ApiService::class.java)
    }
}
object RetrofitInstance {
    private const val BASE_URL = "https://maps.googleapis.com/maps/api/place/"

    val api: ApiService.PlacesApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService.PlacesApiService::class.java)
    }
}
