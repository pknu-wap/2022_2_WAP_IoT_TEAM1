package com.example.test1026

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.TimePicker

class TimeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time)
        //객체 설정
        val timePicker: TimePicker = findViewById(R.id.timePicker)
        val timeText: TextView = findViewById(R.id.time_text)

        //TimePicker 값 변경 이벤트
        timePicker.setOnTimeChangedListener { timePicker, hourOfDay, minute ->
            //변경 된 값 Textview에 담기
            timeText.text = "${hourOfDay}시 ${minute}분"
        }
    }//onCreate

        
        
    }
