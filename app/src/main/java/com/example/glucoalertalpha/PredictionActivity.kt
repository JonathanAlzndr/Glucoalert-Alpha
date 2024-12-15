package com.example.glucoalertalpha

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.glucoalertalpha.api.PredictionResponse
import com.example.glucoalertalpha.databinding.ActivityPredictionBinding

class PredictionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPredictionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPredictionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val prediction: PredictionResponse? =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) intent.getParcelableExtra(
                EXTRA_DATA,
                PredictionResponse::class.java
            ) else intent.getParcelableExtra(EXTRA_DATA)

        if(prediction != null) {
            Log.d(TAG, "onCreate: $prediction")
            binding.tvExplanation.text = prediction.explanation
            binding.tvCalculationDetails.text = prediction.calculationDetails
            binding.tvFinalResult.text = prediction.finalResult.toString()
        }

    }

    companion object {
        private const val TAG = "PredictionActivity"
        const val EXTRA_DATA = "EXTRA_DATA"
    }
}