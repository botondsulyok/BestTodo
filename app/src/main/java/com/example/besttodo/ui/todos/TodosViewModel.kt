package com.example.besttodo.ui.todos

import android.util.Log
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import com.example.besttodo.data.ResultFailure
import com.example.besttodo.data.ResultSuccess
import com.example.besttodo.ui.todos.models.UiTodo
import javax.inject.Inject

class TodosViewModel @Inject constructor(
    private val todosPresenter: TodosPresenter
) : RainbowCakeViewModel<TodosViewState>(Loading) {

    fun getTodos() = execute {
        viewState = Loading
        val result = todosPresenter.getTodos()
        viewState = when(result) {
            is ResultSuccess -> {
                TodosLoaded(result.value)
            }
            is ResultFailure -> {
                Failed(result.reason)
            }
        }
    }

    fun addTodo(todo: UiTodo) = execute {
        if(todo.validate()) {
            viewState = Uploading
            val result = todosPresenter.addTodo(todo)
            when(result) {
                is ResultSuccess -> {
                    viewState = UploadSucess
                    getTodos()
                }
                is ResultFailure -> {
                    viewState = Failed(result.reason)
                }
            }
        }
    }

    fun updateTodo(todo: UiTodo) = execute {
        val result = todosPresenter.updateTodo(todo)
        if(result is ResultFailure)
            viewState = Failed(result.reason)
    }

    private fun UiTodo.validate(): Boolean {
        return this.name.isNotEmpty()
    }

}