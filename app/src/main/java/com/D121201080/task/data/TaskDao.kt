package com.D121201080.task.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.D121201080.task.model.Task

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun  addTask (task: Task)

    @Query("SELECT * FROM task_table WHERE status='Belum Selesai' ORDER BY id ASC")
    fun reallAllDataHome(): LiveData<List<Task>>

    @Query("SELECT * FROM task_table WHERE status='Selesai' ORDER BY id ASC")
    fun reallAllDataHistory(): LiveData<List<Task>>

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)


    @Query("DELETE FROM task_table")
    suspend fun deleteAllTasks()

    @Query("SELECT * FROM task_table ORDER BY id ASC")
    fun reallAllData(): LiveData<List<Task>>


}