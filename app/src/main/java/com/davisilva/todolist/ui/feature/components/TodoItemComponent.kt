package com.davisilva.todolist.ui.feature.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.davisilva.todolist.ui.feature.data.TodoItemActions
import com.davisilva.todolist.ui.feature.data.TodoItemUiData
import com.davisilva.todolist.ui.feature.data.todoItemActionsMock
import com.davisilva.todolist.ui.feature.data.todoNotSelectedMock
import com.davisilva.todolist.ui.feature.data.todoSelectedMock
import com.davisilva.todolist.ui.theme.TodoListTheme

@Composable
fun TodoItemComponent(
    modifier: Modifier = Modifier,
    data: TodoItemUiData,
    actions: TodoItemActions,
) {
    with(data) {
        Surface(
            onClick = actions.onItemClicked,
            modifier = modifier,
            shape = MaterialTheme.shapes.medium,
            shadowElevation = 2.dp,
            border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outline),
        ) {
            Row(
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Checkbox(checked = isSelected, onCheckedChange = actions.onCheckedChange)
                Spacer(modifier = Modifier.width(8.dp))
                Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.Start) {
                    Text(text = title, style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = description, style = MaterialTheme.typography.titleMedium)
                }
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(onClick = actions.onDeleteClick) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                }
            }
        }
    }
}

@Preview
@Composable
private fun TodoItemComponentPreview() {
    TodoListTheme { TodoItemComponent(data = todoSelectedMock, actions = todoItemActionsMock) }
}

@Preview
@Composable
private fun TodoItemNotSelectedComponentPreview() {
    TodoListTheme { TodoItemComponent(data = todoNotSelectedMock, actions = todoItemActionsMock) }
}
