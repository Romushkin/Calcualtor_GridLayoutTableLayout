package com.example.calcualtor_gridlayouttablelayout

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var toolbarMain: Toolbar

    private lateinit var inputET: EditText
    private lateinit var resultTV: TextView

    private lateinit var equalsBTN: Button
    private lateinit var resetBTN: Button

    private lateinit var oneBTN: Button
    private lateinit var twoBTN: Button
    private lateinit var threeBTN: Button
    private lateinit var fourBTN: Button
    private lateinit var fiveBTN: Button
    private lateinit var sixBTN: Button
    private lateinit var sevenBTN: Button
    private lateinit var eightBTN: Button
    private lateinit var nineBTN: Button
    private lateinit var zeroBTN: Button

    private lateinit var sumBTN: Button
    private lateinit var difBTN: Button
    private lateinit var divBTN: Button
    private lateinit var multBTN: Button

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        inputET = findViewById(R.id.inputET)
        resultTV = findViewById(R.id.resultTV)

        oneBTN = findViewById(R.id.oneBTN)
        twoBTN = findViewById(R.id.twoBTN)
        threeBTN = findViewById(R.id.threeBTN)
        fourBTN = findViewById(R.id.fourBTN)
        fiveBTN = findViewById(R.id.fiveBTN)
        sixBTN = findViewById(R.id.sixBTN)
        sevenBTN = findViewById(R.id.sevenBTN)
        eightBTN = findViewById(R.id.eightBTN)
        nineBTN = findViewById(R.id.nineBTN)
        zeroBTN = findViewById(R.id.zeroBTN)

        equalsBTN = findViewById(R.id.equalsBTN)
        resetBTN = findViewById(R.id.resetBTN)

        sumBTN = findViewById(R.id.sumBTN)
        difBTN = findViewById(R.id.difBTN)
        divBTN = findViewById(R.id.divBTN)
        multBTN = findViewById(R.id.multBTN)

        toolbarMain = findViewById(R.id.toolbarMain)
        setSupportActionBar(toolbarMain)
        title = "Калькулятор"
        toolbarMain.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        toolbarMain.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.ic_exit))

        inputET.isEnabled = false

        resetBTN.setOnClickListener {
            inputET.text.clear()
            resultTV.text = "result"
        }
        zeroBTN.setOnClickListener {
            inputET.text.append("0")
        }
        oneBTN.setOnClickListener {
            inputET.text.append("1")
        }
        twoBTN.setOnClickListener {
            inputET.text.append("2")
        }
        threeBTN.setOnClickListener {
            inputET.text.append("3")
        }
        fourBTN.setOnClickListener {
            inputET.text.append("4")
        }
        fiveBTN.setOnClickListener {
            inputET.text.append("5")
        }
        sixBTN.setOnClickListener {
            inputET.text.append("6")
        }
        sevenBTN.setOnClickListener {
            inputET.text.append("7")
        }
        eightBTN.setOnClickListener {
            inputET.text.append("8")
        }
        nineBTN.setOnClickListener {
            inputET.text.append("9")
        }
        divBTN.setOnClickListener {
            inputET.text.append("/") // ALT + 0247
        }
        multBTN.setOnClickListener {
            inputET.text.append("*") // ALT + 0215
        }
        difBTN.setOnClickListener {
            inputET.text.append("-")
        }
        sumBTN.setOnClickListener {
            inputET.text.append("+")
        }

        equalsBTN.setOnClickListener {
            val expression = inputET.text.toString()
            if (expression.isNotEmpty()) {
                try {
                    val result = evaluateExpression(expression)
                    resultTV.text = result.toString()
                } catch (e: Exception) {
                    resultTV.text = "Ошибка"
                }
            }
        }
    }

    private fun evaluateExpression(expression: String): Double {
        val operands = mutableListOf<Double>()
        val operators = mutableListOf<Char>()
        val currentNumber = StringBuilder()

        for (char in expression) {
            when {
                char.isDigit() || char == '.' -> currentNumber.append(char)
                char in "+-*/" -> {
                    if (currentNumber.isNotEmpty()) {
                        operands.add(currentNumber.toString().toDouble())
                        currentNumber.clear()
                    }
                    operators.add(char)
                }
            }
        }

        if (currentNumber.isNotEmpty()) {
            operands.add(currentNumber.toString().toDouble())
        }

        var index = 0
        while (index < operators.size) {
            when (operators[index]) {
                '*' -> {
                    operands[index] = operands[index] * operands[index + 1]
                    operands.removeAt(index + 1)
                    operators.removeAt(index)
                    index--
                }
                '/' -> {
                    if (operands[index + 1] == 0.0) throw ArithmeticException("Деление на ноль")
                    operands[index] = operands[index] / operands[index + 1]
                    operands.removeAt(index + 1)
                    operators.removeAt(index)
                    index--
                }
            }
            index++
        }

        index = 0
        while (index < operators.size) {
            when (operators[index]) {
                '+' -> {
                    operands[index] = operands[index] + operands[index + 1]
                    operands.removeAt(index + 1)
                    operators.removeAt(index)
                    index--
                }
                '-' -> {
                    operands[index] = operands[index] - operands[index + 1]
                    operands.removeAt(index + 1)
                    operators.removeAt(index)
                    index--
                }
            }
            index++
        }

        return operands.first()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.exitMenu) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}