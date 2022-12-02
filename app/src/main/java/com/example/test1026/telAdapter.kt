package com.example.test1026

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class TelListAdapter (val context: Context, val telList: ArrayList<TelItem>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view : View = LayoutInflater.from(context).inflate(R.layout.item, null)

        val Name = view.findViewById<TextView>(R.id.textName)
        val Phone = view.findViewById<TextView>(R.id.textPhone)

        val TelItem = telList[position]
        Name.text = TelItem.Name
        Phone.text = TelItem.Phone

        return view;
    }

    override fun getItem(position: Int): Any {
        return telList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return telList.size
    }

}