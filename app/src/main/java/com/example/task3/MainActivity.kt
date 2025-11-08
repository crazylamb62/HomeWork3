package com.example.task3

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.toColorInt
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

//Set all Activity extends by BaseActivity class
class MainActivity : BaseActivity() {

    private lateinit var editText: EditText
    private lateinit var backgroundColor: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<Button>(R.id.button_id).setOnClickListener {
            val intent = Intent(this, ActivityB::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        editText = findViewById(R.id.plain_text_input)
        backgroundColor = findViewById<ConstraintLayout>(R.id.main)

        if (savedInstanceState != null) {
            val savedText = savedInstanceState.getString("textViewFieldValue")
            val savedBackgroundColor = savedInstanceState.getString("BackgroundColor")
            editText.setText(savedText)
            setColor(savedBackgroundColor!!.toInt())
        }

        findViewById<Button>(R.id.button_gen).setOnClickListener {
            val randomColor = randomNumberGen()
            randomColor.toString().also { editText.setText(it) }
        }

        findViewById<Button>(R.id.button_setColor).setOnClickListener {
            val number = parseColorInput(editText.text.toString())
            if (number in 0..16_777_215) {
                setColor(colorFormat(number).toColorInt())
            } else {
                //Additional task №3. Show an error message
                editText.error = "You have enter wrong color"
            }
        }

        //addition task № 1 & 2 & 4
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        disableScreenshots()
        setShowWhenLocked(true)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val currentText = editText.text.toString()
        val currentColor = (backgroundColor.background as ColorDrawable).color
        outState.putString("textViewFieldValue", currentText)
        outState.putString("BackgroundColor", currentColor.toString())
    }

    fun randomNumberGen(): Int {
        return (0..16_777_215).random()
    }

    fun colorFormat(number: Int): String {
        val customHexFormat = HexFormat {
            upperCase = true
            number {
                minLength = 6
                prefix = "#"
                removeLeadingZeros = true
            }
        }
        return number.toHexString(customHexFormat)
    }

    private fun parseColorInput(input: String): Int {
        return try {
            input.toInt()
        } catch (e: NumberFormatException) {
            -1
        }
    }

    private fun setColor(color: Int) {
        backgroundColor.setBackgroundColor(color)
    }
}