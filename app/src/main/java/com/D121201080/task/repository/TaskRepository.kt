package com.D121201080.task.repository

import androidx.lifecycle.LiveData
import com.D121201080.task.data.TaskDao
import com.D121201080.task.model.Task

class TaskRepository(private val taskDao: TaskDao) {

    val readAllData: LiveData<List<Task>> = taskDao.reallAllData()
    val readAllDataHome: LiveData<List<Task>> = taskDao.reallAllDataHome()
    val readAllDataHistory: LiveData<List<Task>> = taskDao.reallAllDataHistory()
    suspend fun addTask(task: Task){
        taskDao.addTask(task)
    }

    suspend fun updateTask(task: Task){
        taskDao.updateTask(task)
    }

    suspend fun deleteTask(task: Task){
        taskDao.deleteTask(task)
    }


    suspend fun deleteAllTasks(){
        taskDao.deleteAllTasks()
    }
}