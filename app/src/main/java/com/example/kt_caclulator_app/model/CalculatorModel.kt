package com.example.kt_caclulator_app.model

import android.util.Log
import androidx.lifecycle.ViewModel

class CalculatorModel : ViewModel() {
    private val LOG_TAG = "CalculatorModel"
    private var result : Double = 0.0
    private var prevResult : Double = 0.0
    var operationStack = mutableListOf(Int)

    fun calculate(num1: Int, num2: Int, operator: Char): Double {
        prevResult = result
        Log.d(LOG_TAG, "$num1 $operator $num2")
        when (operator) {
            '+' -> result = num1.toDouble() + num2.toDouble()
            '-' -> result = num1.toDouble() - num2.toDouble()
            '*' -> result = num1.toDouble() * num2.toDouble()
            '/' -> result = num1.toDouble() / num2.toDouble()
        }
        Log.d(LOG_TAG, "Result: $result")
        return result
    }

    fun clear() {
        result = 0.0
        prevResult = 0.0
        operationStack.clear()
    }

    fun undo() {
        if (operationStack.isNotEmpty()) {
            operationStack.removeAt(operationStack.size - 1)
        }
    }
}