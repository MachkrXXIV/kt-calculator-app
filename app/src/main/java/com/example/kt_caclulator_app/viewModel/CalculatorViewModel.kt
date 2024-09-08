package com.example.kt_caclulator_app.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kt_caclulator_app.CalculatorApplication
import com.example.kt_caclulator_app.model.CalculatorModel

class CalculatorViewModel(private val calculatorModel: CalculatorModel) : ViewModel() {
    class CalculatorViewModelFactory(private val model: CalculatorModel) : ViewModelProvider.Factory {
        override fun <T: ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CalculatorViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CalculatorViewModel(model) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}