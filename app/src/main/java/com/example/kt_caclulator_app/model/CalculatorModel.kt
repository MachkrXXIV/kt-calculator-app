package com.example.kt_caclulator_app.model

import androidx.lifecycle.ViewModel

class CalculatorModel : ViewModel() {
    var result = 0.0
    var operationStack = mutableListOf(Int)
}