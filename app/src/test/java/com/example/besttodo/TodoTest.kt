package com.example.besttodo

import com.example.besttodo.ui.todos.models.UiTodo
import com.example.besttodo.utils.validate
import com.google.common.truth.Truth.assertThat
import org.junit.Assert.*
import org.junit.Test

class TodoTest {

    @Test
    fun todoValidate_todoWithNotEmptyName_returnsTrue() {
        // Given
        val correctTodo = UiTodo(0, "Correct Name")

        // Then
        assertThat(correctTodo.validate()).isTrue()
    }

    @Test
    fun todoValidate_todoWithEmptyName_returnsTrue() {
        // Given
        val correctTodo = UiTodo(0, "")

        // Then
        assertThat(correctTodo.validate()).isFalse()
    }



}