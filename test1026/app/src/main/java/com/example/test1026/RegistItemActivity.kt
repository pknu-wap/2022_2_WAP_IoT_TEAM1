package com.example.test1026

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_regist_item.*
import java.security.AccessControlContext

var telRecvList = arrayListOf<TelItem>()
var telSendList = arrayListOf<TelItem>()


class RegistItemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regist_item)

        val teladpter = TelListAdapter(this, telRecvList)
        val teladpter2 = TelListAdapter(this, telSendList)

        recevlist.adapter = teladpter
        sendlist.adapter = teladpter2


        btRecvList.setOnClickListener {
            telRecvList.add(TelItem(edName.text.toString(), edPhone.text.toString()))
            teladpter.notifyDataSetChanged()
        }
        btSendList.setOnClickListener {
            telSendList.add(TelItem(edName.text.toString(), edPhone.text.toString()))
            teladpter2.notifyDataSetChanged()
        }


        btHomeScreen.setOnClickListener {
            val intent = Intent(this, EmergencyActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivityForResult(intent, 101)
        }

    }


}
