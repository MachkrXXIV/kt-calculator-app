package com.example.kt_caclulator_app.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kt_caclulator_app.model.CalculatorModel
import com.example.kt_caclulator_app.model.CalculatorUiState
import com.example.kt_caclulator_app.model.LastAction
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
            val newOperand = it.operand1 + digit
            if (it.lastAction == LastAction.EQUALS) {
                it.copy(
                    operand1 = digit,
                    fullOperation = digit,
                    lastAction = LastAction.DIGIT
                )
            } else {
                it.copy(
                    operand1 = newOperand,
                    fullOperation = it.fullOperation + digit,
                    lastAction = LastAction.DIGIT
                )
            }
        }
    }

    fun onOperatorClick(operator: String) {
        Log.d(LOG_TAG, "Operator $operator clicked")
        if (!isValidOperation(LastAction.OPERATOR)) {
            Log.d(LOG_TAG, "isValid: ${isValidOperation(LastAction.OPERATOR)}")
            return
        }

        _uiState.update {
            val currentOperand = it.operand1.toDoubleOrNull() ?: 0.0
            val newResult = if (it.operator.isNotEmpty()) {
                calculatorModel.calculate(
                    it.result.toDouble(),
                    currentOperand,
                    Operator.fromString(it.operator)
                )
            } else {
                currentOperand
            }
            it.copy(
                result = newResult.toString(),
                operator = operator,
                operand1 = "",
                operand2 = newResult.toString(),
                fullOperation = "${it.fullOperation} $operator ",
                lastAction = LastAction.OPERATOR
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
                operand1 = result,
                operand2 = "",
                operator = "",
                fullOperation = "${it.fullOperation} =",
                lastAction = LastAction.EQUALS
            )
        }
    }

    fun onClearClick() {
        Log.d(LOG_TAG, "Clear clicked")
        calculatorModel.clear()
        _uiState.update {
            it.copy(
                result = "0",
                prevResult = 0.0,
                operand1 = "",
                operand2 = "",
                operator = "",
                fullOperation = "",
                lastAction = LastAction.CLEAR
            )
        }
    }

    fun onUndoClick() {
        Log.d(LOG_TAG, "Undo clicked")
        _uiState.update {
            when (it.lastAction) {
                LastAction.DIGIT -> {
                    val newOperand1 = it.operand1.dropLast(1)
                    it.copy(
                        operand1 = newOperand1,
                        fullOperation = newOperand1,
                        lastAction = LastAction.DIGIT
                    )
                }

                LastAction.OPERATOR -> {
                    val newFullOperation = it.fullOperation.dropLast(2)
                    it.copy(
                        fullOperation = newFullOperation,
                        lastAction = LastAction.OPERATOR
                    )
                }

                LastAction.EQUALS -> {
                    val newFullOperation = it.fullOperation.dropLast(2)
                    it.copy(
                        fullOperation = newFullOperation,
                        lastAction = LastAction.EQUALS
                    )
                }

                LastAction.DOT -> {
                    val newOperand1 = it.operand1.dropLast(1)
                    it.copy(
                        operand1 = newOperand1,
                        fullOperation = newOperand1,
                        lastAction = LastAction.DOT
                    )
                }

                else -> it
            }
        }
    }

    fun onSignChangeClick() {
        Log.d(LOG_TAG, "Sign change clicked")
        _uiState.update {
            val newOperand1 = if (it.operand1.startsWith("-")) {
                it.operand1.drop(1)
            } else {
                "-${it.operand1}"
            }
            it.copy(
                operand1 = newOperand1,
                fullOperation = "${it.fullOperation} $newOperand1",
                lastAction = LastAction.DIGIT
            )
        }
    }

    fun onDotClick() {
        Log.d(LOG_TAG, "Dot clicked")
        if (!isValidOperation(LastAction.DOT)) {
            Log.d(LOG_TAG, "isValid: ${isValidOperation(LastAction.DOT)}")
            return
        }
        _uiState.update {
            val newOperand1 = it.operand1 + "."
            it.copy(
                operand1 = newOperand1,
                fullOperation = newOperand1,
                lastAction = LastAction.DOT
            )
        }
    }

    private fun isValidOperation(nextAction: LastAction): Boolean {
        val isValidAction = when (_uiState.value.lastAction) {
            LastAction.DIGIT -> nextAction == LastAction.OPERATOR ||
                    nextAction == LastAction.EQUALS ||
                    nextAction == LastAction.DOT

            LastAction.OPERATOR -> nextAction == LastAction.DIGIT
            LastAction.EQUALS -> nextAction == LastAction.DIGIT ||
                    nextAction == LastAction.OPERATOR ||
                    nextAction == LastAction.DOT

            LastAction.DOT -> nextAction == LastAction.DIGIT
            LastAction.NONE -> nextAction == LastAction.DIGIT || nextAction == LastAction.DOT
            else -> true
        }
        if (!isValidAction) {
            Log.d(LOG_TAG, "Invalid operation $nextAction after ${_uiState.value.lastAction}")
            _uiState.update {
                it.copy(
                    fullOperation = "ERROR: INVALID OPERATION",
                )
            }
        }
        return isValidAction
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