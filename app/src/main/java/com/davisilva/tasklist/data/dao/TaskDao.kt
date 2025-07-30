package com.davisilva.tasklist.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.davisilva.tasklist.data.entities.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: TaskEntity)

    @Update
    suspend fun update(task: TaskEntity)

    @Delete
    suspend fun delete(task: TaskEntity)

    @Query("SELECT * FROM tasks")
    fun getAll(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks where id =:id")
    suspend fun getById(id: Long): TaskEntity?
}
