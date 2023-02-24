package com.example.todoapp.ui.main

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.data.adapters.TaskAdapter
import com.example.todoapp.data.db.AppDatabase
import com.example.todoapp.data.model.Task
import com.example.todoapp.data.repository.TaskRepository
import com.example.todoapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels {
        val database = AppDatabase.getDatabase(this)
        MainViewModelFactory(TaskRepository(database.userDao()))
    }
    private lateinit var binding: ActivityMainBinding
    private var tasksAdapter = TaskAdapter(this, ArrayList())

    private val taskListObserver = Observer<ArrayList<Task>> { handleTaskListObserver(it) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get All Tasks in BD
        viewModel.getALlTasks()

        // Handling the recycler view
        binding.rvTasks.layoutManager = LinearLayoutManager(this)
        binding.rvTasks.setHasFixedSize(true)

        // Handling the Task Observer
        viewModel.taskList.observe(this, taskListObserver)

        //Handling the creation of new tasks
        binding.btnCreate.setOnClickListener {
            handleOnClickBtnCreateTask()
        }


    }

    private fun handleOnClickBtnCreateTask() {
        viewModel.createTask(Task("2", "aldoJr", "09/12/2001"))
    }

    private fun handleTaskListObserver(it: ArrayList<Task>) {
        tasksAdapter = TaskAdapter(this, it)
        binding.rvTasks.adapter = tasksAdapter
        binding.tvCount.text = it.size.toString()
    }
}