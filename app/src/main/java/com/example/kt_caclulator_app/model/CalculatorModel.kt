package com.example.kt_caclulator_app.model

import android.util.Log
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
        Log.d(LOG_TAG, "Result: $result")
        return if (result.isNaN()) "ERROR: DIVIDE BY ZERO" else roundIfWholeNumber(result)
    }

    fun clear() {
        result = 0.0
        prevResult = 0.0
    }

    fun getResult(): Double {
        return result
    }

    private fun roundIfWholeNumber(value: Double): String {
        val v = BigDecimal(value).setScale(8, BigDecimal.ROUND_HALF_UP).stripTrailingZeros()
        return if (v.stripTrailingZeros().scale() <= 0) {
            Log.d(LOG_TAG, "plainString: ${v.toPlainString()}")
            v.toLong().toString()
        } else {
            Log.d(LOG_TAG, "plainString: ${v.toPlainString()}")
            v.toPlainString()
        }
    }
}