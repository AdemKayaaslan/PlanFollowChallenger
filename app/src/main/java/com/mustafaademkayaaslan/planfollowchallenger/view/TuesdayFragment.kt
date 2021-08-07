package com.mustafaademkayaaslan.planfollowchallenger.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.mustafaademkayaaslan.planfollowchallenger.R
import com.mustafaademkayaaslan.planfollowchallenger.adapter.DayRecyclerAdapter
import com.mustafaademkayaaslan.planfollowchallenger.model.Task
import com.mustafaademkayaaslan.planfollowchallenger.viewmodel.TuesdayViewModel
import kotlinx.android.synthetic.main.fragment_tuesday.*

class TuesdayFragment : Fragment(),DayRecyclerAdapter.OnItemClickListener {

    private lateinit var viewModel:TuesdayViewModel
    var list = ArrayList<Task>()
    private lateinit var myAdapter : DayRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tuesday, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(TuesdayViewModel::class.java)

        viewModel.getTuesdayTasks()
        observeLiveData()

        recycler_view_task_tuesday.layoutManager = LinearLayoutManager(this.context)
        recycler_view_task_tuesday.setHasFixedSize(true)

    }

    fun observeLiveData() {
        viewModel.tasks.observe(viewLifecycleOwner, Observer {tasks->

            tasks?.let {
                myAdapter = DayRecyclerAdapter(tasks,false,this)
                recycler_view_task_tuesday.adapter = myAdapter
                println("observe livedaataa Tuesday")
                list = tasks as ArrayList<Task>

            }

        })
    }

    override fun onItemClick(position: Int) {
        val uuid = list[position].uuid
        println("tuesday listener uuid : "+uuid)
        println("tuesday listener position : "+position)
        list.removeAt(position)
        viewModel.deleteItem(uuid)
        myAdapter.notifyItemRemoved(position)
    }

}