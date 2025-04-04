package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    private lateinit var display: TextView
    private var firstNumber = 0.0
    private var operation = ""
    private var isNewInput = true

    private val decimalFormat = DecimalFormat("#.##########")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.textViewResult)

        // Set up digit buttons
        setupDigitButton(R.id.button0, "0")
        setupDigitButton(R.id.button1, "1")
        setupDigitButton(R.id.button2, "2")
        setupDigitButton(R.id.button3, "3")
        setupDigitButton(R.id.button4, "4")
        setupDigitButton(R.id.button5, "5")
        setupDigitButton(R.id.button6, "6")
        setupDigitButton(R.id.button7, "7")
        setupDigitButton(R.id.button8, "8")
        setupDigitButton(R.id.button9, "9")

        // Set up decimal button
        findViewById<Button>(R.id.buttonDecimal).setOnClickListener {
            if (isNewInput) {
                display.text = "0."
                isNewInput = false
            } else if (!display.text.contains(".")) {
                display.text = "${display.text}."
            }
        }

        // Set up operation buttons
        setupOperationButton(R.id.buttonAdd, "+")
        setupOperationButton(R.id.buttonSubtract, "-")
        setupOperationButton(R.id.buttonMultiply, "×")
        setupOperationButton(R.id.buttonDivide, "÷")

        // Set up equals button
        findViewById<Button>(R.id.buttonEquals).setOnClickListener {
            calculateResult()
        }

        // Set up clear button
        findViewById<Button>(R.id.buttonClear).setOnClickListener {
            display.text = "0"
            firstNumber = 0.0
            operation = ""
            isNewInput = true
        }

        // Set up delete button
        findViewById<Button>(R.id.buttonDelete).setOnClickListener {
            val currentText = display.text.toString()
            if (currentText.length > 1) {
                display.text = currentText.substring(0, currentText.length - 1)
            } else {
                display.text = "0"
                isNewInput = true
            }
        }

        // Set up plus/minus button
        findViewById<Button>(R.id.buttonPlusMinus).setOnClickListener {
            try {
                val value = display.text.toString().toDouble() * -1
                display.text = formatNumber(value)
            } catch (e: Exception) {
                display.text = "Error"
            }
        }

        // Set up percent button
        findViewById<Button>(R.id.buttonPercent).setOnClickListener {
            try {
                val value = display.text.toString().toDouble() / 100
                display.text = formatNumber(value)
            } catch (e: Exception) {
                display.text = "Error"
            }
        }
    }

    private fun setupDigitButton(buttonId: Int, digit: String) {
        findViewById<Button>(buttonId).setOnClickListener {
            if (isNewInput) {
                display.text = digit
                isNewInput = false
            } else if (display.text == "0") {
                display.text = digit
            } else {
                display.text = "${display.text}$digit"
            }
        }
    }

    private fun setupOperationButton(buttonId: Int, op: String) {
        findViewById<Button>(buttonId).setOnClickListener {
            try {
                if (operation.isNotEmpty()) {
                    calculateResult()
                }
                firstNumber = display.text.toString().toDouble()
                operation = op
                isNewInput = true
            } catch (e: Exception) {
                display.text = "Error"
            }
        }
    }

    private fun calculateResult() {
        if (operation.isEmpty()) return

        try {
            val secondNumber = display.text.toString().toDouble()
            val result = when (operation) {
                "+" -> firstNumber + secondNumber
                "-" -> firstNumber - secondNumber
                "×" -> firstNumber * secondNumber
                "÷" -> {
                    if (secondNumber == 0.0) throw ArithmeticException("Division by zero")
                    firstNumber / secondNumber
                }
                else -> 0.0
            }

            display.text = formatNumber(result)
            firstNumber = result
            operation = ""
            isNewInput = true
        } catch (e: ArithmeticException) {
            display.text = "Error"
            firstNumber = 0.0
            operation = ""
            isNewInput = true
        } catch (e: Exception) {
            display.text = "Error"
            firstNumber = 0.0
            operation = ""
            isNewInput = true
        }
    }

    private fun formatNumber(number: Double): String {
        return if (number == number.toLong().toDouble()) {
            number.toLong().toString()
        } else {
            decimalFormat.format(number)
        }
    }
}}