package com.mustafaademkayaaslan.planfollowchallenger.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.mustafaademkayaaslan.planfollowchallenger.model.Task
import com.mustafaademkayaaslan.planfollowchallenger.service.TaskDatabase
import kotlinx.coroutines.launch

class DayViewModel(application: Application) :BaseViewModel(application) {

    val tasks = MutableLiveData<List<Task>>()
    val allTasks = MutableLiveData<List<Task>>()

    fun getTasksByDay (day:String) {

        launch {
            val dao = TaskDatabase(getApplication()).TaskDao()
            val tasklist = dao.getTasksByDay(day)
            tasks.value = tasklist

        }

    }

    fun updateTask(task: Task) {
        launch {
            val dao = TaskDatabase(getApplication()).TaskDao()
            dao.updateTask(task)
        }
    }

    fun getAllTasks() {
        launch {
            val dao = TaskDatabase(getApplication()).TaskDao()
            val tasklist = dao.getAllTasks()
            allTasks.value = tasklist

        }
    }

}