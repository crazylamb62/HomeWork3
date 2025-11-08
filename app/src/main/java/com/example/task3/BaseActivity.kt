package com.example.task3

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun disableScreenshots() {
        window.addFlags(WindowManager.LayoutParams.FLAG_SECURE)
    }

    fun enableScreenshots() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
    }
}