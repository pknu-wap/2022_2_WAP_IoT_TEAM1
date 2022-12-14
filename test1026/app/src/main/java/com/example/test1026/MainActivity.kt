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
import android.os.Build
import android.app.NotificationManager
import android.app.NotificationChannel
import android.content.Context
import androidx.core.app.NotificationCompat
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_myinfo.*
import kotlinx.android.synthetic.main.inputlayout2.view.*
import kotlinx.android.synthetic.main.updatelayout2.view.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        BtnSetTime.setOnClickListener{
            val intent = Intent(this, TimeActivity::class.java)
            startActivity(intent)
        }
        BtnGoToAnalsys.setOnClickListener{
            val intent = Intent(this, AnalsysActivity::class.java)
            startActivity(intent)
        }
        BtnGoToEmergency.setOnClickListener{
            val intent = Intent(this, EmergencyActivity::class.java)
            startActivity(intent)
        }
        BtnGoToMy.setOnClickListener{
            val intent = Intent(this, MyinfoActivity::class.java)
            startActivity(intent)
        }
        BtnPlan.setOnClickListener{
            val intent = Intent(this, PlanActivity::class.java)
            startActivity(intent)
        }

        registerForContextMenu(gdbutton)


        var option_1 = findViewById(R.id.option_1) as Button
        option_1.setOnClickListener {
            var builder = NotificationCompat.Builder(this, "MY_channel")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("?????? ?????? ???????????????.")
                .setContentText("1??? ??? ??????????????? ????????? ?????? ???????????????.")

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // ????????? ?????? ???????????? ????????? ?????? ??? ????????? ??????
                val channel_id = "MY_channel" // ????????? ?????? ?????? id ??????
                val channel_name = "????????????" // ?????? ?????? ??????
                val descriptionText = "?????????" // ?????? ????????? ??????
                val importance = NotificationManager.IMPORTANCE_DEFAULT // ?????? ???????????? ??????
                val channel = NotificationChannel(channel_id, channel_name, importance).apply {
                    description = descriptionText
                }

                // ?????? ?????? ????????? ???????????? ??????
                val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)

                // ?????? ??????: ????????? ?????? ID(ex: 1002), ?????? ??????
                notificationManager.notify(1002, builder.build())
            }
        }
    }
    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        var mInflater = this.menuInflater
        if(v===gdbutton){
            menu!!.setHeaderTitle("????????? ??????")
            mInflater.inflate(R.menu.menu2, menu)
        }
        //?????? if????????? ????????? ????????? context menu??? ????????? ??? ??????
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.insert->{
                var dialogView = View.inflate(this@MainActivity, R.layout.inputlayout2, null)
                var dlg = AlertDialog.Builder(this@MainActivity)
                dlg.setTitle("?????? ??????")
                dlg.setIcon(R.drawable.ic_menu_allfriends)
                dlg.setView(dialogView)
                dlg.setPositiveButton("??????"){dialogInterface, i ->
                    gdName.text = dialogView.guardName_insert.text.toString()
                    gdNumber.text = dialogView.guardNumber_insert.text.toString()
                }
                dlg.setNegativeButton("??????"){dialogInterface, i ->
                    Toast.makeText(this, "??????????????????",Toast.LENGTH_SHORT).show()
                }
                dlg.show()
            }
            R.id.update->{
                var name = gdName.text.toString()
                var number = gdNumber.text.toString()
                var dialogView = View.inflate(this@MainActivity, R.layout.updatelayout2, null)
                var dlg = AlertDialog.Builder(this@MainActivity)
                dlg.setTitle("?????? ?????? ??????")
                dlg.setIcon(R.drawable.ic_menu_allfriends)
                dlg.setView(dialogView)
                dialogView.guardName_update.setText(name)
                dialogView.guardNumber_update.setText(number)
                dlg.setPositiveButton("??????"){dialogInterface, i ->
                    gdName.text = dialogView.guardName_update.text.toString()
                    gdNumber.text = dialogView.guardNumber_update.text.toString()
                }
                dlg.setNegativeButton("??????"){dialogInterface, i ->
                    Toast.makeText(this, "??????????????????",Toast.LENGTH_SHORT).show()
                }
                dlg.show()
            }
        }
        return true


    }
}

