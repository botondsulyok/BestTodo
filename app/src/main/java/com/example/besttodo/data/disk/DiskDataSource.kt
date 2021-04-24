package com.example.besttodo.data.disk

import android.util.Log
import com.example.besttodo.TodoApplication
import com.example.besttodo.data.Result
import com.example.besttodo.data.ResultFailure
import com.example.besttodo.data.ResultSuccess
import com.example.besttodo.data.disk.models.RoomTodo
import com.example.besttodo.domain.models.DomainTodo
import javax.inject.Inject

class DiskDataSource @Inject constructor() {

    val todoDao = TodoApplication.db.todoDao()

    fun getTodos(): Result<List<DomainTodo>, String> {
        return try {
            val todos = todoDao.getTodos().map { it.toDomainTodo() }
            ResultSuccess(todos)
        }
        catch (e: Exception) {
            ResultFailure(e.message.toString())
        }
    }

    fun addTodo(todo: DomainTodo): Result<Unit, String> {
        return try {
            todoDao.insertTodo(todo.toRoomTodo())
            ResultSuccess(Unit)
        }
        catch (e: Exception) {
            Log.e("Todo Add Exception", e.message.toString())
            ResultFailure(e.message.toString())
        }
    }

    fun updateTodo(todo: DomainTodo): Result<Unit, String> {
        return try {
            todoDao.updateTodo(todo.toRoomTodo())
            ResultSuccess(Unit)
        }
        catch (e: Exception) {
            Log.e("Todo Update Exception", e.message.toString())
            ResultFailure(e.message.toString())
        }
    }

    fun deleteTodo(todo: DomainTodo): Result<Unit, String> {
        return try {
            todoDao.deleteTodo(todo.toRoomTodo())
            ResultSuccess(Unit)
        }
        catch (e: Exception) {
            Log.e("Todo Delete Exception", e.message.toString())
            ResultFailure(e.message.toString())
        }
    }

    private fun RoomTodo.toDomainTodo(): DomainTodo {
        return DomainTodo(
            id = id,
            name = name,
            checked = checked
        )
    }

    private fun DomainTodo.toRoomTodo(): RoomTodo {
        return RoomTodo(
            id = id,
            name = name,
            checked = checked
        )
    }

}
