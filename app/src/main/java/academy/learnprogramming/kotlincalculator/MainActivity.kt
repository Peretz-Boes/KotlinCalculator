package academy.learnprogramming.kotlincalculator

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var result:EditText
    private lateinit var newNumber:EditText
    private val displayOperation by lazy (LazyThreadSafetyMode.NONE){ findViewById<TextView>(R.id.operation) }
    private var operand1:Double?=null
    private var operand2:Double=0.0
    private var pendingOperation="="

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        result=findViewById(R.id.result)
        newNumber=findViewById(R.id.editText2)
        val equalsButton=findViewById<Button>(R.id.button16)
        val plusButton=findViewById<Button>(R.id.button11)
        val minusButton=findViewById<Button>(R.id.button12)
        val timesButton=findViewById<Button>(R.id.button13)
        val dividedByButton=findViewById<Button>(R.id.button_divide)
        val listener=View.OnClickListener {v->
            val b=v as Button
            newNumber.append(b.text)
        }
        equalsButton.setOnClickListener(listener)
        plusButton.setOnClickListener(listener)
        minusButton.setOnClickListener(listener)
        timesButton.setOnClickListener(listener)
        dividedByButton.setOnClickListener(listener)
        val opListener=View.OnClickListener {v->
            val op=(v as Button).text.toString()
            val value=newNumber.text.toString()
            if (value.isNotEmpty()){
                performOperation(value,op)
            }
            pendingOperation=op
            displayOperation.text=pendingOperation
        }
        equalsButton.setOnClickListener(opListener)
        plusButton.setOnClickListener(opListener)
        minusButton.setOnClickListener(opListener)
        timesButton.setOnClickListener(opListener)
        dividedByButton.setOnClickListener(opListener)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun performOperation(value:String,operation:String){
        if (operand1==null){
            operand1=value.toDouble()
        }else{
            operand2=value.toDouble()
            if (pendingOperation=="=") {
                pendingOperation = operation
            }
            when(pendingOperation){
                "="->operand1=operand2
                "/"->if (operand2==0.0){
                    operand1=Double.NaN
                }else{
                    operand1=operand1!!/operand2
                }
                "*"->operand1=operand1!!*operand2
                "-"->operand1=operand1!!-operand2
                "+"->operand1=operand1!!+operand2
            }
        }
        result.setText(operand1.toString())
        newNumber.setText("")
    }

}
