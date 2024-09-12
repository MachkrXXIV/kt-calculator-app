package com.example.kt_caclulator_app.model

import android.util.Log
import androidx.lifecycle.ViewModel

class CalculatorModel() {
    private val LOG_TAG = "CalculatorModel"
    private var result: Double = 0.0
    private var prevResult: Double = 0.0

    fun calculate(num1: Int, num2: Int, operator: Operator): Double {
        prevResult = result
        Log.d(LOG_TAG, "$num1 $operator $num2")
        when (operator) {
            Operator.ADD -> result = num1.toDouble() + num2.toDouble()
            Operator.SUBTRACT -> result = num1.toDouble() - num2.toDouble()
            Operator.MULTIPLY -> result = num1.toDouble() * num2.toDouble()
            Operator.DIVIDE -> {
                if (num2 == 0) {
                    result = Double.NaN
                } else {
                    result = num1.toDouble() / num2.toDouble()
                }
            }
        }
        Log.d(LOG_TAG, "Result: $result")
        return result
    }

    fun clear() {
        result = 0.0
        prevResult = 0.0
    }

    fun undo() {
        result = prevResult
    }
}