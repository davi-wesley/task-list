package com.davisilva.tasklist.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.davisilva.tasklist.ui.feature.taskdetails.TaskDetailsScreen
import com.davisilva.tasklist.ui.feature.tasklist.TaskListScreen
import kotlinx.serialization.Serializable

@Serializable
object TaskListRoute

@Serializable
data class TaskDetailsRoute(val id: Long? = null)

@Composable
fun TaskNavHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = TaskListRoute) {
        composable<TaskListRoute> {
            TaskListScreen(navigateToTaskDetails = { id ->
                navController.navigate(
                    TaskDetailsRoute(
                        id
                    )
                )
            })
        }
        composable<TaskDetailsRoute> { backStack ->
            val taskId = backStack.toRoute<TaskDetailsRoute>()
            TaskDetailsScreen(
                taskId = taskId.id,
                navigateBack = { navController.popBackStack() },
            )
        }
    }
}
