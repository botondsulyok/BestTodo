package com.example.besttodo.domain.models

data class DomainTodo(
    val id: Long? = null,
    val name: String = "",
    val checked: Boolean = false
)