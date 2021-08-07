package com.mustafaademkayaaslan.planfollowchallenger.view

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mustafaademkayaaslan.planfollowchallenger.R
import com.mustafaademkayaaslan.planfollowchallenger.model.Task
import com.mustafaademkayaaslan.planfollowchallenger.viewmodel.StatisticsViewModel
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import com.kizitonwose.calendarview.utils.yearMonth
import kotlinx.android.synthetic.main.fragment_statistics.*
import java.time.*
import java.time.format.DateTimeFormatter
import java.time.temporal.WeekFields
import java.util.*
import kotlin.collections.ArrayList


class StatisticsFragment : Fragment() {

    private lateinit var viewModel : StatisticsViewModel

    var mondayList = ArrayList<Task>()
    var tuesdayList = ArrayList<Task>()
    var wednesdayList = ArrayList<Task>()
    var thursdayList = ArrayList<Task>()
    var fridayList = ArrayList<Task>()
    var saturdayList = ArrayList<Task>()
    var sundayList = ArrayList<Task>()
    var weekList = ArrayList<Task>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_statistics, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(StatisticsViewModel::class.java)

        viewModel.getStatisticsTasks()


        createCalendar()
        observeLiveData()


    }


    fun observeLiveData () {

        viewModel.tasks.observe(viewLifecycleOwner, Observer { tasks ->

            tasks?.let {


                var allTasksCounter = 0
                var mondayCounter = 0
                var tuesdayCounter = 0
                var wednesdayCounter = 0
                var thursdayCounter = 0
                var fridayCounter = 0
                var saturdayCounter = 0
                var sundayCounter = 0

                var lastWeekCounter = 0
                var lastWeekSuccessCounter = 0
                var lastWeekAllTasksCounter = 0

                for (task in it) {


                    if (task.isDone) {allTasksCounter++}
                    if (task.day==DayOfWeek.MONDAY.toString() && task.isDone) {mondayCounter++}
                    if (task.day==DayOfWeek.TUESDAY.toString() && task.isDone) {tuesdayCounter++}
                    if (task.day==DayOfWeek.WEDNESDAY.toString() && task.isDone) {wednesdayCounter++}
                    if (task.day==DayOfWeek.THURSDAY.toString() && task.isDone) {thursdayCounter++}
                    if (task.day==DayOfWeek.FRIDAY.toString() && task.isDone) {fridayCounter++}
                    if (task.day==DayOfWeek.SATURDAY.toString() && task.isDone) {saturdayCounter++}
                    if (task.day==DayOfWeek.SUNDAY.toString() && task.isDone) {sundayCounter++}

                    if (task.week > lastWeekCounter) {lastWeekCounter++}


                    if (task.day == DayOfWeek.MONDAY.toString()) {
                        mondayList.add(task)
                    } else if (task.day == DayOfWeek.TUESDAY.toString()) {
                        tuesdayList.add(task)
                    } else if (task.day == DayOfWeek.WEDNESDAY.toString()) {
                        wednesdayList.add(task)
                    } else if (task.day == DayOfWeek.THURSDAY.toString()) {
                        thursdayList.add(task)
                    } else if (task.day == DayOfWeek.FRIDAY.toString()) {
                        fridayList.add(task)
                    } else if (task.day == DayOfWeek.SATURDAY.toString()) {
                        saturdayList.add(task)
                    } else if (task.day == DayOfWeek.SUNDAY.toString()) {
                        sundayList.add(task)
                    }

                    if (task.week==1) {weekList.add(task)}

                }



                for (task in it) {
                    if (task.week == lastWeekCounter) {
                        lastWeekAllTasksCounter++
                        if (task.isDone) {
                            lastWeekSuccessCounter++
                        }
                    }
                }

                var lastWeekPercent = 0
                if (lastWeekSuccessCounter != 0) lastWeekPercent = 100*lastWeekSuccessCounter/lastWeekAllTasksCounter


                var allPercent = 0
                if (allTasksCounter != 0) allPercent = 100*allTasksCounter/it.size
                statisticsText.text = "All time completed tasks = %" + allPercent+"\n"  + "Total tasks completed = "+allTasksCounter + "/" + tasks.size +"\n" + "Last week  = %"+lastWeekPercent

                var mondayPercent = 0
                if (mondayCounter != 0) mondayPercent = 100*mondayCounter/mondayList.size

                var tuesdayPercent = 0
                if (tuesdayCounter != 0) tuesdayPercent = 100*tuesdayCounter/tuesdayList.size

                var wednesdayPercent = 0
                if (wednesdayCounter != 0) wednesdayPercent = 100*wednesdayCounter/wednesdayList.size

                var thursdayPercent = 0
                if (thursdayCounter != 0) thursdayPercent = 100*thursdayCounter/thursdayList.size


                firstDaysPercentText.text = "All time monday = %"+mondayPercent+"\n"+"All time tuesday = %"+tuesdayPercent+"\n"+"All time wednesday = %"+wednesdayPercent+"\n"+"All time thursday = %"+thursdayPercent

                var fridayPercent = 0
                if (fridayCounter != 0) fridayPercent = 100*fridayCounter/fridayList.size

                var saturdayPercent = 0
                if (saturdayCounter != 0) saturdayPercent = 100*saturdayCounter/saturdayList.size

                var sundayPercent = 0
                if (sundayCounter != 0) sundayPercent = 100*sundayCounter/sundayList.size

                secondDaysPercentText.text = "All time friday = %"+fridayPercent+"\n"+"All time saturday = %"+saturdayPercent+"\n"+"All time sunday = %"+sundayPercent

                createCalendar()
            }

        })

    }


    fun createCalendar () {


        class DayViewContainer(view: View) : ViewContainer(view) {
            val textView = view.findViewById<TextView>(R.id.calendarDayText)

            lateinit var day: CalendarDay
            init {


            }

        }


        calendarView.dayBinder = object : DayBinder<DayViewContainer> {
            // Called only when a new container is needed.
            override fun create(view: View) = DayViewContainer(view)

            // Called every time we need to reuse a container.
            override fun bind(container: DayViewContainer, day: CalendarDay) {
                container.textView.text = day.date.dayOfMonth.toString()



                calendarView.monthScrollListener = {
                    val month = it.yearMonth.month.toString().toLowerCase()
                    yearText.text =  it.yearMonth.year.toString()
                    monthText.text = month[0].toUpperCase()+month.substring(1)
                }


                // Set the calendar day for this container.
                container.day = day
                //container.view.setBackgroundResource(R.drawable.background_shape)


                val today = LocalDate.now()


                if (day.owner == DayOwner.THIS_MONTH) {
                    container.textView.setTextColor(Color.BLUE)

                    if (today==day.date) {
                        container.view.setBackgroundResource(R.drawable.today_shape)
                        container.textView.setTextColor(Color.WHITE)
                    }
                } else {
                    container.textView.setTextColor(Color.GRAY)
                }



                var temporaryListMonday = arrayListOf<Task>()
                var controlTaskMonday = Task("", "", "", "", false, 0, "")
                var mondayPercent = 2
                var mondayJ = 0
                if (mondayList != null) {

                    for (task in mondayList) {
                        mondayJ++
                        val mondayDate = task.date
                        val date = LocalDate.parse(mondayDate, DateTimeFormatter.ISO_DATE)

                        if (controlTaskMonday.date != "") {
                            if (controlTaskMonday.date == task.date) {
                                temporaryListMonday.add(task)
                            } else {
                                var i = 0
                                for (task in temporaryListMonday) {
                                    if (task.isDone) {
                                        i++
                                    }

                                }
                                mondayPercent = 100*i/temporaryListMonday.size
                                if (mondayPercent<25 && date == day.date) {
                                    container.view.setBackgroundResource(R.drawable.red_shape)
                                } else if (25 <= mondayPercent && mondayPercent < 75 && date == day.date) {
                                    container.view.setBackgroundResource(R.drawable.yellow_shape)
                                } else if (mondayPercent >= 75 && date == day.date) {
                                    container.view.setBackgroundResource(R.drawable.green_shape)
                                }
                                temporaryListMonday = arrayListOf<Task>()
                                temporaryListMonday.add(task)

                            }


                            if (mondayJ==mondayList.size) {
                                var i = 0
                                for (task in temporaryListMonday) {
                                    if (task.isDone) {
                                        i++
                                    }

                                }
                                mondayPercent = 100*i/temporaryListMonday.size
                                if (mondayPercent<25 && date == day.date) {
                                    container.view.setBackgroundResource(R.drawable.red_shape)
                                } else if (25 <= mondayPercent && mondayPercent < 75 && date == day.date) {
                                    container.view.setBackgroundResource(R.drawable.yellow_shape)
                                } else if (mondayPercent >= 75 && date == day.date) {
                                    container.view.setBackgroundResource(R.drawable.green_shape)
                                }
                            }

                        } else {
                            temporaryListMonday.add(task)
                            if (task.isDone){
                                if (day.date==date) {container.view.setBackgroundResource(R.drawable.green_shape)}
                            } else {
                                if (day.date==date) {container.view.setBackgroundResource(R.drawable.red_shape)}
                            }
                        }

                        controlTaskMonday=task

                    }
                }




                               var temporaryListTuesday = arrayListOf<Task>()
                               var controlTaskTuesday = Task("", "", "", "", false, 0, "")
                               var percent = 2
                               var j = 0
                               if (tuesdayList != null) {

                                   for (task in tuesdayList) {
                                       j++
                                       val tuesdayDate = task.date
                                       val date = LocalDate.parse(
                                           tuesdayDate,
                                           DateTimeFormatter.ISO_DATE
                                       )

                                       if (controlTaskTuesday.date != "") {
                                           if (controlTaskTuesday.date == task.date) {
                                               temporaryListTuesday.add(task)
                                           } else {
                                               var i = 0
                                               for (task in temporaryListTuesday) {
                                                   if (task.isDone) {
                                                       i++
                                                   }

                                               }
                                               percent = 100*i/temporaryListTuesday.size
                                               if (percent<25 && date == day.date) {
                                                   container.view.setBackgroundResource(R.drawable.red_shape)
                                               } else if (25 <= percent && percent < 75 && date == day.date) {
                                                   container.view.setBackgroundResource(R.drawable.yellow_shape)
                                               } else if (percent >= 75 && date == day.date) {
                                                   container.view.setBackgroundResource(R.drawable.green_shape)
                                               }
                                               temporaryListTuesday = arrayListOf<Task>()
                                               temporaryListTuesday.add(task)

                                           }



                                           if (j==tuesdayList.size) {
                                               var i = 0
                                               for (task in temporaryListTuesday) {
                                                   j++
                                                   if (task.isDone) {
                                                       i++
                                                   }

                                               }
                                               percent = 100*i/temporaryListTuesday.size
                                               if (percent<25 && date == day.date) {
                                                   container.view.setBackgroundResource(R.drawable.red_shape)
                                               } else if (25 <= percent && percent < 75 && date == day.date) {
                                                   container.view.setBackgroundResource(R.drawable.yellow_shape)
                                               } else if (percent >= 75 && date == day.date) {
                                                   container.view.setBackgroundResource(R.drawable.green_shape)
                                               }
                                           }

                                       } else {
                                           temporaryListTuesday.add(task)
                                           if (task.isDone){
                                               if (day.date==date) {container.view.setBackgroundResource(
                                                   R.drawable.green_shape
                                               )}
                                           } else {
                                               if (day.date==date) {container.view.setBackgroundResource(
                                                   R.drawable.red_shape
                                               )}
                                           }
                                       }

                                       controlTaskTuesday=task

                                   }
                               }




                               var temporaryListWednesday = arrayListOf<Task>()
                               var controlTaskWednesday = Task("", "", "", "", false, 0, "")
                               var wednesdayPercent = 2
                               var wednesdayJ = 0
                               if (wednesdayList != null) {

                                   for (task in wednesdayList) {
                                       wednesdayJ++
                                       val wednesdayDate = task.date
                                       val date = LocalDate.parse(
                                           wednesdayDate,
                                           DateTimeFormatter.ISO_DATE
                                       )

                                       if (controlTaskWednesday.date != "") {
                                           if (controlTaskWednesday.date == task.date) {
                                               temporaryListWednesday.add(task)
                                           } else {
                                               var i = 0
                                               for (task in temporaryListWednesday) {
                                                   if (task.isDone) {
                                                       i++
                                                   }

                                               }
                                               wednesdayPercent = 100*i/temporaryListWednesday.size
                                               if (wednesdayPercent<25 && date == day.date) {
                                                   container.view.setBackgroundResource(R.drawable.red_shape)
                                               } else if (25 <= wednesdayPercent && wednesdayPercent < 75 && date == day.date) {
                                                   container.view.setBackgroundResource(R.drawable.yellow_shape)
                                               } else if (wednesdayPercent >= 75 && date == day.date) {
                                                   container.view.setBackgroundResource(R.drawable.green_shape)
                                               }
                                               temporaryListWednesday = arrayListOf<Task>()
                                               temporaryListWednesday.add(task)

                                           }



                                           if (wednesdayJ==wednesdayList.size) {
                                               var i = 0
                                               for (task in temporaryListWednesday) {
                                                   if (task.isDone) {
                                                       i++
                                                   }

                                               }
                                               wednesdayPercent = 100*i/temporaryListWednesday.size
                                               if (wednesdayPercent<25 && date == day.date) {
                                                   container.view.setBackgroundResource(R.drawable.red_shape)
                                               } else if (25 <= wednesdayPercent && wednesdayPercent < 75 && date == day.date) {
                                                   container.view.setBackgroundResource(R.drawable.yellow_shape)
                                               } else if (wednesdayPercent >= 75 && date == day.date) {
                                                   container.view.setBackgroundResource(R.drawable.green_shape)
                                               }
                                           }

                                       } else {
                                           temporaryListWednesday.add(task)
                                           if (task.isDone){
                                               if (day.date==date) {container.view.setBackgroundResource(
                                                   R.drawable.green_shape
                                               )}
                                           } else {
                                               if (day.date==date) {container.view.setBackgroundResource(
                                                   R.drawable.red_shape
                                               )}
                                           }
                                       }

                                       controlTaskWednesday=task

                                   }
                               }





                               var temporaryListThursday = arrayListOf<Task>()
                               var controlTaskThursday = Task("", "", "", "", false, 0, "")
                               var thursdayPercent = 2
                               var thursdayJ = 0
                               if (thursdayList != null) {

                                   for (task in thursdayList) {
                                       thursdayJ++
                                       val tuesdayDate = task.date
                                       val date = LocalDate.parse(
                                           tuesdayDate,
                                           DateTimeFormatter.ISO_DATE
                                       )

                                       if (controlTaskThursday.date != "") {
                                           if (controlTaskThursday.date == task.date) {
                                               temporaryListThursday.add(task)
                                           } else {
                                               var i = 0
                                               for (task in temporaryListThursday) {
                                                   if (task.isDone) {
                                                       i++
                                                   }

                                               }
                                               thursdayPercent = 100*i/temporaryListThursday.size
                                               if (thursdayPercent<25 && date == day.date) {
                                                   container.view.setBackgroundResource(R.drawable.red_shape)
                                               } else if (25 <= thursdayPercent && thursdayPercent < 75 && date == day.date) {
                                                   container.view.setBackgroundResource(R.drawable.yellow_shape)
                                               } else if (thursdayPercent >= 75 && date == day.date) {
                                                   container.view.setBackgroundResource(R.drawable.green_shape)
                                               }
                                               temporaryListThursday = arrayListOf<Task>()
                                               temporaryListThursday.add(task)

                                           }



                                           if (thursdayJ==thursdayList.size) {
                                               var i = 0
                                               for (task in temporaryListThursday) {
                                                   if (task.isDone) {
                                                       i++
                                                   }

                                               }
                                               thursdayPercent = 100*i/temporaryListThursday.size
                                               if (thursdayPercent<25 && date == day.date) {
                                                   container.view.setBackgroundResource(R.drawable.red_shape)
                                               } else if (25 <= thursdayPercent && thursdayPercent < 75 && date == day.date) {
                                                   container.view.setBackgroundResource(R.drawable.yellow_shape)
                                               } else if (thursdayPercent >= 75 && date == day.date) {
                                                   container.view.setBackgroundResource(R.drawable.green_shape)
                                               }
                                           }

                                       } else {
                                           temporaryListThursday.add(task)
                                           if (task.isDone){
                                               if (day.date==date) {container.view.setBackgroundResource(
                                                   R.drawable.green_shape
                                               )}
                                           } else {
                                               if (day.date==date) {container.view.setBackgroundResource(
                                                   R.drawable.red_shape
                                               )}
                                           }
                                       }

                                       controlTaskThursday=task

                                   }
                               }




                               var temporaryListFriday = arrayListOf<Task>()
                               var controlTaskFriday = Task("", "", "", "", false, 0, "")
                               var fridayPercent = 2
                               var fridayJ = 0
                               if (fridayList != null) {

                                   for (task in fridayList) {
                                       fridayJ++
                                       val tuesdayDate = task.date
                                       val date = LocalDate.parse(
                                           tuesdayDate,
                                           DateTimeFormatter.ISO_DATE
                                       )

                                       if (controlTaskFriday.date != "") {
                                           if (controlTaskFriday.date == task.date) {
                                               temporaryListFriday.add(task)
                                           } else {
                                               var i = 0
                                               for (task in temporaryListFriday) {
                                                   if (task.isDone) {
                                                       i++
                                                   }

                                               }
                                               fridayPercent = 100*i/temporaryListFriday.size
                                               if (fridayPercent<25 && date == day.date) {
                                                   container.view.setBackgroundResource(R.drawable.red_shape)
                                               } else if (25 <= fridayPercent && fridayPercent < 75 && date == day.date) {
                                                   container.view.setBackgroundResource(R.drawable.yellow_shape)
                                               } else if (fridayPercent >= 75 && date == day.date) {
                                                   container.view.setBackgroundResource(R.drawable.green_shape)
                                               }
                                               temporaryListFriday = arrayListOf<Task>()
                                               temporaryListFriday.add(task)

                                           }



                                           if (fridayJ==fridayList.size) {
                                               var i = 0
                                               for (task in temporaryListFriday) {
                                                   if (task.isDone) {
                                                       i++
                                                   }

                                               }
                                               fridayPercent = 100*i/temporaryListFriday.size
                                               if (fridayPercent<25 && date == day.date) {
                                                   container.view.setBackgroundResource(R.drawable.red_shape)
                                               } else if (25 <= fridayPercent && fridayPercent < 75 && date == day.date) {
                                                   container.view.setBackgroundResource(R.drawable.yellow_shape)
                                               } else if (fridayPercent >= 75 && date == day.date) {
                                                   container.view.setBackgroundResource(R.drawable.green_shape)
                                               }
                                           }

                                       } else {
                                           temporaryListFriday.add(task)
                                           if (task.isDone){
                                               if (day.date==date) {container.view.setBackgroundResource(
                                                   R.drawable.green_shape
                                               )}
                                           } else {
                                               if (day.date==date) {container.view.setBackgroundResource(
                                                   R.drawable.red_shape
                                               )}
                                           }
                                       }

                                       controlTaskFriday=task

                                   }
                               }






                               var temporaryListSaturday = arrayListOf<Task>()
                               var controlTaskSaturday = Task("", "", "", "", false, 0, "")
                               var saturdayPercent = 2
                               var saturdayJ = 0
                               if (saturdayList != null) {

                                   for (task in saturdayList) {
                                       saturdayJ++
                                       val tuesdayDate = task.date
                                       val date = LocalDate.parse(
                                           tuesdayDate,
                                           DateTimeFormatter.ISO_DATE
                                       )

                                       if (controlTaskSaturday.date != "") {
                                           if (controlTaskSaturday.date == task.date) {
                                               temporaryListSaturday.add(task)
                                           } else {
                                               var i = 0
                                               for (task in temporaryListSaturday) {
                                                   if (task.isDone) {
                                                       i++
                                                   }

                                               }
                                               saturdayPercent = 100*i/temporaryListSaturday.size
                                               if (saturdayPercent<25 && date == day.date) {
                                                   container.view.setBackgroundResource(R.drawable.red_shape)
                                               } else if (25 <= saturdayPercent && saturdayPercent < 75 && date == day.date) {
                                                   container.view.setBackgroundResource(R.drawable.yellow_shape)
                                               } else if (saturdayPercent >= 75 && date == day.date) {
                                                   container.view.setBackgroundResource(R.drawable.green_shape)
                                               }
                                               temporaryListSaturday = arrayListOf<Task>()
                                               temporaryListSaturday.add(task)

                                           }



                                           if (saturdayJ==saturdayList.size) {
                                               var i = 0
                                               for (task in temporaryListSaturday) {
                                                   if (task.isDone) {
                                                       i++
                                                   }

                                               }
                                               saturdayPercent = 100*i/temporaryListSaturday.size
                                               if (saturdayPercent<25 && date == day.date) {
                                                   container.view.setBackgroundResource(R.drawable.red_shape)
                                               } else if (25 <= saturdayPercent && saturdayPercent < 75 && date == day.date) {
                                                   container.view.setBackgroundResource(R.drawable.yellow_shape)
                                               } else if (saturdayPercent >= 75 && date == day.date) {
                                                   container.view.setBackgroundResource(R.drawable.green_shape)
                                               }
                                           }

                                       } else {
                                           temporaryListSaturday.add(task)
                                           if (task.isDone){
                                               if (day.date==date) {container.view.setBackgroundResource(
                                                   R.drawable.green_shape
                                               )}
                                           } else {
                                               if (day.date==date) {container.view.setBackgroundResource(
                                                   R.drawable.red_shape
                                               )}
                                           }
                                       }

                                       controlTaskSaturday=task

                                   }
                               }




                               var temporaryListSunday = arrayListOf<Task>()
                               var controlTaskSunday = Task("", "", "", "", false, 0, "")
                               var sundayPercent = 2
                               var sundayJ = 0
                               if (sundayList != null) {

                                   for (task in sundayList) {
                                       sundayJ++
                                       val sundayDate = task.date
                                       val date = LocalDate.parse(
                                           sundayDate,
                                           DateTimeFormatter.ISO_DATE
                                       )

                                       if (controlTaskSunday.date != "") {
                                           if (controlTaskSunday.date == task.date) {
                                               temporaryListSunday.add(task)
                                           } else {
                                               var i = 0
                                               for (task in temporaryListSunday) {
                                                   if (task.isDone) {
                                                       i++
                                                   }

                                               }
                                               sundayPercent = 100*i/temporaryListSunday.size
                                               if (sundayPercent<25 && date == day.date) {
                                                   container.view.setBackgroundResource(R.drawable.red_shape)
                                               } else if (25 <= sundayPercent && sundayPercent < 75 && date == day.date) {
                                                   container.view.setBackgroundResource(R.drawable.yellow_shape)
                                               } else if (sundayPercent >= 75 && date == day.date) {
                                                   container.view.setBackgroundResource(R.drawable.green_shape)
                                               }
                                               temporaryListSunday = arrayListOf<Task>()
                                               temporaryListSunday.add(task)

                                           }



                                           if (sundayJ==sundayList.size) {
                                               var i = 0
                                               for (task in temporaryListSunday) {
                                                   if (task.isDone) {
                                                       i++
                                                   }

                                               }
                                               sundayPercent = 100*i/temporaryListSunday.size
                                               if (sundayPercent<25 && date == day.date) {
                                                   container.view.setBackgroundResource(R.drawable.red_shape)
                                               } else if (25 <= sundayPercent && sundayPercent < 75 && date == day.date) {
                                                   container.view.setBackgroundResource(R.drawable.yellow_shape)
                                               } else if (sundayPercent >= 75 && date == day.date) {
                                                   container.view.setBackgroundResource(R.drawable.green_shape)
                                               }
                                           }

                                       } else {
                                           temporaryListSunday.add(task)
                                           if (task.isDone){
                                               if (day.date==date) {container.view.setBackgroundResource(
                                                   R.drawable.green_shape
                                               )}
                                           } else {
                                               if (day.date==date) {container.view.setBackgroundResource(
                                                   R.drawable.red_shape
                                               )}
                                           }
                                       }

                                       controlTaskSunday=task

                                   }
                               }



            }


        }


        val currentMonth = YearMonth.now()
        var firstMonth = currentMonth.minusMonths(1)


        if (weekList.size>0) {
            val firstWeekDate = weekList[0].date
            val date = LocalDate.parse(firstWeekDate, DateTimeFormatter.ISO_DATE)
            firstMonth = date.yearMonth.minusMonths(1)
        }

        val lastMonth = currentMonth.plusMonths(1)
        val firstDayOfWeek = DayOfWeek.MONDAY //WeekFields.of(Locale.getDefault()).firstDayOfWeek
        val string = "weekfields turkey "+WeekFields.of(Locale.getDefault()).dayOfWeek()
        calendarView.setup(firstMonth, lastMonth, firstDayOfWeek)
        calendarView.scrollToMonth(currentMonth)
        /*
        if (firstDayOfWeek == DayOfWeek.SUNDAY) {
            legendLayout.legendText1.text = "SUNqq"
            legendLayout.legendText2.text = "MON"
            legendLayout.legendText3.text = "TUE"
            legendLayout.legendText4.text = "WED"
            legendLayout.legendText5.text = "THU"
            legendLayout.legendText6.text = "FRI"
            legendLayout.legendText7.text = "SAT"

        }

         */

    }


}