package com.example.besttodo.ui.todos.dialogfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.besttodo.R
import com.example.besttodo.databinding.FragmentAddTodoBinding
import com.example.besttodo.ui.todos.CreateTodoListener
import com.example.besttodo.ui.todos.models.UiTodo
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddTodoBottomSheetDialogFragment
    : BottomSheetDialogFragment() {

    companion object {
        const val TODO_ACTION_VALUE = "ADD_ACTION_VALUE"
        const val ADD_TODO_VALUE = "ADD_TODO_VALUE"
    }

    private var _binding: FragmentAddTodoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTodoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAdd.setOnClickListener {
            if(binding.etName.text?.isEmpty() == true) {
                binding.etName.error = getString(R.string.error_cant_be_empty)
            }
            else {
                (arguments?.getSerializable(TODO_ACTION_VALUE) as? CreateTodoListener)?.action?.invoke(
                    UiTodo(name = binding.etName.text.toString())
                )
                dismiss()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}