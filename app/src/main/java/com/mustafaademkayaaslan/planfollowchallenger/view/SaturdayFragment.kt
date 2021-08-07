package com.mustafaademkayaaslan.planfollowchallenger.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.mustafaademkayaaslan.planfollowchallenger.R
import com.mustafaademkayaaslan.planfollowchallenger.adapter.DayRecyclerAdapter
import com.mustafaademkayaaslan.planfollowchallenger.model.Task
import com.mustafaademkayaaslan.planfollowchallenger.viewmodel.SaturdayViewModel
import kotlinx.android.synthetic.main.saturday_fragment.*

class SaturdayFragment : Fragment(),DayRecyclerAdapter.OnItemClickListener {

    private lateinit var viewModel: SaturdayViewModel
    var list = ArrayList<Task>()
    private lateinit var myAdapter : DayRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.saturday_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SaturdayViewModel::class.java)

        viewModel.getSaturdayTasks()
        observeLiveData()

        recycler_view_saturday.layoutManager = LinearLayoutManager(this.context)
        recycler_view_saturday.setHasFixedSize(true)

    }

    fun observeLiveData() {
        viewModel.tasks.observe(viewLifecycleOwner, Observer {tasks->

            tasks?.let {
                myAdapter = DayRecyclerAdapter(tasks,false,this)
                recycler_view_saturday.adapter = myAdapter
                list = it as ArrayList<Task>
                println("observe livedaataa saturday")



            }

        })
    }

    override fun onItemClick(position: Int) {
        val uuid = list[position].uuid
        println("saturday listener : "+uuid)
        println("saturday listener : "+position)
        viewModel.deleteItem(uuid)
        list.removeAt(position)
        myAdapter.notifyItemRemoved(position)
    }

}