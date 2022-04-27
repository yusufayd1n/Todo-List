package com.example.todolist.ui.todolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.data.TodoModel
import com.example.todolist.data.TodoRepository
import com.example.todolist.util.Routes
import com.example.todolist.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository:TodoRepository
): ViewModel() {

    val todos=repository.getTodos()

    private val _uiEvent= Channel<UiEvent>()
    val uiEvent=_uiEvent.receiveAsFlow()

    private var deletedTodo:TodoModel?=null
    fun onEvent(event: Event){
        when(event){
            is Event.OnTodoClick->{
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_TODO+ "?todoId=${event.todo.id}"))
            }
            is Event.OnAddTodoClick->{
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_TODO))
            }
            is Event.OnUndoDeleteClick->{
                deletedTodo?.let {todo->
                    viewModelScope.launch {
                        repository.insertTodo(todo)
                    }
                }

            }
            is Event.OnDeleteTodoClick->{
                viewModelScope.launch {
                    deletedTodo=event.todo
                    repository.deleteTodo(event.todo)
                    sendUiEvent(UiEvent.ShowSnackBar(
                        message = "Todo Deleted",
                        action = "Undo"
                    ))
                }
            }
            is Event.OnDoneChange->{
                viewModelScope.launch {
                    repository.insertTodo(
                        event.todo.copy(
                            isDone = event.isDone
                        )
                    )
                }
            }
        }
    }
    private fun sendUiEvent(event:UiEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}