package com.example.todoapp.data.db.dao

import android.app.TaskInfo
import androidx.room.*
import com.example.todoapp.data.db.TaskEntity

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks")
    suspend fun getAll(): List<TaskEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(task: TaskEntity)

    @Delete
    suspend fun delete(task: TaskEntity)

}