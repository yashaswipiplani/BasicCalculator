package com.piplani.basiccalculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    private var textBox: TextView? = null
    private var lastNumeric: Boolean = false
    private var lastDot: Boolean = false
    private var isDecimal: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textBox = findViewById(R.id.textBox)
    }
    fun onDigit(view: View) {
        textBox?.append((view as Button).text)
        lastNumeric = true
        lastDot = false
    }
    fun onCLR(view: View) {
        textBox?.text = ""
        isDecimal = false
        lastNumeric = false
        lastDot = false
    }
    fun onDecimal(view: View) {
        if (lastNumeric && !lastDot && !isDecimal) {
            textBox?.append(".")
            lastDot = true
            isDecimal = true
            lastNumeric = false
        }
    }
    fun onOperator(view: View){
        textBox?.text?.let{
            if(lastNumeric && !isOperatorAdded(it.toString())){
                textBox?.append((view as Button).text)
                lastNumeric = false
                lastDot = false
                isDecimal = false
            }
        }
    }
    fun onSubtract(view: View){
        textBox?.text?.let{
            if((lastNumeric||it.isEmpty()) && !isOperatorAdded(it.toString())){
                textBox?.append((view as Button).text)
                lastNumeric = false
                lastDot = false
                isDecimal = false
            }
        }
    }
    @SuppressLint("SetTextI18n")
    fun onEquals(view: View) {
        if (lastNumeric) {
            var textboxvalue = textBox?.text.toString()
            var prefix = ""
            try {
                if (textboxvalue.startsWith("-")){
                    prefix = "-"
                    textboxvalue = textboxvalue.substring(1)
                }
                if (textboxvalue.contains("-")){
                    val splitValue = textboxvalue.split("-")
                    var firstValue = splitValue[0]
                    val secondValue = splitValue[1]
                    if (prefix.isNotEmpty()){
                        firstValue = prefix + firstValue
                    }
                    textBox?.text =removeZeroAfterDot((firstValue.toDouble() - secondValue.toDouble()).toString())
                    isDecimal = false
                } else  if (textboxvalue.contains("+")){
                    val splitValue = textboxvalue.split("+")
                    var firstValue = splitValue[0]
                    val secondValue = splitValue[1]
                    if (prefix.isNotEmpty()){
                        firstValue = prefix + firstValue
                    }
                    textBox?.text = removeZeroAfterDot((firstValue.toDouble() + secondValue.toDouble()).toString())
                    isDecimal = false
                } else if (textboxvalue.contains("*")){
                    val splitValue = textboxvalue.split("*")
                    var firstValue = splitValue[0]
                    val secondValue = splitValue[1]
                    if (prefix.isNotEmpty()){
                        firstValue = prefix + firstValue
                    }
                    textBox?.text = removeZeroAfterDot((firstValue.toDouble() * secondValue.toDouble()).toString())
                    isDecimal = false
                } else  if (textboxvalue.contains("/")){
                    val splitValue = textboxvalue.split("/")
                    var firstValue = splitValue[0]
                    val secondValue = splitValue[1]
                    if (prefix.isNotEmpty()){
                        firstValue = prefix + firstValue
                    }
                    textBox?.text = removeZeroAfterDot((firstValue.toDouble() / secondValue.toDouble()).toString())
                    isDecimal = false
                }
            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }
    }
    private fun removeZeroAfterDot(result : String): String{
        var value = result
        if (result.endsWith(".0")){
            value = result.substring(0, result.length - 2)
        }
        return value
    }
    private fun isOperatorAdded(value: String): Boolean {
        return if (value.startsWith("-") && !(value.contains("+") || value.substring(1).contains("-")
                    || value.contains("*") || value.contains("/"))) {
            false
        } else {
            value.contains("+") || value.contains("-")
                    || value.contains("*") || value.contains("/")
        }
    }
}