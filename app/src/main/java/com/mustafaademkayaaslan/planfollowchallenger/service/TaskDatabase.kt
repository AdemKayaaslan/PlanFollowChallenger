package com.mustafaademkayaaslan.planfollowchallenger.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mustafaademkayaaslan.planfollowchallenger.model.Task

@Database(entities = arrayOf(Task::class),version = 1)
abstract class TaskDatabase: RoomDatabase() {

    abstract fun TaskDao():TaskDao


    companion object {

        @Volatile private var instance : TaskDatabase?=null

        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: makeDatabase(context).also {
                instance = it
            }
        }

        private fun makeDatabase(context:Context) = Room.databaseBuilder(
            context.applicationContext,TaskDatabase::class.java,"taskdatabase"
        ).build()

    }

}