package com.example.test1026

import android.Manifest
import android.app.Activity
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_emergency.*
import kotlinx.android.synthetic.main.activity_myinfo.*

val MY_PERMISSIONS_REQUEST_RECEIVE_SMS :Int  = 1
val MY_PERMISSIONS_REQUEST_SEND_SMS :Int  = 2
private val multiplePermissionsCode = 100
private val requiredPermissions = arrayOf(
    Manifest.permission.SEND_SMS,
    Manifest.permission.RECEIVE_SMS
    //Manifest.permission.READ_PHONE_STATE,
    //Manifest.permission.ACCESS_COARSE_LOCATION,
)
private val currentVersion = arrayListOf<String>(
    "curVer: 1.0.0",
    "curDate: 2020.01.11"
)

class EmergencyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emergency)

        checkPermissions()
        btSend.setOnClickListener {
            var phone = editphone.text.toString()
            var message = editsms.text.toString()
            val smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(phone, null, message, null, null)
        }
        btVersion.setOnClickListener({
            for((index, currentVersion) in currentVersion.withIndex()){
                println("$currentVersion")
            }
        })
        btRegistScreen.setOnClickListener {
            val intent = Intent(this, RegistItemActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivityForResult(intent, 100);
        }
    }
    private fun checkPermissions() {
        //?????????????????? ?????? ???????????? ?????? ??????(?????????)??? ????????? ????????? ?????? ?????????
        var rejectedPermissionList = ArrayList<String>()

        //????????? ??????????????? ????????? ??????????????? ?????? ????????? ???????????? ??????
        for(permission in requiredPermissions){
            if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                //?????? ????????? ????????? rejectedPermissionList??? ??????
                rejectedPermissionList.add(permission)
            }
        }
        //????????? ???????????? ?????????...
        if(rejectedPermissionList.isNotEmpty()){
            //?????? ??????!
            val array = arrayOfNulls<String>(rejectedPermissionList.size)
            ActivityCompat.requestPermissions(this, rejectedPermissionList.toArray(array), multiplePermissionsCode)
        }
    }
    //?????? ?????? ?????? ??????
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            multiplePermissionsCode -> {
                if(grantResults.isNotEmpty()) {
                    for((i, permission) in permissions.withIndex()) {
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            //?????? ?????? ??????
                            Log.i("TAG", "The user has denied to $permission")
                            Log.i("TAG", "I can't work for you anymore then. ByeBye!")
                        }
                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        val sender = intent.getStringExtra("sender")
        val contents = intent.getStringExtra("contents")
        val receivedDate = intent.getStringExtra("receivedDate")
        val str = sender+contents+receivedDate
        println(sender+"\n" + contents+"\n" + receivedDate+"\n")
        var phone = editphone.text.toString().replace("[^0-9]", "")
        sendSMS(phone,str)

        //var message = editsms.text.toString()
        //val smsManager = SmsManager.getDefault()
        //smsManager.sendTextMessage(phone, null, str, null, null)

        super.onNewIntent(intent)
    }

    fun println(data: String) {
        textView.append(data + "\n")
    }

    fun sendSMS(smsNumber:String, smsText:String) {

        val intentSent : Intent = Intent("SMS_SENT_ACTION")
        val intentDelivered : Intent = Intent("SMS_DELIVERED_ACTION")
        val sentIntent = PendingIntent.getBroadcast(this,0,intentSent,0)
        val deliveredIntent = PendingIntent.getBroadcast(this,0,intentDelivered,0)

        registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                when (resultCode) {
                    Activity.RESULT_OK ->  // ?????? ??????
                    {
                        Toast.makeText(context, "?????? ??????", Toast.LENGTH_SHORT).show()
                        println("?????? ??????")
                    }
                    SmsManager.RESULT_ERROR_GENERIC_FAILURE ->  // ?????? ??????
                    {
                        Toast.makeText(context, "?????? ??????", Toast.LENGTH_SHORT).show()
                        println("?????? ??????")
                    }
                    SmsManager.RESULT_ERROR_NO_SERVICE ->  // ????????? ?????? ??????
                    {
                        Toast.makeText(context, "????????? ????????? ????????????", Toast.LENGTH_SHORT).show()
                        println("????????? ????????? ????????????")
                    }
                    SmsManager.RESULT_ERROR_RADIO_OFF ->  // ?????? ??????
                    {
                        Toast.makeText(context, "???????????? ??????????????????", Toast.LENGTH_SHORT).show()
                        println("???????????? ??????????????????")
                    }
                    SmsManager.RESULT_ERROR_NULL_PDU ->  // PDU ??????
                    {
                        Toast.makeText(context, "PDU Null", Toast.LENGTH_SHORT).show()
                        println("PDU Null")
                    }
                }
            }
        }, IntentFilter("SMS_SENT_ACTION"))

        // SMS??? ??????????????? ??????
        registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                when (resultCode) {
                    Activity.RESULT_OK ->  // ?????? ??????
                    {
                        Toast.makeText(context, "SMS ?????? ??????", Toast.LENGTH_SHORT).show()
                        println("SMS ?????? ??????")
                    }
                    Activity.RESULT_CANCELED ->  // ?????? ??????
                    {
                        Toast.makeText(context, "SMS ?????? ??????", Toast.LENGTH_SHORT).show()
                        println("SMS ?????? ??????")
                    }
                }
            }
        }, IntentFilter("SMS_DELIVERED_ACTION"))

        val SmsManager = SmsManager.getDefault()
        SmsManager.sendTextMessage(smsNumber, null, smsText, sentIntent, deliveredIntent)

    }

}