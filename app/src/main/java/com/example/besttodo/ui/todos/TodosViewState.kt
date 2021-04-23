package com.example.besttodo.ui.todos

import com.example.besttodo.ui.todos.models.UiTodo

sealed class TodosViewState

object Loading : TodosViewState()

data class TodosLoaded(val todosList: List<UiTodo> = listOf()) : TodosViewState()

data class Failed(val message: String) : TodosViewState()

object Uploading : TodosViewState()

object UploadSucess : TodosViewState()