package com.example.test1026

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import com.example.test1026.databinding.ActivityTimeBinding
import kotlinx.android.synthetic.main.activity_time.*
import java.util.Calendar
import java.util.Calendar.getInstance
import java.util.logging.Logger.global

class TimeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time)
        //객체 설정
        val timePicker: TimePicker = findViewById(R.id.timePicker)
        setTimePickerInterval(timePicker)
        //TimePicker 값 변경 이벤트
        timePicker.setOnTimeChangedListener { timePicker, hourOfDay, minute ->
            timeText.text="${hourOfDay}시 ${minute*5}분"

        }//setOnTimeChangedListener


    }//onCreate
}//AppCompatActivity

// 시간 간격을 5분 단위로 바꿔주는 함수 setTimePickerInterval()
private fun setTimePickerInterval(timePicker: TimePicker) {
    lateinit var binding: ActivityTimeBinding
    try {
        val TIME_PICKER_INTERVAL = 5
        val minutePicker = timePicker.findViewById(
            Resources.getSystem().getIdentifier(
                "minute", "id", "android"
            )
        ) as NumberPicker
        minutePicker.minValue = 0
        minutePicker.maxValue = 60 / TIME_PICKER_INTERVAL - 1
        val displayedValues: MutableList<String> = ArrayList()
        var i = 0
        while (i < 60) {
            displayedValues.add(String.format("%02d", i))
            i += TIME_PICKER_INTERVAL
        }
        minutePicker.displayedValues = displayedValues.toTypedArray()

    } //try
    catch (e: Exception) {
    }
} //setTimePickerInterval()
