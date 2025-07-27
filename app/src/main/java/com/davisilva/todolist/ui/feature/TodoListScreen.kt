package com.davisilva.todolist.ui.feature

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.davisilva.todolist.ui.feature.components.TodoItemComponent
import com.davisilva.todolist.ui.feature.data.TodoItemActions
import com.davisilva.todolist.ui.feature.data.TodoItemUiData
import com.davisilva.todolist.ui.feature.data.todoNotSelectedMock
import com.davisilva.todolist.ui.feature.data.todoSelectedMock
import com.davisilva.todolist.ui.theme.TodoListTheme

@Composable fun TodoListScreen(modifier: Modifier = Modifier) {}

@Composable
fun TodoListContent(todoList: List<TodoItemUiData>) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {}) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.consumeWindowInsets(paddingValues),
            contentPadding = PaddingValues(16.dp),

        ) {
            itemsIndexed(todoList) { index, item ->
                TodoItemComponent(
                    data = item,
                    actions =
                        TodoItemActions(
                            onCheckedChange = {},
                            onDeleteClick = {},
                            onItemClicked = {},
                        ),
                )

                if(index < todoList.lastIndex) {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Preview
@Composable
private fun TodoListScreenPreview() {
    TodoListTheme { TodoListContent(listOf(todoSelectedMock, todoNotSelectedMock)) }
}
