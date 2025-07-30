package com.davisilva.tasklist.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.davisilva.tasklist.data.dao.TaskDao
import com.davisilva.tasklist.data.entities.TaskEntity

@Database(entities = [TaskEntity::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {

    abstract val taskDao: TaskDao
}

object TaskDatabaseProvider {

    @Volatile
    private var INSTANCE: TaskDatabase? = null

    fun provide(context: Context): TaskDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                TaskDatabase::class.java,
                "task_db"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}
