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
        //거절되었거나 아직 수락하지 않은 권한(퍼미션)을 저장할 문자열 배열 리스트
        var rejectedPermissionList = ArrayList<String>()

        //필요한 퍼미션들을 하나씩 끄집어내서 현재 권한을 받았는지 체크
        for(permission in requiredPermissions){
            if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                //만약 권한이 없다면 rejectedPermissionList에 추가
                rejectedPermissionList.add(permission)
            }
        }
        //거절된 퍼미션이 있다면...
        if(rejectedPermissionList.isNotEmpty()){
            //권한 요청!
            val array = arrayOfNulls<String>(rejectedPermissionList.size)
            ActivityCompat.requestPermissions(this, rejectedPermissionList.toArray(array), multiplePermissionsCode)
        }
    }
    //권한 요청 결과 함수
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            multiplePermissionsCode -> {
                if(grantResults.isNotEmpty()) {
                    for((i, permission) in permissions.withIndex()) {
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            //권한 획득 실패
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
                    Activity.RESULT_OK ->  // 전송 성공
                    {
                        Toast.makeText(context, "전송 완료", Toast.LENGTH_SHORT).show()
                        println("전송 완료")
                    }
                    SmsManager.RESULT_ERROR_GENERIC_FAILURE ->  // 전송 실패
                    {
                        Toast.makeText(context, "전송 실패", Toast.LENGTH_SHORT).show()
                        println("전송 실패")
                    }
                    SmsManager.RESULT_ERROR_NO_SERVICE ->  // 서비스 지역 아님
                    {
                        Toast.makeText(context, "서비스 지역이 아닙니다", Toast.LENGTH_SHORT).show()
                        println("서비스 지역이 아닙니다")
                    }
                    SmsManager.RESULT_ERROR_RADIO_OFF ->  // 무선 꺼짐
                    {
                        Toast.makeText(context, "휴대폰이 꺼져있습니다", Toast.LENGTH_SHORT).show()
                        println("휴대폰이 꺼져있습니다")
                    }
                    SmsManager.RESULT_ERROR_NULL_PDU ->  // PDU 실패
                    {
                        Toast.makeText(context, "PDU Null", Toast.LENGTH_SHORT).show()
                        println("PDU Null")
                    }
                }
            }
        }, IntentFilter("SMS_SENT_ACTION"))

        // SMS가 도착했을때 실행
        registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                when (resultCode) {
                    Activity.RESULT_OK ->  // 도착 완료
                    {
                        Toast.makeText(context, "SMS 도착 완료", Toast.LENGTH_SHORT).show()
                        println("SMS 도착 완료")
                    }
                    Activity.RESULT_CANCELED ->  // 도착 안됨
                    {
                        Toast.makeText(context, "SMS 도착 실패", Toast.LENGTH_SHORT).show()
                        println("SMS 도착 실패")
                    }
                }
            }
        }, IntentFilter("SMS_DELIVERED_ACTION"))

        val SmsManager = SmsManager.getDefault()
        SmsManager.sendTextMessage(smsNumber, null, smsText, sentIntent, deliveredIntent)

    }

}