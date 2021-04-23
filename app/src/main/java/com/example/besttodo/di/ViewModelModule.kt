package com.example.besttodo.di

import androidx.lifecycle.ViewModel
import co.zsmb.rainbowcake.dagger.ViewModelKey
import com.example.besttodo.ui.todos.TodosViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(TodosViewModel::class)
    abstract fun bindTodosViewModel(todosViewModel: TodosViewModel): ViewModel

}