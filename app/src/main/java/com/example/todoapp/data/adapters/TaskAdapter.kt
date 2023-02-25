package com.example.todoapp.data.adapters

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.data.model.Task
import com.example.todoapp.ui.main.MainViewModel

class TaskAdapter(
    private val ctx: Context,
    private val tasks: ArrayList<Task>,
    private val viewModel: MainViewModel
) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tvTitle)
        val btnDelete: ImageButton = itemView.findViewById(R.id.btnDelete)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(LayoutInflater.from(ctx).inflate(R.layout.task_item, parent, false))
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.title.text = tasks[position].title

        holder.btnDelete.setOnClickListener {
            viewModel.deleteTask(tasks[position])
        }

    }

    override fun getItemCount(): Int = tasks.size

}