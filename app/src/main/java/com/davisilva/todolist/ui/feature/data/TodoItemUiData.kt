package com.davisilva.todolist.ui.feature.data

data class TodoItemUiData(
    val title: String,
    val description: String,
    val isSelected: Boolean
)

//Mocks
var todoSelectedMock = TodoItemUiData(
    title = "Todo selected",
    description = "Todo selected description",
    isSelected = true
)

var todoNotSelectedMock = TodoItemUiData(
    title = "Todo not selected",
    description = "Todo not selected description",
    isSelected = false
)


