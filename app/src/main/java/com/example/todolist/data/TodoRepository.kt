package com.example.todolist.data
import kotlinx.coroutines.flow.Flow

interface TodoRepository {

    suspend fun insertTodo(todo: TodoModel)

    suspend fun deleteTodo(todo: TodoModel)

    suspend fun getTodoById(id:Int):TodoModel?

    fun getTodos(): Flow<List<TodoModel>>
}