package com.example.besttodo.data.disk

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.besttodo.data.disk.models.RoomTodo

@Database(entities = arrayOf(RoomTodo::class), version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun todoDao(): TodoDao

}
