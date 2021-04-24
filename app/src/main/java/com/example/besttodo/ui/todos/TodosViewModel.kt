package com.example.besttodo.ui.todos

import android.content.Context
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import com.example.besttodo.R
import com.example.besttodo.data.ResultFailure
import com.example.besttodo.data.ResultSuccess
import com.example.besttodo.ui.todos.models.UiTodo
import javax.inject.Inject

class TodosViewModel @Inject constructor(
    private val todosPresenter: TodosPresenter,
    private val context: Context
) : RainbowCakeViewModel<TodosViewState>(Loading) {

    companion object {
        const val MAX_NUMBER_OF_TODOS = 5
    }

    fun getTodos() = execute {
        viewState = Loading
        viewState = when(val result = todosPresenter.getTodos()) {
            is ResultSuccess -> {
                TodosLoaded(result.value)
            }
            is ResultFailure -> {
                Failed(result.reason)
            }
        }
    }

    fun addTodo(todo: UiTodo) = execute {
        if(!todo.validate()) {
            viewState = Failed(context.getString(R.string.error_invalid_name))
        }
        else if(!canAddUncheckedTodo()) {
            viewState = Failed(context.getString(R.string.error_todos_limit, MAX_NUMBER_OF_TODOS))
        }
        else {
            viewState = Uploading
            viewState = when(val result = todosPresenter.addTodo(todo)) {
                is ResultSuccess -> {
                    ActionSuccess(context.getString(R.string.txt_created))
                }
                is ResultFailure -> {
                    Failed(result.reason)
                }
            }
        }
        getTodos()
    }

    fun updateTodo(todo: UiTodo) = execute {
        if(!todo.checked && !canAddUncheckedTodo()) {
            viewState = Failed(context.getString(R.string.error_todos_limit, MAX_NUMBER_OF_TODOS))
        }
        else {
            when(val result = todosPresenter.updateTodo(todo)) {
                is ResultFailure -> {
                    viewState = Failed(result.reason)
                }
            }
        }
        getTodos()
    }

    fun deleteTodo(todo: UiTodo) = execute {
        viewState = when(val result = todosPresenter.deleteTodo(todo)) {
            is ResultSuccess -> {
                ActionSuccess(context.getString(R.string.txt_deleted))
            }
            is ResultFailure -> {
                Failed(result.reason)
            }
        }
        getTodos()
    }

    private fun UiTodo.validate(): Boolean {
        return this.name.isNotEmpty()
    }

    private fun canAddUncheckedTodo(): Boolean {
        val currentViewState = viewState
        return currentViewState is TodosLoaded &&
                currentViewState.todosList.count { !it.checked } < MAX_NUMBER_OF_TODOS
    }

}