package com.davisilva.tasklist.data.repositories

import com.davisilva.tasklist.data.dao.TaskDao
import com.davisilva.tasklist.data.entities.TaskEntity
import com.davisilva.tasklist.domain.entities.TaskModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskRepositoryImpl(
    private val taskDao: TaskDao
) : ITaskRepository {
    override suspend fun insert(title: String, description: String, id: Long?) {
        val taskEntity = id?.let {
            taskDao.getById(it)?.copy(
                title = title,
                description = description,
            )
        } ?: TaskEntity(title = title, description = description, isDone = false)
        taskDao.insert(taskEntity)
    }

    override suspend fun updateIsDone(id: Long, isDone: Boolean) {
        val todo = taskDao.getById(id) ?: return
        taskDao.update(todo.copy(isDone = isDone))
    }

    override suspend fun delete(id: Long) {
        val existingTodo = taskDao.getById(id) ?: return
        taskDao.delete(existingTodo)
    }

    override fun getAll(): Flow<List<TaskModel>> {
        return taskDao.getAll().map {
            it.map { todoEntity -> todoEntity.toTodoModel() }
        }
    }

    override suspend fun getById(id: Long): TaskModel? {
        return taskDao.getById(id)?.toTodoModel()
    }
}

fun TaskEntity.toTodoModel(): TaskModel {
    return TaskModel(
        id = id,
        title = title,
        description = description,
        isSelected = isDone
    )
}

