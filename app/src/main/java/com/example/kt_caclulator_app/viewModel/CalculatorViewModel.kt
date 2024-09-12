package com.example.kt_caclulator_app.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kt_caclulator_app.model.CalculatorModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class CalculatorUiState(
    val result: Double = 0.0,
    val prevResult: Double = 0.0,
    val operand1: Int = 0,
    val operand2: Int = 0,
    val fullOperation: String = "",
)

class CalculatorViewModel(private val calculatorModel: CalculatorModel) : ViewModel() {
    private val LOG_TAG = "CalculatorViewModel"
    private val _uiState = MutableStateFlow(CalculatorUiState())
    val uiState: StateFlow<CalculatorUiState> = _uiState.asStateFlow()

    fun onDigitClick(digit: Int) {
        Log.d(LOG_TAG, "Digit $digit clicked")
        _uiState.update {
            it.copy(
                operand1 = it.operand1 * 10 + digit,
                fullOperation = "${it.operand1 * 10 + digit}"

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