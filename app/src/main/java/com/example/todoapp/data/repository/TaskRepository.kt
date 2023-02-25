package com.example.todoapp.data.repository

import android.util.Log
import com.example.todoapp.data.db.dao.TaskDao
import com.example.todoapp.data.db.toTask
import com.example.todoapp.data.db.toTaskEntity
import com.example.todoapp.data.model.Task

class TaskRepository(private val taskDao: TaskDao) {
    suspend fun createTask(task: Task) {
        taskDao.add(task.toTaskEntity())
    }

    suspend fun getAllTask(): ArrayList<Task> {
        val tasksEntity = taskDao.getAll()
        val tasks: ArrayList<Task> = ArrayList()

        tasksEntity.forEach {
            tasks.add(it.toTask())
        }

        return tasks
    }

    suspend fun deleteTask(task: Task) {
        taskDao.delete(task.toTaskEntity())
    }
}