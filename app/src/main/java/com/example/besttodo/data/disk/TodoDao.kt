package com.example.besttodo.data.disk

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.besttodo.data.disk.models.RoomTodo

@Dao
interface TodoDao {

    @Query("SELECT * FROM todo")
    fun getTodos(): List<RoomTodo>

    @Insert
    fun insertTodo(todo: RoomTodo)

    @Update
    fun updateTodo(todo: RoomTodo)

}
