package com.example.glucoalertalpha.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("api/predict")
    fun prediction(@Body request: PredictionRequest): Call<PredictionResponse>
}