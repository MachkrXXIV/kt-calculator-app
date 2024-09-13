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
            val newOperand1 = it.operand1 + digit
            it.copy(
                operand1 = newOperand1,
                fullOperation = newOperand1
            )
        }
    }

    fun onOperatorClick(operator: String) {
        Log.d(LOG_TAG, "Operator $operator clicked")
        _uiState.update {
            it.copy(
                operator = operator,
                operand2 = it.operand1,
                operand1 = "",
                fullOperation = "${it.operand1} $operator"
            )
        }
    }

    fun onEqualsClick() {
        Log.d(LOG_TAG, "Equals clicked")
        val op = when (_uiState.value.operator) {
            "+" -> Operator.ADD
            "-" -> Operator.SUBTRACT
            "x" -> Operator.MULTIPLY
            "/" -> Operator.DIVIDE
            else -> Operator.ADD
        }
        val result = calculatorModel.calculate(
            _uiState.value.operand2.toDoubleOrNull() ?: 0.0,
            _uiState.value.operand1.toDoubleOrNull() ?: 0.0,
            op
        )
        _uiState.update {
            it.copy(
                result = result,
                prevResult = result,
                fullOperation = "${it.operand2} ${it.operator} ${it.operand1} = $result"
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
                fullOperation = ""
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