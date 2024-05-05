package com.example.myvirtualappa2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.myvirtualappa2.R


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "onCreate")

        // Set a breakpoint here
        setContentView(R.layout.activity_main)
        enableEdgeToEdge() // Enable edge-to-edge display
        Log.d("MainActivity", "Edge-to-edge display enabled")

        val startButton = findViewById<Button>(R.id.startButton)

        startButton.setOnClickListener {
            Log.d("MainActivity", "Start button clicked")
            // create the explicit intent
            val intent = Intent(this, SecondScreenActivity::class.java)
            // Set a breakpoint here
            startActivity(intent)
            Log.d("MainActivity", "Started SecondScreenActivity")
        }
    }
}