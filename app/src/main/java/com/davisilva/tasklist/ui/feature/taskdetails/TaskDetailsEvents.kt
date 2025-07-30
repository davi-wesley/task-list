package com.davisilva.tasklist.ui.feature.taskdetails

sealed interface TaskDetailsEvents {

    data class TaskDetailsTitleChanged(val title: String) : TaskDetailsEvents
    data class TaskDetailsDescriptionChanged(val description: String) : TaskDetailsEvents
    data object TaskDetailsSaveClicked : TaskDetailsEvents

}