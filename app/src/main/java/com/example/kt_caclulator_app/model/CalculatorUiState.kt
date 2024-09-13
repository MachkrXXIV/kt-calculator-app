package com.example.kt_caclulator_app.model

data class CalculatorUiState(
    val result: Double = 0.0,
    val prevResult: Double = 0.0,
    val operand1: String = "",
    val operand2: String = "",
    val operator: String = "",
    val fullOperation: String = "",
)
