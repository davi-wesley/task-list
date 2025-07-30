package com.davisilva.tasklist.ui.feature.taskdetails

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davisilva.tasklist.data.repositories.ITaskRepository
import com.davisilva.tasklist.ui.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class TaskDetailsViewModel(
    private val taskId: Long? = null,
    private val taskRepository: ITaskRepository
) : ViewModel() {

    var title by mutableStateOf("")
        private set

    var description by mutableStateOf<String?>(null)
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        taskId?.let {
            viewModelScope.launch {
                taskRepository.getById(taskId)?.let { task ->
                    title = task.title
                    description = task.description
                }
            }
        }
    }

    fun onEvent(event: TaskDetailsEvents) {
        when (event) {
            is TaskDetailsEvents.TaskDetailsTitleChanged -> title = event.title
            is TaskDetailsEvents.TaskDetailsDescriptionChanged -> description = event.description
            else -> saveTask()

        }
    }

    private fun saveTask() {
        viewModelScope.launch {
            if (title.isBlank()) {
                _uiEvent.send(UiEvent.ShowSnackBar("The title can't be empty"))
                return@launch
            }

            taskRepository.insert(title, description ?: "", taskId)
            _uiEvent.send(UiEvent.NavigateBack)
        }
    }
}