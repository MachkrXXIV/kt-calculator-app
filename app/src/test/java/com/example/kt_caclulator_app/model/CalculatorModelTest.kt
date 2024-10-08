package com.example.kt_caclulator_app.model

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import kotlin.math.exp

class CalculatorModelTest {
    private val calculatorModel = CalculatorModel()

    @Before
    fun setup() {
        calculatorModel.clear()
    }

    @Test
    fun `calculate addition`() {
        val result = calculatorModel.calculate(3.0, 5.0, Operator.ADD)
        val expected = "8"
        assertEquals(expected, result)
    }

    @Test
    fun `calculate subtraction`() {
        val result = calculatorModel.calculate(9.0, 4.0, Operator.SUBTRACT)
        val expected = "5"
        assertEquals(expected, result)
    }

    @Test
    fun `calculate multiplication`() {
        val result = calculatorModel.calculate(1.0, 7.0, Operator.MULTIPLY)
        val expected = "7"
        assertEquals(expected, result)
    }

    @Test
    fun `calculate division`() {
        val result = calculatorModel.calculate(25.0, 5.0, Operator.DIVIDE)
        val expected = "5"
        assertEquals(expected, result)
    }

    @Test
    fun `calculate addition with negative numbers`() {
        val result = calculatorModel.calculate(-4.6, 3.0, Operator.ADD)
        val expected = "-1.6"
        assertEquals(expected, result)
    }

    @Test
    fun `calculate subtraction with result being negative`() {
        val result = calculatorModel.calculate(25.0, 27.2, Operator.SUBTRACT)
        val expected = "-2.2"
        assertEquals(expected, result)
    }

    @Test
    fun `calculate multiplication with big numbers`() {
        // will round up to 6.666533e9 but actually has more digits
        val result = calculatorModel.calculate(66666.0, 99999.0, Operator.MULTIPLY)
        val expected = 6.666533e9
        assertEquals(expected, calculatorModel.getResult(), 1.05e7)
    }

    @Test
    fun `calculate division with big num2`() {
        val result = calculatorModel.calculate(2.0, 10000000.0, Operator.DIVIDE)
        val expected = "0.0000002"
        assertEquals(expected, result)
    }

    @Test
    fun `calculate chain addition`() {
        calculatorModel.calculate(24.0, 31.0, Operator.ADD)
        calculatorModel.calculate(calculatorModel.getResult(), 7.0, Operator.ADD)
        calculatorModel.calculate(calculatorModel.getResult(), 8.5, Operator.ADD)
        val expected = 70.5
        assertEquals(expected, calculatorModel.getResult(), 0.0)
    }

    @Test
    fun `calculate divide by zero shows NaN`() {
        val result = calculatorModel.calculate(5.0, 0.0, Operator.DIVIDE)
        val expected = "ERROR: DIVIDE BY ZERO"
        assertEquals(expected, result)
    }
}