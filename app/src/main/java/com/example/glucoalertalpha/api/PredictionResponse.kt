package com.example.glucoalertalpha.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PredictionResponse(

	@field:SerializedName("calculationDetails")
	val calculationDetails: String,

	@field:SerializedName("explanation")
	val explanation: String,

	@field:SerializedName("finalResult")
	val finalResult: Double
) : Parcelable
