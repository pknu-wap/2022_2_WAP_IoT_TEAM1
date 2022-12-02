package com.example.test1026

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [Todo::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}