package com.mustafaademkayaaslan.planfollowchallenger.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.mustafaademkayaaslan.planfollowchallenger.model.Task
import com.mustafaademkayaaslan.planfollowchallenger.service.TaskDatabase
import kotlinx.coroutines.launch
import java.time.DayOfWeek

class SaturdayViewModel (application: Application) : BaseViewModel(application) {

    val tasks = MutableLiveData<List<Task>>()

    fun getSaturdayTasks () {
        launch {
            val dao = TaskDatabase(getApplication()).TaskDao()
            val tasklist = dao.getTasksByDay(DayOfWeek.SATURDAY.toString())
            tasks.value = tasklist
        }
    }

    fun deleteItem(id:Int) {
        launch {
            val dao = TaskDatabase(getApplication()).TaskDao()
            dao.deleteTask(id)
        }
    }

}