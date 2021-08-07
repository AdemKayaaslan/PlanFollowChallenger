package com.mustafaademkayaaslan.planfollowchallenger.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.mustafaademkayaaslan.planfollowchallenger.model.Task
import com.mustafaademkayaaslan.planfollowchallenger.service.TaskDatabase
import kotlinx.coroutines.launch
import java.time.DayOfWeek

class TuesdayViewModel(application: Application):BaseViewModel(application) {

    val tasks = MutableLiveData<List<Task>>()

    fun getTuesdayTasks () {
        launch {
            val dao = TaskDatabase(getApplication()).TaskDao()
            val tasklist = dao.getTasksByDay(DayOfWeek.TUESDAY.toString())
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