package com.example.besttodo.data.disk.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo")
data class RoomTodo(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    val name: String = "",
    val checked: Boolean = false
)