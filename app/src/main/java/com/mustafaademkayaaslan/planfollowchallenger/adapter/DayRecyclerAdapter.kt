package com.mustafaademkayaaslan.planfollowchallenger.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.mustafaademkayaaslan.planfollowchallenger.R
import com.mustafaademkayaaslan.planfollowchallenger.model.Task
import kotlinx.android.synthetic.main.day_task_item.view.*

class DayRecyclerAdapter(private val taskList:List<Task>,
                         private val isCheckBox:Boolean,
                         private val listener : OnItemClickListener) : RecyclerView.Adapter<DayRecyclerAdapter.DayRecyclerViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayRecyclerViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.day_task_item,parent,false)

        return DayRecyclerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DayRecyclerViewHolder, position: Int) {

        val currentItem = taskList[position]

        holder.textView1.text = ""+currentItem.name
        holder.textView2.text = currentItem.day + "  " + currentItem.startTime + " - " + currentItem.stopTime



        if (isCheckBox) {
            holder.checkBox.visibility =View.VISIBLE
            holder.delete.visibility = View.GONE
            if (taskList[position].isDone) {
                holder.checkBox.isChecked=true
                println("isDoneeee : " + taskList[position].isDone)
            }
        } else {
            holder.checkBox.visibility = View.GONE
            holder.delete.visibility = View.VISIBLE
        }

    }

    override fun getItemCount() = taskList.size

    inner class DayRecyclerViewHolder(itemView:View):RecyclerView.ViewHolder(itemView),View.OnClickListener {

        val imageView:ImageView = itemView.image_view
        val textView1 = itemView.text_view_1
        val textView2 = itemView.text_view_2
        val checkBox = itemView.checkBoxDayTask
        val delete = itemView.imageViewDelete


        init {
            delete.setOnClickListener(this)
            checkBox.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position=adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
                println("if viewholder")
            }

        }

    }

    interface OnItemClickListener {
        fun onItemClick (position: Int)
    }
}