package com.example.phototransfer2

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val textView = TextView(this).apply {
            text = "Phototransfer2 skeleton ready"
            textSize = 24f
            setPadding(16, 16, 16, 16)
        }
        setContentView(textView)
    }
}
