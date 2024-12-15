package com.example.glucoalertalpha.api

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PredictionRequest(
    var seringMakan: Double,
    var seringHaus: Double,
    var seringKencing: Double,
    var riwayatDiabetes: Double,
    var junkFood: Double,
    var jarangOlahraga: Double,
    var lukaSusahSembuh: Double,
    var obesitas: Double,
    var hipertensi: Double,
    var etnis: Double
) : Parcelable