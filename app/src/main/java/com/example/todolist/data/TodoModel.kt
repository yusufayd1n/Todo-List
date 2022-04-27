package com.example.todolist.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TodoModel(
    val title:String,
    val description:String?,
    val isDone:Boolean,
    @PrimaryKey val id: Int?=null
)
