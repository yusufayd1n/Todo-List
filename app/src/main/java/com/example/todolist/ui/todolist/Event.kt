package com.example.todolist.ui.todolist

import com.example.todolist.data.TodoModel

sealed class Event{
    data class OnDeleteTodoClick(val todo:TodoModel):Event()
    data class OnDoneChange(val todo:TodoModel,val isDone:Boolean):Event()
    object OnUndoDeleteClick:Event()
    data class OnTodoClick(val todo: TodoModel):Event()
    object OnAddTodoClick:Event()
}
