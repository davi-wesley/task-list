package com.davisilva.tasklist.data.repositories

import com.davisilva.tasklist.domain.entities.TaskModel
import kotlinx.coroutines.flow.Flow

interface ITaskRepository {

    suspend fun insert(title: String, description: String, id: Long?)

    suspend fun updateIsDone(id: Long, isDone: Boolean)

    suspend fun delete(id: Long)

    fun getAll(): Flow<List<TaskModel>>

    suspend fun getById(id: Long): TaskModel?


}