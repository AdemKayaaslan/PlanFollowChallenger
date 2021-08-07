package com.mustafaademkayaaslan.planfollowchallenger.view

import android.app.AlertDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mustafaademkayaaslan.planfollowchallenger.R
import com.mustafaademkayaaslan.planfollowchallenger.adapter.ViewPagerAdapter
import com.mustafaademkayaaslan.planfollowchallenger.model.Task
import com.mustafaademkayaaslan.planfollowchallenger.viewmodel.TaskAddViewModel
import com.google.android.material.tabs.TabLayoutMediator
import com.mustafaademkayaaslan.planfollowchallenger.adapter.DayRecyclerAdapter
import kotlinx.android.synthetic.main.activity_task_add.*
import kotlinx.android.synthetic.main.popup_task_add.*
import java.time.DayOfWeek
import java.util.*

class TaskAddActivity : AppCompatActivity(),TimePickerDialog.OnTimeSetListener {


    private var isSettedFromStartButton:Boolean = true
    private var isCalledFromSaveButton:Boolean = false
    private lateinit var startTextview:TextView
    private lateinit var stopTextview:TextView
    private lateinit var nameEditText:EditText

    var list = ArrayList<Task>()


    private lateinit var viewModel:TaskAddViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_add)

        viewModel = ViewModelProviders.of(this).get(TaskAddViewModel::class.java)




        val mDialog = Dialog(this)
        mDialog.setContentView(R.layout.popup_task_add)

        val saveButton = mDialog.findViewById<Button>(R.id.save_button)
        val startButton = mDialog.findViewById<Button>(R.id.startButton)
        val stopButton = mDialog.findViewById<Button>(R.id.stopButton)
        startTextview = mDialog.findViewById<TextView>(R.id.startText)
        stopTextview = mDialog.findViewById<TextView>(R.id.stopText)
        nameEditText = mDialog.findViewById<EditText>(R.id.nameEditText)
        val monday = mDialog.findViewById<CheckBox>(R.id.monday)
        val tuesday = mDialog.findViewById<CheckBox>(R.id.tuesday)
        val wednesday = mDialog.findViewById<CheckBox>(R.id.wednesday)
        val thursday = mDialog.findViewById<CheckBox>(R.id.thursday)
        val friday = mDialog.findViewById<CheckBox>(R.id.friday)
        val saturday = mDialog.findViewById<CheckBox>(R.id.saturday)
        val sunday = mDialog.findViewById<CheckBox>(R.id.sunday)


        viewPager.adapter = ViewPagerAdapter(this)

        TabLayoutMediator(tab_layout,viewPager) { tab, position ->

            when (position) {
                0 -> tab.text = DayOfWeek.MONDAY.toString()
                1 -> tab.text = DayOfWeek.TUESDAY.toString()
                2 -> tab.text = DayOfWeek.WEDNESDAY.toString()
                3 -> tab.text = DayOfWeek.THURSDAY.toString()
                4 -> tab.text = DayOfWeek.FRIDAY.toString()
                5 -> tab.text = DayOfWeek.SATURDAY.toString()
                6 -> tab.text = DayOfWeek.SUNDAY.toString()
            }

        }.attach()


        addButton.setOnClickListener {
            mDialog.show()


            startButton.setOnClickListener {
                TimePickerDialog(this,this,8,8,true).show()
                isSettedFromStartButton = true
            }

            stopButton.setOnClickListener {
                TimePickerDialog(this,this,8,8,true).show()

                isSettedFromStartButton = false
            }

            saveButton.setOnClickListener {
                val taskNameFromUser = nameEditText.text.toString()
                val startTime = startTextview.text.toString()
                val stopTime = stopTextview.text.toString()
                if (taskNameFromUser.isEmpty()) {
                    nameEditText.error = "Name required"
                    nameEditText.requestFocus()
                    return@setOnClickListener
                }


                val isDone = false
                val week = 0
                val date = "-"
                val taskList = arrayListOf<Task>()
                if (monday.isChecked) {
                    val monday = DayOfWeek.MONDAY.toString()

                    val mondayTask=Task(taskNameFromUser,monday,startTime,stopTime,isDone,week,date)
                    taskList.add(mondayTask)
                }
                if (tuesday.isChecked) {
                    val tuesday = DayOfWeek.TUESDAY.toString()
                    val taskNameFromUser = nameEditText.text.toString()
                    val startTime = startTextview.text.toString()
                    val stopTime = stopTextview.text.toString()
                    val tuesdayTask=Task(taskNameFromUser,tuesday,startTime,stopTime,isDone,week,date)
                    taskList.add(tuesdayTask)
                }
                if (wednesday.isChecked) {
                    val wednesday = DayOfWeek.WEDNESDAY.toString()
                    val wednesdayTask=Task(taskNameFromUser,wednesday,startTime,stopTime,isDone,week,date)
                    taskList.add(wednesdayTask)
                }
                if (thursday.isChecked) {
                    val thursday = DayOfWeek.THURSDAY.toString()
                    val thursdayTask=Task(taskNameFromUser,thursday,startTime,stopTime,isDone,week,date)
                    taskList.add(thursdayTask)
                }
                if (friday.isChecked) {
                    val friday = DayOfWeek.FRIDAY.toString()
                    val fridayTask=Task(taskNameFromUser,friday,startTime,stopTime,isDone,week,date)
                    taskList.add(fridayTask)
                }
                if (saturday.isChecked) {
                    val saturday = DayOfWeek.SATURDAY.toString()
                    val saturdayTask=Task(taskNameFromUser,saturday,startTime,stopTime,isDone,week,date)
                    taskList.add(saturdayTask)
                }
                if (sunday.isChecked) {
                    val sunday = DayOfWeek.SUNDAY.toString()
                    val sundayTask=Task(taskNameFromUser,sunday,startTime,stopTime,isDone,week,date)
                    taskList.add(sundayTask)
                }

                if (!taskList.isEmpty()) {
                    viewModel.storeInSQLite(taskList)
                    mDialog.cancel()
                    monday.isChecked=false
                    tuesday.isChecked=false
                    wednesday.isChecked=false
                    thursday.isChecked=false
                    friday.isChecked=false
                    saturday.isChecked=false
                    sunday.isChecked=false
                    startTextview.text = "-- : --"
                    stopTextview.text = "-- : --"
                    nameEditText.text.clear()
                    firstOpenText.visibility = View.GONE
                    firstOpenImageView.visibility=View.GONE
                } else {
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("No Task Added")
                    builder.setMessage("No tasks will be added because no days are checked, do you want to quit anyway?")

                    builder.setPositiveButton("NO",DialogInterface.OnClickListener { dialog, which ->
                        //Toast.makeText(this, "check checkboxes!", Toast.LENGTH_SHORT).show()
                    })
                    
                    builder.setNegativeButton("YES",DialogInterface.OnClickListener { dialog, which -> 
                        mDialog.cancel()
                        monday.isChecked=false
                        tuesday.isChecked=false
                        wednesday.isChecked=false
                        thursday.isChecked=false
                        friday.isChecked=false
                        saturday.isChecked=false
                        sunday.isChecked=false
                        startTextview.text = "-- : --"
                        stopTextview.text = "-- : --"
                        nameEditText.text.clear()
                    })
                    builder.show()
                }


                val lastPosition = viewPager.currentItem
                viewPager.adapter = ViewPagerAdapter(this)
                viewPager.currentItem=lastPosition
                isCalledFromSaveButton = true
                viewModel.getTasks()
                observeLiveData()
            }
        }


        viewModel.getTasks()
        observeLiveData()


    }

    fun fakesave (view:View) {
        println("saveButtonreAL")
        if (list.size>0) {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "There is no task in your plan.", Toast.LENGTH_SHORT).show()
        }

    }

    fun observeLiveData() {

        val menuBoolean:Boolean = intent.getBooleanExtra("menu",true)

        viewModel.tasks.observe(this, Observer { tasks->
            tasks?.let {

                if (it.size>0) {
                    firstOpenText.visibility = View.GONE
                    firstOpenImageView.visibility=View.GONE
                }

                if (it.size >0 && menuBoolean && !isCalledFromSaveButton) {
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }

                list = tasks as ArrayList<Task>
            }


            println("tasks addactivvv: "+list+" "+list.size + " tasks itself"+ tasks)
        })
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {

        if (isSettedFromStartButton) {
            startTextview.text = ""+hourOfDay + " : " + minute
        } else {
            stopTextview.text = ""+hourOfDay + " : " + minute
        }

    }



}