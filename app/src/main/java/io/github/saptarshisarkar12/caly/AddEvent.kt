package io.github.saptarshisarkar12.caly

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class AddEvent: AppCompatActivity() {
    private lateinit var addTime: Button
    private lateinit var addDate: Button
    private lateinit var saveButton: Button
    private lateinit var selectedTime: String
    private lateinit var selectedDate: String
    private var isMonthly: Boolean = false
    private var isYearly: Boolean = false
    private lateinit var yearlySwitch: Switch
    private lateinit var monthlySwitch: Switch
    private var alarmTime: Calendar = Calendar.getInstance()

    override fun setTheme(resid: Int) {
        super.setTheme(com.google.android.material.R.style.Theme_AppCompat)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_event)
        addTime = findViewById(R.id.time_button)
        addTime.setOnClickListener {
            getTime()
        }
        addDate = findViewById(R.id.date_button)
        addDate.setOnClickListener {
            getDate()
        }
        yearlySwitch = findViewById(R.id.yearly)
        yearlySwitch.setOnCheckedChangeListener { _, isChecked ->
            isYearly = isChecked
        }
        monthlySwitch = findViewById(R.id.monthly)
        monthlySwitch.setOnCheckedChangeListener { _, isChecked ->
            isMonthly = isChecked
        }
        saveButton = findViewById(R.id.save)
        saveButton.setOnClickListener {
            val intent = Intent(this, AlarmReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(this, 0, intent,
                PendingIntent.FLAG_IMMUTABLE)
            val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
            if (alarmManager.canScheduleExactAlarms()) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmTime.timeInMillis, pendingIntent)
                Log.i("AddEvent", "Alarm scheduled")
            } else {
                Log.e("AddEvent", "Cannot schedule exact alarms")
            }
            val intent2 = Intent(this, MainActivity::class.java)
            startActivity(intent2)
        }
    }

    private fun getTime() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        TimePickerDialog(
            this,
            { _, selectedHour, selectedMinute ->
                selectedTime = "$selectedHour:$selectedMinute"
                alarmTime = Calendar.getInstance()
                alarmTime.set(Calendar.HOUR_OF_DAY, selectedHour)
                alarmTime.set(Calendar.MINUTE, selectedMinute)
                alarmTime.set(Calendar.SECOND, 0)
            },
            hour,
            minute,
            true
        ).show()
    }

    private fun getDate() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DATE)
        DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                selectedDate = "$selectedDayOfMonth/$selectedMonth/$selectedYear"
            },
            year,
            month,
            dayOfMonth
        ).show()
    }
}