package com.example.kt_caclulator_app

import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.kt_caclulator_app.model.CalculatorModel
import com.example.kt_caclulator_app.viewModel.CalculatorViewModel

class MainActivity : AppCompatActivity() {

    // allows us for viewModel to persist longer than activity
    private val calculatorViewModel: CalculatorViewModel by viewModels {
        CalculatorViewModel.CalculatorViewModelFactory((application as CalculatorApplication).calculatorModel)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val rootView = findViewById<ViewGroup>(R.id.main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // bind textViews
        val tvCalculationDisplay = findViewById<TextView>(R.id.calculationDisplay);
        val tvResultDisplay = findViewById<TextView>(R.id.resultDisplay);

        // bind digits
        val btnDigits = rootView.findViewsWithTag("digit")
        btnDigits.forEach { btn ->
            btn.setOnClickListener {
                calculatorViewModel.onDigitClick((btn as Button).text.toString())
                tvCalculationDisplay.text = calculatorViewModel.uiState.value.fullOperation
            }
        }

        // bind operators
        val btnOperators = rootView.findViewsWithTag("operator")
        btnOperators.forEach { btn ->
            btn.setOnClickListener {
                calculatorViewModel.onOperatorClick((btn as Button).text.toString())
                tvCalculationDisplay.text = calculatorViewModel.uiState.value.fullOperation
            }
        }

        // bind special buttons
        val btnEqual = findViewById<Button>(R.id.btnEqual)
        val btnClear = findViewById<Button>(R.id.btnClear)
        btnEqual.setOnClickListener {
            calculatorViewModel.onEqualsClick()
            tvResultDisplay.text = calculatorViewModel.uiState.value.result.toString()
            tvCalculationDisplay.text = calculatorViewModel.uiState.value.fullOperation
        }
        btnClear.setOnClickListener {
            calculatorViewModel.onClearClick()
            tvResultDisplay.text = calculatorViewModel.uiState.value.result.toString()
            tvCalculationDisplay.text = calculatorViewModel.uiState.value.fullOperation
        }
    }
}