package com.mustafaademkayaaslan.planfollowchallenger.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.mustafaademkayaaslan.planfollowchallenger.model.Task
import com.mustafaademkayaaslan.planfollowchallenger.service.TaskDatabase
import kotlinx.coroutines.launch

class StatisticsViewModel(application: Application) : BaseViewModel(application) {

    val tasks = MutableLiveData<List<Task>>()

    fun getStatisticsTasks() {

        launch {
            val dao = TaskDatabase(getApplication()).TaskDao()
            val tasklist = dao.getStatisticsTasks()
            tasks.value = tasklist
            println("statistics qq : "+ tasklist)
        }

    }

}