package com.mustafaademkayaaslan.planfollowchallenger.service

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import java.time.DayOfWeek
import java.util.*

class WeeklyRefresher(context: Context, workerParams: WorkerParameters): CoroutineWorker(context,
    workerParams
) {
    override suspend fun doWork(): Result {

        val liveTasks = TaskDatabase.invoke(this.applicationContext).TaskDao().getLiveTasks()
        val backupTasks = TaskDatabase.invoke(this.applicationContext).TaskDao().getLiveTasks()
        val alltasks = TaskDatabase.invoke(this.applicationContext).TaskDao().getAllTasks()


        var weekInt = 1
        for (task in alltasks) {
            if (task.week >= weekInt) {
                weekInt = task.week + 1
                println("weekInt : "+weekInt)
            }
        }


        for (task in backupTasks) {
            task.uuid = 0
            task.week = weekInt
            val myCalendar = Calendar.getInstance()
            var date = ""


            if (task.day == DayOfWeek.SUNDAY.toString()) {
                myCalendar.add(Calendar.DAY_OF_YEAR,-1)
                if(myCalendar.get(Calendar.MONTH)<10 &&  myCalendar.get(Calendar.DAY_OF_MONTH) >= 10) {
                    date = ""+ myCalendar.get(Calendar.YEAR) + "-"+"0"+myCalendar.get(Calendar.MONTH).plus(1)+ "-" + myCalendar.get(Calendar.DAY_OF_MONTH)
                } else if (myCalendar.get(Calendar.MONTH) >= 10 &&  myCalendar.get(Calendar.DAY_OF_MONTH) < 10) {
                    date = ""+ myCalendar.get(Calendar.YEAR) + "-"+myCalendar.get(Calendar.MONTH).plus(1)+ "-" + "0"+myCalendar.get(Calendar.DAY_OF_MONTH)
                } else if (myCalendar.get(Calendar.MONTH) < 10 &&  myCalendar.get(Calendar.DAY_OF_MONTH) < 10) {
                    date = ""+ myCalendar.get(Calendar.YEAR) + "-"+"0"+myCalendar.get(Calendar.MONTH).plus(1)+ "-" +"0"+ myCalendar.get(Calendar.DAY_OF_MONTH)
                } else {
                    date = ""+ myCalendar.get(Calendar.YEAR) + "-"+myCalendar.get(Calendar.MONTH).plus(1)+ "-" + myCalendar.get(Calendar.DAY_OF_MONTH)
                }
                task.date = date
            } else if (task.day == DayOfWeek.SATURDAY.toString()) {
                myCalendar.add(Calendar.DAY_OF_YEAR,-2)
                if(myCalendar.get(Calendar.MONTH)<10 &&  myCalendar.get(Calendar.DAY_OF_MONTH) >= 10) {
                    date = ""+ myCalendar.get(Calendar.YEAR) + "-"+"0"+myCalendar.get(Calendar.MONTH).plus(1)+ "-" + myCalendar.get(Calendar.DAY_OF_MONTH)
                } else if (myCalendar.get(Calendar.MONTH) >= 10 &&  myCalendar.get(Calendar.DAY_OF_MONTH) < 10) {
                    date = ""+ myCalendar.get(Calendar.YEAR) + "-"+myCalendar.get(Calendar.MONTH).plus(1)+ "-" + "0"+myCalendar.get(Calendar.DAY_OF_MONTH)
                } else if (myCalendar.get(Calendar.MONTH) < 10 &&  myCalendar.get(Calendar.DAY_OF_MONTH) < 10) {
                    date = ""+ myCalendar.get(Calendar.YEAR) + "-"+"0"+myCalendar.get(Calendar.MONTH).plus(1)+ "-" +"0"+ myCalendar.get(Calendar.DAY_OF_MONTH)
                } else {
                    date = ""+ myCalendar.get(Calendar.YEAR) + "-"+myCalendar.get(Calendar.MONTH).plus(1)+ "-" + myCalendar.get(Calendar.DAY_OF_MONTH)
                }
                task.date = date
            } else if (task.day == DayOfWeek.FRIDAY.toString()) {
                myCalendar.add(Calendar.DAY_OF_YEAR,-3)
                if(myCalendar.get(Calendar.MONTH)<10 &&  myCalendar.get(Calendar.DAY_OF_MONTH) >= 10) {
                    date = ""+ myCalendar.get(Calendar.YEAR) + "-"+"0"+myCalendar.get(Calendar.MONTH).plus(1)+ "-" + myCalendar.get(Calendar.DAY_OF_MONTH)
                } else if (myCalendar.get(Calendar.MONTH) >= 10 &&  myCalendar.get(Calendar.DAY_OF_MONTH) < 10) {
                    date = ""+ myCalendar.get(Calendar.YEAR) + "-"+myCalendar.get(Calendar.MONTH).plus(1)+ "-" + "0"+myCalendar.get(Calendar.DAY_OF_MONTH)
                } else if (myCalendar.get(Calendar.MONTH) < 10 &&  myCalendar.get(Calendar.DAY_OF_MONTH) < 10) {
                    date = ""+ myCalendar.get(Calendar.YEAR) + "-"+"0"+myCalendar.get(Calendar.MONTH).plus(1)+ "-" +"0"+ myCalendar.get(Calendar.DAY_OF_MONTH)
                } else {
                    date = ""+ myCalendar.get(Calendar.YEAR) + "-"+myCalendar.get(Calendar.MONTH).plus(1)+ "-" + myCalendar.get(Calendar.DAY_OF_MONTH)
                }
                task.date = date
            } else if (task.day == DayOfWeek.THURSDAY.toString()) {
                myCalendar.add(Calendar.DAY_OF_YEAR,-4)
                if(myCalendar.get(Calendar.MONTH)<10 &&  myCalendar.get(Calendar.DAY_OF_MONTH) >= 10) {
                    date = ""+ myCalendar.get(Calendar.YEAR) + "-"+"0"+myCalendar.get(Calendar.MONTH).plus(1)+ "-" + myCalendar.get(Calendar.DAY_OF_MONTH)
                } else if (myCalendar.get(Calendar.MONTH) >= 10 &&  myCalendar.get(Calendar.DAY_OF_MONTH) < 10) {
                    date = ""+ myCalendar.get(Calendar.YEAR) + "-"+myCalendar.get(Calendar.MONTH).plus(1)+ "-" + "0"+myCalendar.get(Calendar.DAY_OF_MONTH)
                } else if (myCalendar.get(Calendar.MONTH) < 10 &&  myCalendar.get(Calendar.DAY_OF_MONTH) < 10) {
                    date = ""+ myCalendar.get(Calendar.YEAR) + "-"+"0"+myCalendar.get(Calendar.MONTH).plus(1)+ "-" +"0"+ myCalendar.get(Calendar.DAY_OF_MONTH)
                } else {
                    date = ""+ myCalendar.get(Calendar.YEAR) + "-"+myCalendar.get(Calendar.MONTH).plus(1)+ "-" + myCalendar.get(Calendar.DAY_OF_MONTH)
                }
                task.date = date
            } else if (task.day == DayOfWeek.WEDNESDAY.toString()) {
                myCalendar.add(Calendar.DAY_OF_YEAR,-5)
                if(myCalendar.get(Calendar.MONTH)<10 &&  myCalendar.get(Calendar.DAY_OF_MONTH) >= 10) {
                    date = ""+ myCalendar.get(Calendar.YEAR) + "-"+"0"+myCalendar.get(Calendar.MONTH).plus(1)+ "-" + myCalendar.get(Calendar.DAY_OF_MONTH)
                } else if (myCalendar.get(Calendar.MONTH) >= 10 &&  myCalendar.get(Calendar.DAY_OF_MONTH) < 10) {
                    date = ""+ myCalendar.get(Calendar.YEAR) + "-"+myCalendar.get(Calendar.MONTH).plus(1)+ "-" + "0"+myCalendar.get(Calendar.DAY_OF_MONTH)
                } else if (myCalendar.get(Calendar.MONTH) < 10 &&  myCalendar.get(Calendar.DAY_OF_MONTH) < 10) {
                    date = ""+ myCalendar.get(Calendar.YEAR) + "-"+"0"+myCalendar.get(Calendar.MONTH).plus(1)+ "-" +"0"+ myCalendar.get(Calendar.DAY_OF_MONTH)
                } else {
                    date = ""+ myCalendar.get(Calendar.YEAR) + "-"+myCalendar.get(Calendar.MONTH).plus(1)+ "-" + myCalendar.get(Calendar.DAY_OF_MONTH)
                }
                task.date = date
            } else if (task.day == DayOfWeek.TUESDAY.toString()) {
                myCalendar.add(Calendar.DAY_OF_YEAR,-6)
                if(myCalendar.get(Calendar.MONTH)<10 &&  myCalendar.get(Calendar.DAY_OF_MONTH) >= 10) {
                    date = ""+ myCalendar.get(Calendar.YEAR) + "-"+"0"+myCalendar.get(Calendar.MONTH).plus(1)+ "-" + myCalendar.get(Calendar.DAY_OF_MONTH)
                } else if (myCalendar.get(Calendar.MONTH) >= 10 &&  myCalendar.get(Calendar.DAY_OF_MONTH) < 10) {
                    date = ""+ myCalendar.get(Calendar.YEAR) + "-"+myCalendar.get(Calendar.MONTH).plus(1)+ "-" + "0"+myCalendar.get(Calendar.DAY_OF_MONTH)
                } else if (myCalendar.get(Calendar.MONTH) < 10 &&  myCalendar.get(Calendar.DAY_OF_MONTH) < 10) {
                    date = ""+ myCalendar.get(Calendar.YEAR) + "-"+"0"+myCalendar.get(Calendar.MONTH).plus(1)+ "-" +"0"+ myCalendar.get(Calendar.DAY_OF_MONTH)
                } else {
                    date = ""+ myCalendar.get(Calendar.YEAR) + "-"+myCalendar.get(Calendar.MONTH).plus(1)+ "-" + myCalendar.get(Calendar.DAY_OF_MONTH)
                }
                task.date = date
            } else if (task.day == DayOfWeek.MONDAY.toString()) {
                myCalendar.add(Calendar.DAY_OF_YEAR,-7)
                if(myCalendar.get(Calendar.MONTH)<10 &&  myCalendar.get(Calendar.DAY_OF_MONTH) >= 10) {
                    date = ""+ myCalendar.get(Calendar.YEAR) + "-"+"0"+myCalendar.get(Calendar.MONTH).plus(1)+ "-" + myCalendar.get(Calendar.DAY_OF_MONTH)
                } else if (myCalendar.get(Calendar.MONTH) >= 10 &&  myCalendar.get(Calendar.DAY_OF_MONTH) < 10) {
                    date = ""+ myCalendar.get(Calendar.YEAR) + "-"+myCalendar.get(Calendar.MONTH).plus(1)+ "-" + "0"+myCalendar.get(Calendar.DAY_OF_MONTH)
                } else if (myCalendar.get(Calendar.MONTH) < 10 &&  myCalendar.get(Calendar.DAY_OF_MONTH) < 10) {
                    date = ""+ myCalendar.get(Calendar.YEAR) + "-"+"0"+myCalendar.get(Calendar.MONTH).plus(1)+ "-" +"0"+ myCalendar.get(Calendar.DAY_OF_MONTH)
                } else {
                    date = ""+ myCalendar.get(Calendar.YEAR) + "-"+myCalendar.get(Calendar.MONTH).plus(1)+ "-" + myCalendar.get(Calendar.DAY_OF_MONTH)
                }
                task.date = date
            }


        }

        for (task in liveTasks) {
            task.isDone = false
        }

        TaskDatabase.invoke(this.applicationContext).TaskDao().insertAll(*backupTasks.toTypedArray())
        TaskDatabase.invoke(this.applicationContext).TaskDao().updateMultipleTasks(liveTasks)



        println("all tasks : "+ alltasks)

        println("live Tasks"+liveTasks)
        println("backup Tasks"+backupTasks)



        println("work req")


        return Result.success()
    }

}