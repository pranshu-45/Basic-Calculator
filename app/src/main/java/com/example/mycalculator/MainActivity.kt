package com.example.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Display
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    private var displayText:TextView?=null
    private var hasDot: Boolean=false
    private var lastNumeric=false
    private var lastDot=false
    private var newInstance=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lastNumeric=true
        lastDot=false
        newInstance=true
        displayText=findViewById(R.id.DisplayText)
    }

    fun onDigit(view: View){
        var selectedBtn:Button= view as Button
        if(newInstance)
        {
            displayText?.text=""
            newInstance=false
        }

        displayText?.append(selectedBtn.text)
        lastDot=false
        lastNumeric=true
    }

    fun onClear(view:View){
        displayText?.text = ""
        hasDot=false
        newInstance=true
        lastNumeric=false
        lastDot=false
    }

    fun onDot(view:View){
        if(lastNumeric && !hasDot)
        {
            displayText?.append(".")
            hasDot=true
            lastNumeric=false
            lastDot=true
        }
    }

    fun onOperator(view:View) {
        if(newInstance && (view as Button).text=="-")
        {
            displayText?.text="-"
            newInstance=false
            lastNumeric=false
            lastDot=false
        }
        else
        {
            displayText?.text?.let {
                if(lastNumeric && !isOperatorAdded(it.toString())){
                    displayText?.append((view as Button).text)
                    lastNumeric=false
                    lastDot=false
                    hasDot=false
                }
            }
        }
    }

    fun isOperatorAdded(str:String):Boolean{
        return if(str.startsWith("-") && !isOperatorAdded(str.substring(1))){
            return false
        }
        else
        {
            str.contains("+") || str.contains("-") || str.contains("*") || str.contains("/")
        }
    }

    fun onEqual(view:View){
        if(lastNumeric)
        {
            var tvValue=displayText?.text.toString()
            var prefix=""
            try {
                if(tvValue.startsWith("-"))
                {
                    tvValue=tvValue.substring(1)
                    prefix="-"
                }
                if(tvValue.contains("-"))
                {
                    var splitValue = tvValue.split("-")
                    var one= splitValue[0]
                    var two= splitValue[1]
                    if(prefix.isNotEmpty())
                        one=prefix+one
                    displayText?.text=removeZeroAfterResult((one.toDouble()-two.toDouble()).toString())
                }
                else if(tvValue.contains("+"))
                {
                    var splitValue = tvValue.split("+")
                    var one= splitValue[0]
                    var two= splitValue[1]
                    if(prefix.isNotEmpty())
                        one=prefix+one
                    displayText?.text=removeZeroAfterResult((one.toDouble()+two.toDouble()).toString())
                }
                else if(tvValue.contains("*"))
                {
                    var splitValue = tvValue.split("*")
                    var one= splitValue[0]
                    var two= splitValue[1]
                    if(prefix.isNotEmpty())
                        one=prefix+one
                    displayText?.text=removeZeroAfterResult((one.toDouble()*two.toDouble()).toString())
                }
                else if(tvValue.contains("/"))
                {
                    var splitValue = tvValue.split("/")
                    var one= splitValue[0]
                    var two= splitValue[1]
                    if(prefix.isNotEmpty())
                        one=prefix+one
                    displayText?.text=removeZeroAfterResult((one.toDouble()/two.toDouble()).toString())
                }

            }catch (e:ArithmeticException)
            {
                e.printStackTrace()
            }

        }
    }

    fun removeZeroAfterResult(result:String):String{
        var value=result
        if(result[result.length-2]=='.' && result[result.length-1]=='0')
            value=value.substring(0,result.length-2)
        return value
    }
}