package com.example.kt_caclulator_app

import android.app.Application
import com.example.kt_caclulator_app.model.CalculatorModel

class CalculatorApplication : Application() {
    val calculatorModel : CalculatorModel = CalculatorModel()
}