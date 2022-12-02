package com.example.test1026

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Todo(

    var name : String,

) {
    @PrimaryKey(autoGenerate = true) var id : Int = 0
}