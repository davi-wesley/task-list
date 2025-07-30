package com.davisilva.tasklist.ui.feature.tasklist

import com.davisilva.tasklist.domain.entities.TaskModel
import com.davisilva.tasklist.ui.feature.taskdetails.TaskDetailsEvents

interface TaskListEvent {

    data class EditTaskEvent(val id: Long?) : TaskListEvent

    data class DeleteTaskEvent(val id: Long) : TaskListEvent

    data class ChangeTaskStatusEvent(val id: Long, val isCompleted: Boolean) : TaskListEvent


}