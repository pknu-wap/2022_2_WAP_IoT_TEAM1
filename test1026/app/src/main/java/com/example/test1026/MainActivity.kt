package com.example.test1026

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.inputlayout.*
import kotlinx.android.synthetic.main.inputlayout.view.*
import kotlinx.android.synthetic.main.updatelayout.*
import kotlinx.android.synthetic.main.updatelayout.view.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnGoMyPage.setOnClickListener{
            val intent = Intent(this, MyinfoActivity::class.java)
            startActivity(intent)
        }
        btnGoAnalsys.setOnClickListener{
            val intent = Intent(this, AnalsysActivity::class.java)
            startActivity(intent)
        }
        btnGoEmer.setOnClickListener{
            val intent = Intent(this, EmergencyActivity::class.java)
            startActivity(intent)
        }
    }
}

