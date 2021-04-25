package com.example.besttodo

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.longClick
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.besttodo.ui.todos.TodosFragment
import com.example.besttodo.ui.todos.models.UiTodo
import com.example.besttodo.ui.todos.recyclerview.TodosRecyclerViewAdapter
import org.hamcrest.Matchers
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.instanceOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class TodosFragmentTest {

    val listItemNumberInTest = 2

    val todoInTest = UiTodo(2, "Name2")

    val todosList = MutableList<UiTodo>(listItemNumberInTest + 1) {
        UiTodo(it.toLong(), "Name${it}")
    }.apply {
        this[listItemNumberInTest] = todoInTest
    }

    @get:Rule
    var activityScenarioRule = activityScenarioRule<MainActivity>()

    @Before
    fun initRecyclerViewWithData() {
        activityScenarioRule.scenario.onActivity { activity: MainActivity? ->
            (activity?.findViewById<RecyclerView>(R.id.rvTodos)?.adapter
                    as? ListAdapter<UiTodo, TodosRecyclerViewAdapter.ViewHolder>)
                ?.submitList(todosList)
        }
    }

    @Test
    fun verifyUI() {
        // Then
        onView(withId(R.id.clTodos)).check(matches(isDisplayed()))
        onView(withId(R.id.fabCreateTodo)).check(matches(isClickable()))
    }

    @Test
    fun whenCreateTodoButtonClicked_thenAddTodoBottomSheetDialogFragmentVisible() {
        // When
        onView(withId(R.id.fabCreateTodo)).perform(click())

        // Then
        onView(withId(R.id.scrollViewAddTodo))
            .check(matches(isDisplayed()))
    }

    @Test
    fun whenTodoClicked_thenDialogFragmentWithTheNameOfTheTodoVisible() {
        // When
        onView(withId(R.id.rvTodos))
            .perform(actionOnItemAtPosition<TodosRecyclerViewAdapter.ViewHolder>(
                listItemNumberInTest, click()
            ))

        // Then
        onView(withText(todoInTest.name)).check(matches(isDisplayed()))
    }

    @Test
    fun whenTodoLongClicked_thenAlertDialogVisible() {
        // When
        onView(withId(R.id.rvTodos))
            .perform(actionOnItemAtPosition<TodosRecyclerViewAdapter.ViewHolder>(
                listItemNumberInTest, longClick()
            ))

        // Then
        onView(withText(R.string.title_warning))
            .check(matches(isDisplayed()))
    }


}