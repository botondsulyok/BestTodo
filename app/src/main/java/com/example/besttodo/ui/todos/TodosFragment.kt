package com.example.besttodo.ui.todos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.extensions.exhaustive
import com.example.besttodo.R
import com.example.besttodo.databinding.FragmentTodosBinding
import com.example.besttodo.ui.todos.dialogfragments.AddTodoBottomSheetDialogFragment
import com.example.besttodo.ui.todos.models.UiTodo
import com.example.besttodo.ui.todos.recyclerview.TodosRecyclerViewAdapter
import com.google.android.material.snackbar.Snackbar
import java.io.Serializable
import java.time.Duration

class TodosFragment : RainbowCakeFragment<TodosViewState, TodosViewModel>(),
    TodosRecyclerViewAdapter.TodoItemClickListener {

    override fun provideViewModel() = getViewModelFromFactory()
    override fun getViewResource() = 0

    private var _binding: FragmentTodosBinding? = null
    private val binding get() = _binding!!

    private val recyclerViewAdapter = TodosRecyclerViewAdapter()

    private var addTodoBottomSheetDialogFragment = AddTodoBottomSheetDialogFragment()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabCreateTodo.setOnClickListener {
            if(!addTodoBottomSheetDialogFragment.isAdded) {
                addTodoBottomSheetDialogFragment = AddTodoBottomSheetDialogFragment()
                addTodoBottomSheetDialogFragment.also {
                        val args = Bundle()
                        args.putSerializable(AddTodoBottomSheetDialogFragment.TODO_ACTION_VALUE, CreateTodoListener { todo ->
                            viewModel.addTodo(todo)
                        })
                        it.arguments = args
                    }.show(parentFragmentManager, AddTodoBottomSheetDialogFragment.ADD_TODO_VALUE)

            }
        }

        recyclerViewAdapter.todoClickListener = this
        binding.rvTodos.adapter = recyclerViewAdapter

        viewModel.getTodos()
    }

    override fun render(viewState: TodosViewState) {
        when (viewState) {
            is Loading -> {
                binding.progressBar.visibility = View.VISIBLE
            }
            is TodosLoaded -> {
                binding.progressBar.visibility = View.GONE
                recyclerViewAdapter.submitList(viewState.todosList)
            }
            is Failed -> {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(activity, viewState.message, Toast.LENGTH_LONG).show()
            }
            is Uploading -> {
                binding.progressBar.visibility = View.VISIBLE
            }
            is UploadSucess -> {
                Snackbar.make(binding.root, getString(R.string.txt_created), Snackbar.LENGTH_LONG).show()
            }
        }.exhaustive
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCheckBoxClick(todo: UiTodo?, isChecked: Boolean): Boolean {
        if(todo != null) {
            viewModel.updateTodo(todo.copy(checked = isChecked))
        }
        return true
    }

}

class CreateTodoListener(val action: (UiTodo) -> Unit) : Serializable