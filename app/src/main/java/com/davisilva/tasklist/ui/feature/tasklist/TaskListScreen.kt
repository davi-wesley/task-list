package com.davisilva.tasklist.ui.feature.tasklist

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.davisilva.tasklist.data.TaskDatabaseProvider
import com.davisilva.tasklist.data.repositories.TaskRepositoryImpl
import com.davisilva.tasklist.domain.entities.TaskModel
import com.davisilva.tasklist.domain.entities.taskModelMockNotSelected
import com.davisilva.tasklist.domain.entities.taskModelMockSelected
import com.davisilva.tasklist.navigation.TaskDetailsRoute
import com.davisilva.tasklist.ui.UiEvent
import com.davisilva.tasklist.ui.feature.tasklist.components.TaskItemComponent
import com.davisilva.tasklist.ui.feature.tasklist.data.TodoItemActions
import com.davisilva.tasklist.ui.theme.TaskListTheme

@Composable
fun TaskListScreen(navigateToTaskDetails: (taskId: Long?) -> Unit) {

    val context = LocalContext.current.applicationContext
    val database = TaskDatabaseProvider.provide(context)
    val dao = database.taskDao
    val repository = TaskRepositoryImpl(dao)

    val viewModel = viewModel<TaskListViewModel> {
        TaskListViewModel(
            taskRepository = repository
        )
    }

    val snackBarHostState = remember { SnackbarHostState() }

    val tasks by viewModel.tasks.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                is UiEvent.Navigate<*> -> {
                    when (uiEvent.route) {
                        is TaskDetailsRoute -> navigateToTaskDetails(uiEvent.route.id)
                    }
                }

                is UiEvent.NavigateBack -> {

                }

                is UiEvent.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(uiEvent.message)
                }

            }
        }
    }

    TaskListContent(tasks = tasks, onEvent = viewModel::onEvent, snackBarHostState)
}

@Composable
fun TaskListContent(
    tasks: List<TaskModel>,
    onEvent: (TaskListEvent) -> Unit,
    snackBarHostState: SnackbarHostState
) {
    Scaffold(snackbarHost = {
        SnackbarHost(hostState = snackBarHostState)
    }, floatingActionButton = {
        FloatingActionButton(onClick = { onEvent(TaskListEvent.EditTaskEvent(null)) }) {
            Icon(Icons.Default.Add, contentDescription = "Add")
        }
    }) { paddingValues ->
        LazyColumn(
            modifier = Modifier.consumeWindowInsets(paddingValues),
            contentPadding = PaddingValues(16.dp),
        ) {
            itemsIndexed(tasks) { index, item ->
                TaskItemComponent(
                    data = item,
                    actions = TodoItemActions(
                        onCheckedChange = {
                            onEvent(TaskListEvent.ChangeTaskStatusEvent(item.id, it))
                        }, onDeleteClick = {
                            onEvent(TaskListEvent.DeleteTaskEvent(item.id))

                        }, onItemClicked = {
                            onEvent(TaskListEvent.EditTaskEvent(item.id))
                        }
                    ),
                )

                if (index < tasks.lastIndex) {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Preview
@Composable
private fun TodoListScreenPreview() {
    TaskListTheme {
        TaskListContent(
            listOf(taskModelMockSelected, taskModelMockNotSelected),
            {},
            SnackbarHostState()
        )
    }
}
