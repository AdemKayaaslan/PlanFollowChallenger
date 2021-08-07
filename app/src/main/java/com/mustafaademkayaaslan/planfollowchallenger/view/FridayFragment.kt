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
import com.mustafaademkayaaslan.planfollowchallenger.viewmodel.FridayViewModel
import kotlinx.android.synthetic.main.friday_fragment.*

class FridayFragment : Fragment(),DayRecyclerAdapter.OnItemClickListener {
    private lateinit var viewModel: FridayViewModel
    var list = ArrayList<Task>()
    private lateinit var myAdapter : DayRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.friday_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FridayViewModel::class.java)


        viewModel.getFridayTasks()
        observeLiveData()

        recycler_view_friday.layoutManager = LinearLayoutManager(this.context)
        recycler_view_friday.setHasFixedSize(true)

    }

    fun observeLiveData() {
        viewModel.tasks.observe(viewLifecycleOwner, Observer {tasks->

            tasks?.let {
                myAdapter = DayRecyclerAdapter(tasks,false,this)
                recycler_view_friday.adapter = myAdapter
                list = it as ArrayList<Task>
                println("observe livedaataa friday")


            }

        })
    }

    override fun onItemClick(position: Int) {
        val uuid = list[position].uuid
        println("friday listener : "+uuid)
        println("friday listener : "+position)
        viewModel.deleteItem(uuid)
        list.removeAt(position)
        myAdapter.notifyItemRemoved(position)
    }

}