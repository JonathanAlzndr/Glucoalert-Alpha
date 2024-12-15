package com.example.glucoalertalpha

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.glucoalertalpha.api.ApiConfig
import com.example.glucoalertalpha.api.PredictionRequest
import com.example.glucoalertalpha.api.PredictionResponse
import com.example.glucoalertalpha.databinding.ActivityDiagnoseBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DiagnoseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDiagnoseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDiagnoseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val options = resources.getStringArray(R.array.options_array)
        val adapter = ArrayAdapter(
            this@DiagnoseActivity,
            android.R.layout.simple_dropdown_item_1line,
            options
        )

        binding.question1.setAdapter(adapter)
        binding.question2.setAdapter(adapter)
        binding.question3.setAdapter(adapter)
        binding.question4.setAdapter(adapter)
        binding.question5.setAdapter(adapter)
        binding.question6.setAdapter(adapter)
        binding.question7.setAdapter(adapter)
        binding.question8.setAdapter(adapter)
        binding.question9.setAdapter(adapter)
        binding.question10.setAdapter(adapter)

        binding.upload.setOnClickListener {

            var question1 = binding.question1.text.toString().trim()
            var question2 = binding.question2.text.toString().trim()
            var question3 = binding.question3.text.toString().trim()
            var question4 = binding.question4.text.toString().trim()
            var question5 = binding.question5.text.toString().trim()
            var question6 = binding.question6.text.toString().trim()
            var question7 = binding.question7.text.toString().trim()
            var question8 = binding.question8.text.toString().trim()
            var question9 = binding.question9.text.toString().trim()
            var question10 = binding.question10.text.toString().trim()
            println("DIAGNOSE value from convertChoice: $question10")


            if (validateInput(
                    question1,
                    question2,
                    question3,
                    question4,
                    question5,
                    question6,
                    question7,
                    question8,
                    question9,
                    question10
                )
            ) {
                val value1 = convertChoiceToDouble(question1)
                val value2 = convertChoiceToDouble(question2)
                val value3 = convertChoiceToDouble(question3)
                val value4 = convertChoiceToDouble(question4)
                val value5 = convertChoiceToDouble(question5)
                val value6 = convertChoiceToDouble(question6)
                val value7 = convertChoiceToDouble(question7)
                val value8 = convertChoiceToDouble(question8)
                val value9 = convertChoiceToDouble(question9)
                val value10 = convertChoiceToDouble(question10)

                val predictionRequest = PredictionRequest(
                    value1,
                    value2,
                    value3,
                    value4,
                    value5,
                    value6,
                    value7,
                    value8,
                    value9,
                    value10
                )
                Log.d("DIAGNOSE","onCreate: $predictionRequest")

                val client = ApiConfig.getApiService()
                client.prediction(predictionRequest).enqueue(object : Callback<PredictionResponse> {
                    override fun onResponse(
                        call: Call<PredictionResponse>,
                        response: Response<PredictionResponse>
                    ) {
                        if(response.isSuccessful) {
                            val result = response.body()
                            val intent = Intent(this@DiagnoseActivity, PredictionActivity::class.java)
                            intent.putExtra(PredictionActivity.EXTRA_DATA, result)
                            startActivity(intent)
                        }
                    }

                    override fun onFailure(p0: Call<PredictionResponse>, p1: Throwable) {
                        showToast(p1.message.toString())
                    }

                })
            } else {
                showToast("Ada input kosong")
            }

        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this@DiagnoseActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun convertChoiceToDouble(choice: String): Double {
        return when (choice) {
            "Sangat Yakin" -> 1.0
            "Yakin" -> 0.8
            "Cukup Yakin" -> 0.6
            "Kurang Yakin" -> 0.4
            "Tidak Tahu" -> 0.2
            "Tidak" -> 0.0
            else -> throw IllegalArgumentException("Pilihan tidak valid: $choice")
        }
    }

    private fun validateInput(vararg inputs: String): Boolean {
        for (input in inputs) {
            if (input.isEmpty()) {
                return false
            }
        }
        return true
    }


}