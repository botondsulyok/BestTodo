package com.example.besttodo.ui.todos.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.besttodo.R
import com.example.besttodo.databinding.TodosRowBinding
import com.example.besttodo.ui.todos.models.UiTodo

class TodosRecyclerViewAdapter : ListAdapter<UiTodo, TodosRecyclerViewAdapter.ViewHolder>(TodosDiffCallback) {

    interface TodoItemClickListener {
        fun onItemClick(todo: UiTodo?): Boolean
        fun onCheckBoxClick(todo: UiTodo?): Boolean
        fun onItemLongClick(todo: UiTodo?): Boolean
    }

    var todoClickListener: TodoItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = TodosRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: TodosRowBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                todoClickListener?.onItemClick(binding.todo)
            }
            binding.cbDone.setOnClickListener {
                val todo = binding.todo?.copy(checked = binding.cbDone.isChecked) ?: UiTodo()
                binding.cbDone.isChecked = true
                todoClickListener?.onCheckBoxClick(todo)
            }
            binding.cardViewTodosRow.setOnLongClickListener {
                todoClickListener?.onItemLongClick(binding.todo)
                true
            }
        }

        fun bind(todo: UiTodo) {
            binding.todo = todo
        }

    }

    object TodosDiffCallback: DiffUtil.ItemCallback<UiTodo>() {
        override fun areItemsTheSame(oldItem: UiTodo, newItem: UiTodo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UiTodo, newItem: UiTodo): Boolean {
            return oldItem == newItem
        }
    }

}