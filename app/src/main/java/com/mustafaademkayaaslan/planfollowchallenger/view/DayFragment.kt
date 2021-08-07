package com.mustafaademkayaaslan.planfollowchallenger.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.*
import com.google.android.gms.ads.MobileAds
import com.mustafaademkayaaslan.planfollowchallenger.R
import com.mustafaademkayaaslan.planfollowchallenger.adapter.DayRecyclerAdapter
import com.mustafaademkayaaslan.planfollowchallenger.model.Task
import com.mustafaademkayaaslan.planfollowchallenger.service.NotificationMaker
import com.mustafaademkayaaslan.planfollowchallenger.service.WeeklyRefresher
import com.mustafaademkayaaslan.planfollowchallenger.viewmodel.DayViewModel
import kotlinx.android.synthetic.main.fragment_day.*
import java.time.DayOfWeek
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class DayFragment() : Fragment(),DayRecyclerAdapter.OnItemClickListener {


    private lateinit var viewModel : DayViewModel
    var list = ArrayList<Task>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_day, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        
        viewModel = ViewModelProviders.of(this).get(DayViewModel::class.java)


        val myCalendar = Calendar.getInstance()


        val day = myCalendar.get(Calendar.DAY_OF_WEEK)
        var dayOfWeek = ""
        when(day) {

            1 -> dayOfWeek = DayOfWeek.SUNDAY.toString()
            2 -> dayOfWeek = DayOfWeek.MONDAY.toString()
            3 -> dayOfWeek = DayOfWeek.TUESDAY.toString()
            4 -> dayOfWeek = DayOfWeek.WEDNESDAY.toString()
            5 -> dayOfWeek = DayOfWeek.THURSDAY.toString()
            6 -> dayOfWeek = DayOfWeek.FRIDAY.toString()
            7 -> dayOfWeek = DayOfWeek.SATURDAY.toString()
        }


        viewModel.getTasksByDay(dayOfWeek)
        viewModel.getAllTasks()



        recycler_view.layoutManager = LinearLayoutManager(this.context)
        recycler_view.setHasFixedSize(true)

        observeLiveData()

        notificationCaller()

    }



    fun observeLiveData() {

        viewModel.tasks.observe(viewLifecycleOwner, Observer { tasks ->

            tasks?.let {

                if (it.size>0) {
                    dayEmptyText.visibility=View.GONE
                    noTaskDayArrow.visibility=View.GONE
                }

                recycler_view.adapter = DayRecyclerAdapter(tasks, true, this)
                list = tasks as ArrayList<Task>
                for (name in list) {

                }

            }

        })


        viewModel.allTasks.observe(viewLifecycleOwner, Observer { tasks ->
            tasks?.let {


                val currentDate = Calendar.getInstance()
                val dueDate = Calendar.getInstance()

                dueDate.set(Calendar.HOUR_OF_DAY,2)
                dueDate.set(Calendar.MINUTE,0)

                if (currentDate.get(Calendar.DAY_OF_WEEK)<2) {
                    dueDate.add(Calendar.DAY_OF_YEAR,1)
                } else if (currentDate.get(Calendar.DAY_OF_WEEK)==2) {
                    dueDate.add(Calendar.DAY_OF_YEAR,7)
                } else {
                    val dayDiffirence = 7 - dueDate.get(Calendar.DAY_OF_WEEK)+2
                    dueDate.add(Calendar.DAY_OF_YEAR,dayDiffirence)
                }

                val timeDifferencetoMonday = (dueDate.timeInMillis-currentDate.timeInMillis)

                val refreshWorkRequest: WorkRequest =
                    PeriodicWorkRequestBuilder<WeeklyRefresher>(7, TimeUnit.DAYS)
                       .setInitialDelay(timeDifferencetoMonday, TimeUnit.MILLISECONDS)
                        .build()

                WorkManager.getInstance(this.requireContext()).enqueue(refreshWorkRequest)

            }
        })




    }

    fun notificationCaller () {

        val currentCalendar = Calendar.getInstance()
        val notificationCalendar = Calendar.getInstance()

        notificationCalendar.set(Calendar.HOUR_OF_DAY, 20)
        notificationCalendar.set(Calendar.MINUTE, 30)

        if (notificationCalendar.getTime().compareTo(Date()) <= 0) {notificationCalendar.add(Calendar.DAY_OF_MONTH, 1)}

        val notificationTimeDiffirance =(notificationCalendar.timeInMillis - currentCalendar.timeInMillis)


        val notificationWorkRequest: WorkRequest =
            PeriodicWorkRequestBuilder<NotificationMaker>(24, TimeUnit.HOURS)
                .setInitialDelay(notificationTimeDiffirance, TimeUnit.MILLISECONDS)
                .build()

        WorkManager.getInstance(this.requireContext()).enqueue(notificationWorkRequest)

    }


    override fun onItemClick(position: Int) {
        if (list[position].isDone==false) {
            list[position].isDone=true
        } else {
            list[position].isDone=false
        }

        viewModel.updateTask(list[position])

    }

}