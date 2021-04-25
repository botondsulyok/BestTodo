package com.example.besttodo.utils

import com.example.besttodo.ui.todos.models.UiTodo

fun UiTodo.validate(): Boolean {
    return this.name.isNotEmpty()
}