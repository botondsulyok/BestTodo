package com.example.besttodo.ui.todos

import android.util.Log
import co.zsmb.rainbowcake.withIOContext
import com.example.besttodo.data.Result
import com.example.besttodo.data.ResultFailure
import com.example.besttodo.data.ResultSuccess
import com.example.besttodo.domain.TodosInteractor
import com.example.besttodo.domain.models.DomainTodo
import com.example.besttodo.ui.todos.models.UiTodo
import javax.inject.Inject

class TodosPresenter @Inject constructor(
    private val todosInteractor: TodosInteractor
) {

    suspend fun getTodos(): Result<List<UiTodo>, String> = withIOContext {
        when(val result = todosInteractor.getTodos()) {
            is ResultSuccess -> {
                ResultSuccess(value = result.value.map { it.toUiTodo() })
            }
            is ResultFailure -> {
                ResultFailure(reason = result.reason)
            }
        }
    }

    suspend fun addTodo(todo: UiTodo): Result<Unit, String> = withIOContext {
        when(val result = todosInteractor.addTodo(todo.toDomainTodo())) {
            is ResultSuccess -> {
                ResultSuccess(Unit)
            }
            is ResultFailure -> {
                ResultFailure(reason = result.reason)
            }
        }
    }

    suspend fun updateTodo(todo: UiTodo): Result<Unit, String> = withIOContext {
        when(val result = todosInteractor.updateTodo(todo.toDomainTodo())) {
            is ResultSuccess -> {
                ResultSuccess(Unit)
            }
            is ResultFailure -> {
                ResultFailure(reason = result.reason)
            }
        }
    }

    suspend fun deleteTodo(todo: UiTodo): Result<Unit, String> = withIOContext {
        when(val result = todosInteractor.deleteTodo(todo.toDomainTodo())) {
            is ResultSuccess -> {
                ResultSuccess(Unit)
            }
            is ResultFailure -> {
                ResultFailure(reason = result.reason)
            }
        }
    }

    private fun UiTodo.toDomainTodo(): DomainTodo {
        return DomainTodo(
            id = id,
            name = name,
            checked = checked
        )
    }

    private fun DomainTodo.toUiTodo(): UiTodo {
        return UiTodo(
            id = id,
            name = name,
            checked = checked
        )
    }

}