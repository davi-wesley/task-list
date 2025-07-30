package com.davisilva.tasklist.ui.feature.taskdetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.davisilva.tasklist.data.TaskDatabaseProvider
import com.davisilva.tasklist.data.repositories.TaskRepositoryImpl
import com.davisilva.tasklist.ui.UiEvent
import com.davisilva.tasklist.ui.theme.TaskListTheme

@Composable
fun TaskDetailsScreen(
    taskId: Long?,
    navigateBack: () -> Unit
) {

    val context = LocalContext.current.applicationContext
    val database = TaskDatabaseProvider.provide(context)
    val dao = database.taskDao
    val repository = TaskRepositoryImpl(dao)

    val viewModel = viewModel<TaskDetailsViewModel> {
        TaskDetailsViewModel(
            taskId = taskId,
            taskRepository = repository,
        )
    }

    val title = viewModel.title
    val description = viewModel.description

    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                is UiEvent.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(uiEvent.message)
                }

                is UiEvent.NavigateBack -> {
                    navigateBack()
                }

                is UiEvent.Navigate<*> -> {}
            }
        }
    }

    TaskDetailsContent(
        title = title,
        description = description,
        snackBarHostState = snackBarHostState,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun TaskDetailsContent(
    modifier: Modifier = Modifier,
    title: String,
    description: String?,
    snackBarHostState: SnackbarHostState,
    onEvent: (TaskDetailsEvents) -> Unit
) {
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onEvent(TaskDetailsEvents.TaskDetailsSaveClicked)
            }) {
                Icon(Icons.Default.Check, contentDescription = "save")
            }
        }) { padding ->
        Column(
            modifier = modifier
                .consumeWindowInsets(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                modifier = modifier.fillMaxWidth(),
                value = title,
                onValueChange = {
                    onEvent(TaskDetailsEvents.TaskDetailsTitleChanged(it))
                },
                label = { Text("Title") },
            )
            Spacer(Modifier.size(16.dp))
            OutlinedTextField(
                modifier = modifier.fillMaxWidth(),
                value = description ?: "",
                onValueChange = {
                    onEvent(
                        TaskDetailsEvents.TaskDetailsDescriptionChanged(it)
                    )
                },
                label = { Text("Description") },
            )
        }
    }
}

@Preview
@Composable
private fun TaskDetailsScreenPreview() {
    TaskListTheme {
        TaskDetailsContent(
            title = "Title",
            description = "Description",
            onEvent = {},
            snackBarHostState = SnackbarHostState()
        )
    }
}
