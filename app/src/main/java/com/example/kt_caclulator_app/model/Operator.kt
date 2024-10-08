package com.example.kt_caclulator_app.model

enum class Operator {
    ADD, SUBTRACT, MULTIPLY, DIVIDE;

    companion object {
        fun fromString(value: String): Operator {
            return when (value) {
                "+" -> ADD
                "-" -> SUBTRACT
                "x" -> MULTIPLY
                "/" -> DIVIDE
                else -> ADD
            }
        }
    }
}