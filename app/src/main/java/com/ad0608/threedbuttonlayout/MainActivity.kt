package com.ad0608.threedbuttonlayout

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ad0608.threedbutton.ThreeDButtonLayout

class MainActivity : AppCompatActivity() {
    private val threeDButtonLayout: ThreeDButtonLayout by lazy {
        findViewById(R.id.threeDButtonLayout)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        threeDButtonLayout.setOnClickListener {
            Toast.makeText(this, "Button Clicked!!", Toast.LENGTH_SHORT).show()
        }
    }
}