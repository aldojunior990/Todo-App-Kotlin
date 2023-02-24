package com.example.todoapp.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todoapp.data.model.Task

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey val id: Int = 0,
    val title: String,
    val dateTime: String,
)

fun Task.toTaskEntity(): TaskEntity {
    return with(this) {
        TaskEntity(
            id = this.id.toInt(),
            title = this.title,
            dateTime = this.dateTime
        )
    }
}


fun TaskEntity.toTask(): Task {
    return Task(
        id = this.id.toString(),
        title = this.title,
        dateTime = this.dateTime
    )
}


