package com.davisilva.tasklist.ui.feature.tasklist.data

data class TodoItemActions(
    val onCheckedChange: (Boolean) -> Unit,
    val onDeleteClick: () -> Unit,
    val onItemClicked: () -> Unit
)

//Mock
val todoItemActionsMock = TodoItemActions(
    onCheckedChange = {},
    onDeleteClick = {},
    onItemClicked = {}
)
