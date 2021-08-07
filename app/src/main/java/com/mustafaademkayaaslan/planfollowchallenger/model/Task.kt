package com.mustafaademkayaaslan.planfollowchallenger.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task (

    @ColumnInfo(name="name")
    val name:String,

    @ColumnInfo(name="day")
    val day:String,

    @ColumnInfo(name="starttime")
    val startTime:String,

    @ColumnInfo(name="stoptime")
    val stopTime:String,

    @ColumnInfo(name="isdone")
    var isDone:Boolean,

    @ColumnInfo(name="week")
    var week:Int,

    @ColumnInfo(name="date")
    var date:String

) {
    @PrimaryKey(autoGenerate = true)
    var uuid:Int = 0
}