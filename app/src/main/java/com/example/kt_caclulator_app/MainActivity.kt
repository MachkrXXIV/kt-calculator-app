package com.example.kt_caclulator_app

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.kt_caclulator_app.model.CalculatorModel
import com.example.kt_caclulator_app.viewModel.CalculatorViewModel

class MainActivity : AppCompatActivity() {

    // allows us for viewModel to persist longer than activity
    private val calculatorViewModel: CalculatorViewModel by viewModels {
        CalculatorViewModel.CalculatorViewModelFactory((application as CalculatorApplication).calculatorModel)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}