package com.example.todoapp.ui.main

import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.R
import com.example.todoapp.data.adapters.TaskAdapter
import com.example.todoapp.data.db.AppDatabase
import com.example.todoapp.data.model.Task
import com.example.todoapp.data.repository.TaskRepository
import com.example.todoapp.databinding.ActivityMainBinding
import com.example.todoapp.databinding.BottomSheetDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels {
        val database = AppDatabase.getDatabase(this)
        MainViewModelFactory(TaskRepository(database.userDao()))
    }
    private lateinit var binding: ActivityMainBinding
    private lateinit var tasksAdapter: TaskAdapter

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
        showBottomSheetDialog()
    }

    private fun handleTaskListObserver(it: ArrayList<Task>) {
        tasksAdapter = TaskAdapter(this, it, viewModel)
        binding.rvTasks.adapter = tasksAdapter
        binding.tvCount.text = it.size.toString()
    }

    private fun showBottomSheetDialog() {
        val dialog = BottomSheetDialog(this).apply {
            window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        }
        val dialogBinding = BottomSheetDialogBinding.inflate(layoutInflater, null, false)

        dialogBinding.btnCreate.setOnClickListener {
            val title: String = dialogBinding.etTitle.text.toString()
            if (title == "") {
                Toast.makeText(this, getString(R.string.title_error_message), Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            viewModel.createTask(
                Task(
                    id = (viewModel.taskList.value?.size?.plus(1)).toString(),
                    title = title
                )
            )

            dialog.hide()
        }

        dialog.setContentView(dialogBinding.root)
        dialog.show()
    }

}