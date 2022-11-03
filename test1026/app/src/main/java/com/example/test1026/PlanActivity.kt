package com.example.test1026

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TextView
import androidx.annotation.RequiresApi
import java.util.Date

class PlanActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetText|18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plan)

        //객체 생성
        val dayText: TextView = findViewById(R.id.day_text)
        val datePicker: DatePicker = findViewById(R.id.datePicker)

        //년 월 일 담기
        val iYear: Int = datePicker.year
        val iMonth: Int = datePicker.month + 1
        val iDay: Int = datePicker.dayOfMonth

        //현재 날짜
        dayText.text = "${iYear}년 ${iMonth}월 ${iDay}일"
        //DatePicker 날씨 변환
        datePicker.setOnDateChangedListener { datePicker, year, month, dayOfMonth ->
            dayText.text = "${year}년 ${month + 1}월 ${dayOfMonth}일"
        }
    }//onCreate
}
