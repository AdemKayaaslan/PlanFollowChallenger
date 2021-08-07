package com.mustafaademkayaaslan.planfollowchallenger.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.mustafaademkayaaslan.planfollowchallenger.R
import com.mustafaademkayaaslan.planfollowchallenger.adapter.DayRecyclerAdapter
import com.mustafaademkayaaslan.planfollowchallenger.model.Task
import com.mustafaademkayaaslan.planfollowchallenger.viewmodel.MondayViewModel
import kotlinx.android.synthetic.main.monday_fragment.*
import java.util.zip.Inflater

class MondayFragment : Fragment(),DayRecyclerAdapter.OnItemClickListener,View.OnClickListener {

    private lateinit var fragmentSaveButton:Button
    private lateinit var viewModel: MondayViewModel
    var list = ArrayList<Task>()
    private lateinit var myAdapter:DayRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.monday_fragment, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MondayViewModel::class.java)




        viewModel.getMondayTasks()
        observeLiveData()

        recycler_view_task_monday.layoutManager = LinearLayoutManager(this.context)
        recycler_view_task_monday.setHasFixedSize(true)

    }

    fun observeLiveData() {
        viewModel.tasks.observe(viewLifecycleOwner, Observer {tasks->

            tasks?.let {
                myAdapter = DayRecyclerAdapter(tasks,false,this)
                recycler_view_task_monday.adapter = myAdapter
                myAdapter.notifyDataSetChanged()
                println("observe livedaataa monday"+it)
                list = tasks as ArrayList<Task>

            }

        })
    }

    override fun onItemClick(position: Int) {
        val uuid = list[position].uuid
        println("monday listener"+uuid)
        println("monday listener"+position)
        viewModel.deleteItem(uuid)
        list.removeAt(position)
        myAdapter.notifyItemRemoved(position)

    }

    override fun onClick(v: View?) {
println("fragment on click nwqqq")
    }


}