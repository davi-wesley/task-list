package com.davisilva.todolist.domain.entities

data class TodoModel(
    val title: String,
    val description: String,
    val isSelected: Boolean
)

//Mocks
val todoSelected = TodoModel(
    title = "Todo selected",
    description = "Todo selected description",
    isSelected = true
)

val todoNotSelected = TodoModel(
    title = "Todo not selected",
    description = "Todo not selected description",
    isSelected = false
)