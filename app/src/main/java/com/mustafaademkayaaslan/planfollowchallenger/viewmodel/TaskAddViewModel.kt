package com.mustafaademkayaaslan.planfollowchallenger.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.mustafaademkayaaslan.planfollowchallenger.model.Task
import com.mustafaademkayaaslan.planfollowchallenger.service.TaskDatabase
import kotlinx.coroutines.launch

class TaskAddViewModel(application: Application):BaseViewModel(application) {


    val tasks = MutableLiveData<List<Task>>()

    fun storeInSQLite(list:List<Task>) {
        launch {

            val dao = TaskDatabase(getApplication()).TaskDao()
            val listLong = dao.insertAll(*list.toTypedArray())
            var i = 0
            while (i<list.size) {
                list[i].uuid=listLong[i].toInt()
                i=i+1
            }

            //tasks.value = list
            println("Task Add viewmodel"+list)

        }

    }


    fun getTasks() {
        launch {
            val dao = TaskDatabase(getApplication()).TaskDao()
            val list = dao.getLiveTasks()
            tasks.value = list
        }


    }

}