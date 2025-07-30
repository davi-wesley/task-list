package com.davisilva.tasklist.ui.feature.tasklist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davisilva.tasklist.data.repositories.ITaskRepository
import com.davisilva.tasklist.navigation.TaskDetailsRoute
import com.davisilva.tasklist.ui.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TaskListViewModel(
    private val taskRepository: ITaskRepository
) : ViewModel() {


    val tasks = taskRepository.getAll().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: TaskListEvent) {
        when (event) {
            is TaskListEvent.DeleteTaskEvent -> {
                deleteTask(event.id)
            }

            is TaskListEvent.EditTaskEvent -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.Navigate(TaskDetailsRoute(event.id)))
                }
            }

            is TaskListEvent.ChangeTaskStatusEvent -> {
                changeTaskStatus(event.id, event.isCompleted)
            }
        }
    }

    fun deleteTask(id: Long) {
        viewModelScope.launch {
            taskRepository.delete(id)
            _uiEvent.send(UiEvent.ShowSnackBar(message = "Task successfully deleted"))
        }
    }

    fun changeTaskStatus(id: Long, isCompleted: Boolean) {
        viewModelScope.launch {
            taskRepository.updateIsDone(id, isCompleted)
        }
    }
}