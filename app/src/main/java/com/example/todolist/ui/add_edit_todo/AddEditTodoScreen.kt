package com.example.todolist.ui.add_edit_todo

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todolist.util.UiEvent
import kotlinx.coroutines.flow.collect
import java.lang.reflect.Modifier

@Composable
fun AddEditTodoScreen(
    onPopBackStack:()->Unit,
    viewModel:AddEditTodoViewModel= hiltViewModel()
) {
    val scaffoldState= rememberScaffoldState()
    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect {event->
            when(event){
                is UiEvent.PopBackStack->onPopBackStack()
                is UiEvent.ShowSnackBar->{
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                }
                else->Unit
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = androidx.compose.ui.Modifier
            .fillMaxSize()
            .padding(15.dp),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(AddEditTodoEvent.OnSaveTodoClick)
            }) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Save"
                )
            }
        }
    ) {
        Column(
            modifier = androidx.compose.ui.Modifier.fillMaxSize()
        ) {
            TextField(value = viewModel.title, onValueChange = {
                viewModel.onEvent(AddEditTodoEvent.OnTittleChange(it))
            },
            placeholder = {
                Text(text = "Tittle")
            },
                modifier = androidx.compose.ui.Modifier.fillMaxWidth()
                )
            Spacer(modifier = androidx.compose.ui.Modifier.height(8.dp))

            TextField(value = viewModel.description, onValueChange = {
                viewModel.onEvent(AddEditTodoEvent.OnDescriptionChange(it))
            },
                placeholder = {
                    Text(text = "Description")
                },
                modifier = androidx.compose.ui.Modifier.fillMaxWidth(),
                singleLine = false,
                maxLines = 5
            )
        }
    }
}