package io.github.saptarshisarkar12.caly

import android.content.Intent
import android.os.Bundle
import android.widget.CalendarView
import android.widget.ImageButton
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    private lateinit var calendarView: CalendarView
    private lateinit var addButton: ImageButton

    override fun setTheme(resid: Int) {
        super.setTheme(com.google.android.material.R.style.Theme_AppCompat)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        calendarView = findViewById(R.id.calendarView)
        addButton = findViewById(R.id.addButton)
        addButton.setOnClickListener {
            val intent = Intent(this.applicationContext, AddEvent::class.java)
            startActivity(intent)
        }
    }
}