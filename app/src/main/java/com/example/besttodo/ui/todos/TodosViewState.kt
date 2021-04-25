package com.example.besttodo.ui.todos

import com.example.besttodo.ui.todos.models.UiTodo

sealed class TodosViewState

object Initial: TodosViewState()

object Loading : TodosViewState()

object Errored: TodosViewState()

data class TodosLoaded(val todosList: List<UiTodo> = listOf()) : TodosViewState()

object Uploading : TodosViewState()