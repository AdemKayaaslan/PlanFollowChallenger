package com.mustafaademkayaaslan.planfollowchallenger.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.mustafaademkayaaslan.planfollowchallenger.model.Task

@Dao
interface TaskDao {

    @Insert
    suspend fun insertAll(vararg tasks:Task):List<Long>

    @Query("SELECT * FROM task")
    suspend fun getAllTasks():List<Task>

    @Query("SELECT * FROM task WHERE week = 0")
    suspend fun getLiveTasks():List<Task>

    @Query("SELECT * FROM task WHERE uuid = :taskId")
    suspend fun getTask(taskId:Int):Task

    @Query("SELECT * FROM task WHERE week > 0")
    suspend fun getStatisticsTasks():List<Task>

    @Query("DELETE FROM task")
    suspend fun deleteAllTasks()

    @Query("SELECT * FROM task WHERE day = :taskName AND week = 0")
    suspend fun getTasksByDay(taskName:String):List<Task>

    @Query ("DELETE FROM task WHERE uuid = :taskId")
    suspend fun deleteTask(taskId:Int)

    @Update
    suspend fun updateTask(task :Task)

    @Update
    suspend fun updateMultipleTasks(taskList: List<Task>)




}