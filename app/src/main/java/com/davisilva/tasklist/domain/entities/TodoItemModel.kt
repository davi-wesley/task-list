package com.davisilva.tasklist.domain.entities

data class TaskModel(
    val id: Long,
    val title: String,
    val description: String,
    val isSelected: Boolean
)

//Mocks
val taskModelMockSelected = TaskModel(
    id = 1,
    title = "Todo selected",
    description = "Todo selected description",
    isSelected = true
)

val taskModelMockNotSelected = TaskModel(
    id = 2,
    title = "Todo not selected",
    description = "Todo not selected description",
    isSelected = false
)