package com.example.test1026

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.NumberPicker
import com.example.test1026.databinding.ActivityMainBinding
import com.example.test1026.databinding.ActivityTimeBinding

class TimeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTimeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val num_pic = binding.numberPicker
        val num_text = binding.numView
        val picked = num_pic.value
        num_pic.minValue = 0
        num_pic.maxValue = 12
        num_pic.wrapSelectorWheel = false
        num_pic.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        num_pic.setOnValueChangedListener{num_pic,i1,picked ->
            num_text.text=picked.toString()
        }
    }
}