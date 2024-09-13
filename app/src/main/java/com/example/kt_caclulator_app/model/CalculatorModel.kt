package com.example.kt_caclulator_app.model

import android.util.Log
import androidx.lifecycle.ViewModel
import java.math.BigDecimal

class CalculatorModel() {
    private val LOG_TAG = "CalculatorModel"
    private var result: Double = 0.0
    private var prevResult: Double = 0.0

    fun calculate(num1: Double, num2: Double, operator: Operator): String {
        prevResult = result
        Log.d(LOG_TAG, "$num1 $operator $num2")
        result = when (operator) {
            Operator.ADD -> num1 + num2
            Operator.SUBTRACT -> num1 - num2
            Operator.MULTIPLY -> num1 * num2
            Operator.DIVIDE -> {
                if (num2 == 0.0) {
                    Double.NaN
                } else {
                    num1 / num2
                }
            }
        }
        result = if (result.isNaN()) result else BigDecimal(result).setScale(
            8,
            BigDecimal.ROUND_HALF_UP
        ).toDouble()
        Log.d(LOG_TAG, "Result: $result")
        return roundIfWholeNumber(result)
    }

    fun clear() {
        result = 0.0
        prevResult = 0.0
    }

    fun undo() {
        result = prevResult
    }

    fun getResult(): Double {
        return result
    }

    fun getPrevResult(): Double {
        return prevResult
    }

    private fun roundIfWholeNumber(value: Double): String {
        return if (value % 1.0 == 0.0) {
            value.toLong().toString()
        } else {
            value.toString()
        }
    }
}