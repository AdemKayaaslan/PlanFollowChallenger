package com.mustafaademkayaaslan.planfollowchallenger.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.core.content.ContextCompat.getSystemService
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.mustafaademkayaaslan.planfollowchallenger.util.NotificationHelper

class NotificationMaker(context: Context, workerParams: WorkerParameters): CoroutineWorker(context,
    workerParams
) {
    val myContext = context

    override suspend fun doWork(): Result {

        val notificationHelper = NotificationHelper(myContext)
        notificationHelper.createNotification()
        println("NotificationMaker")
        return Result.success()
    }

}