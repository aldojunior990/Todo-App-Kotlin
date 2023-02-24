package com.example.todoapp.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.model.Task
import com.example.todoapp.data.repository.TaskRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: TaskRepository) : ViewModel() {

    private var _taskList = MutableLiveData<ArrayList<Task>>()

    val taskList: MutableLiveData<ArrayList<Task>> get() = _taskList

    fun createTask(task: Task) {
        viewModelScope.launch {
            repository.createTask(task)
            _taskList.postValue(repository.getAllTask())
        }
    }

    fun getALlTasks() {
        viewModelScope.launch {
            _taskList.postValue(repository.getAllTask())
        }

    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.deleteTask(task)
            _taskList.postValue(repository.getAllTask())
        }

    }

}