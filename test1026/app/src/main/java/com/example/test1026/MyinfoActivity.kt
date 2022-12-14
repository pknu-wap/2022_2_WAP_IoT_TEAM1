package com.example.test1026

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_myinfo.*
import kotlinx.android.synthetic.main.inputlayout.*
import kotlinx.android.synthetic.main.inputlayout.view.*
import kotlinx.android.synthetic.main.updatelayout.*
import kotlinx.android.synthetic.main.updatelayout.view.*

class MyinfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_myinfo)

        btnClose.setOnClickListener {
            finish()
        }

        registerForContextMenu(button)
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        var mInflater = this.menuInflater
        if(v===button){
            menu!!.setHeaderTitle("피보호자 정보")
            mInflater.inflate(R.menu.menu1, menu)
        }
        //추가 if문으로 다수의 위젯에 context menu를 부착할 수 있음
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.insert->{
                var dialogView = View.inflate(this@MyinfoActivity, R.layout.inputlayout, null)
                var dlg = AlertDialog.Builder(this@MyinfoActivity)
                dlg.setTitle("정보 입력")
                dlg.setIcon(R.drawable.ic_menu_allfriends)
                dlg.setView(dialogView)
                dlg.setPositiveButton("확인"){dialogInterface, i ->
                    tvName.text = dialogView.userName_insert.text.toString()
                    tvNumber.text = dialogView.userNumber_insert.text.toString()
                    tvAddress.text = dialogView.userAddress_insert.text.toString()
                    tvSymptom.text = dialogView.userSymptom_insert.text.toString()
                }
                dlg.setNegativeButton("취소"){dialogInterface, i ->
                    Toast.makeText(this, "취소했습니다",Toast.LENGTH_SHORT).show()
                }
                dlg.show()
            }
            R.id.update->{
                var name = tvName.text.toString()
                var number = tvNumber.text.toString()
                var address = tvAddress.text.toString()
                var symptom = tvSymptom.text.toString()
                var dialogView = View.inflate(this@MyinfoActivity, R.layout.updatelayout, null)
                var dlg = AlertDialog.Builder(this@MyinfoActivity)
                dlg.setTitle("기존 정보 수정")
                dlg.setIcon(R.drawable.ic_menu_allfriends)
                dlg.setView(dialogView)
                dialogView.userName_update.setText(name)
                dialogView.userNumber_update.setText(number)
                dialogView.userAddress_update.setText(address)
                dialogView.userSymptom_update.setText(symptom)
                dlg.setPositiveButton("확인"){dialogInterface, i ->
                    tvName.text = dialogView.userName_update.text.toString()
                    tvNumber.text = dialogView.userNumber_update.text.toString()
                    tvAddress.text = dialogView.userAddress_update.text.toString()
                    tvSymptom.text = dialogView.userSymptom_update.text.toString()
                }
                dlg.setNegativeButton("취소"){dialogInterface, i ->
                    Toast.makeText(this, "취소했습니다",Toast.LENGTH_SHORT).show()
                }
                dlg.show()
            }
        }
        return true


    }

}