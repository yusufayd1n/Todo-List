package com.example.todolist.data

import kotlinx.coroutines.flow.Flow

class TodoRepositoryImplementation(
    private val dao: Dao
) : TodoRepository {

    override suspend fun insertTodo(todo: TodoModel) {
        dao.insertTodo(todo)
    }

    override suspend fun deleteTodo(todo: TodoModel) {
        dao.deleteTodo(todo)
    }

    override suspend fun getTodoById(id: Int): TodoModel? {
        return dao.getTodoById(id)
    }

    override fun getTodos(): Flow<List<TodoModel>> {
        return dao.getTodos()
    }
}