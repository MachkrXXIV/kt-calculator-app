package com.example.kt_caclulator_app.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kt_caclulator_app.model.CalculatorModel
import com.example.kt_caclulator_app.model.CalculatorUiState
import com.example.kt_caclulator_app.model.Operator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CalculatorViewModel(private val calculatorModel: CalculatorModel) : ViewModel() {
    private val LOG_TAG = "CalculatorViewModel"
    private val _uiState = MutableStateFlow(CalculatorUiState())
    val uiState: StateFlow<CalculatorUiState> = _uiState.asStateFlow()

    fun onDigitClick(digit: String) {
        Log.d(LOG_TAG, "Digit $digit clicked")
        _uiState.update {
            val newOperand = if (it.isNewOperation) digit else it.operand1 + digit
            it.copy(
                operand1 = newOperand,
                fullOperation = it.fullOperation + digit,
                isNewOperation = false
            )
        }
    }

    fun onOperatorClick(operator: String) {
        Log.d(LOG_TAG, "Operator $operator clicked")
        _uiState.update {
            val currentOperand = it.operand1.toDoubleOrNull() ?: 0.0
            val newResult = if (it.operator.isNotEmpty()) {
                calculatorModel.calculate(
                    it.result,
                    currentOperand,
                    Operator.fromString(it.operator)
                )
            } else {
                currentOperand
            }
            it.copy(
                result = newResult,
                operator = operator,
                operand2 = it.operand1,
                operand1 = "",
                fullOperation = "${it.fullOperation} $operator "
            )
        }
    }

    fun onEqualsClick() {
        Log.d(LOG_TAG, "Equals clicked")
        _uiState.update {
            val result = calculatorModel.calculate(
                _uiState.value.operand2.toDoubleOrNull() ?: 0.0,
                _uiState.value.operand1.toDoubleOrNull() ?: 0.0,
                Operator.fromString(it.operator)
            )
            it.copy(
                result = result,
                prevResult = result,
                operand1 = result.toString(),
                operand2 = "",
                operator = "",
                fullOperation = "${it.fullOperation} = $result",
                isNewOperation = true
            )
        }
    }

    fun onClearClick() {
        Log.d(LOG_TAG, "Clear clicked")
        calculatorModel.clear()
        _uiState.update {
            it.copy(
                result = 0.0,
                prevResult = 0.0,
                operand1 = "",
                operand2 = "",
                operator = "",
                fullOperation = "",
                isNewOperation = true
            )
        }
    }

    fun onDotClick() {
        Log.d(LOG_TAG, "Dot clicked")
        _uiState.update {
            val newOperand1 = it.operand1 + "."
            it.copy(
                operand1 = newOperand1,
                fullOperation = newOperand1
            )
        }
    }

    // big templated constructor
    class CalculatorViewModelFactory(private val model: CalculatorModel) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CalculatorViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CalculatorViewModel(model) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}