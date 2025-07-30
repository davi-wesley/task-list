package com.davisilva.tasklist.ui.feature.tasklist.data

data class TaskItemUiData(
    val title: String,
    val description: String,
    val isSelected: Boolean
)

//Mocks
var taskSelectedMock = TaskItemUiData(
    title = "Todo selected",
    description = "Todo selected description",
    isSelected = true
)

var taskNotSelectedMock = TaskItemUiData(
    title = "Todo not selected",
    description = "Todo not selected description",
    isSelected = false
)


