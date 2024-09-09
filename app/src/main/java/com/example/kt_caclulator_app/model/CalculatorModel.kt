package com.example.kt_caclulator_app.model

import android.util.Log
import androidx.lifecycle.ViewModel

enum class Operation {
    ADD, SUBTRACT, MULTIPLY, DIVIDE
}

class CalculatorModel() {
    private val LOG_TAG = "CalculatorModel"
    private var result : Double = 0.0
    private var prevResult : Double = 0.0
    var operationStack = mutableListOf(Int)

    fun calculate(num1: Int, num2: Int, operator: Operation): Double {
        prevResult = result
        Log.d(LOG_TAG, "$num1 $operator $num2")
        when (operator) {
            Operation.ADD -> result = num1.toDouble() + num2.toDouble()
            Operation.SUBTRACT -> result = num1.toDouble() - num2.toDouble()
            Operation.MULTIPLY -> result = num1.toDouble() * num2.toDouble()
            Operation.DIVIDE -> {
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
        operationStack.clear()
    }

    fun undo() {
        if (operationStack.isNotEmpty()) {
            operationStack.removeAt(operationStack.size - 1)
        }
    }
}