package com.example.todolist.data

import androidx.room.*
import androidx.room.Dao
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: TodoModel)

    @Delete
    suspend fun deleteTodo(todo: TodoModel)

    @Query("SELECT * FROM todoModel WHERE id=:id")
    suspend fun getTodoById(id:Int):TodoModel?

    @Query("SELECT * FROM todoModel")
    fun getTodos(): Flow<List<TodoModel>>
}