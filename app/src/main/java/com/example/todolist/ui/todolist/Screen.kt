package com.example.todolist.ui.todolist

import android.widget.ListView
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todolist.util.UiEvent
import kotlinx.coroutines.flow.collect

@Composable
fun TodoListScreen(
    onNavigate: (UiEvent.Navigate)->Unit,
    viewModel: ListViewModel= hiltViewModel()
){
    val todos=viewModel.todos.collectAsState(initial = emptyList())
    val scaffoldState= rememberScaffoldState()
    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect {event->
            when(event){
                is UiEvent.ShowSnackBar->{
                    val result=scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                    if(result==SnackbarResult.ActionPerformed){
                        viewModel.onEvent(Event.OnUndoDeleteClick)
                    }
                }
                is UiEvent.Navigate->onNavigate(event)
                else->Unit
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(Event.OnAddTodoClick)
            }) {
                Icon(
                   imageVector =  Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ){
            items(todos.value){todo->
                TodoItem(
                    todo = todo,
                    onEvent =viewModel::onEvent,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.onEvent(Event.OnTodoClick(todo))
                        }
                        .padding(15.dp)
                    )

            }
        }
    }
}