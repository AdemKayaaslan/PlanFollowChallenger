package com.mustafaademkayaaslan.planfollowchallenger.adapter

import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mustafaademkayaaslan.planfollowchallenger.view.*

class ViewPagerAdapter(
    fragment: TaskAddActivity

): FragmentStateAdapter(fragment) {
    private lateinit var myFragment:Fragment
    inner class ViewPagerViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)

    override fun getItemCount(): Int { return 7 }

    override fun createFragment(position: Int): Fragment {


        when (position) {
            0 -> myFragment = MondayFragment()
            1 -> myFragment = TuesdayFragment()
            2 -> myFragment = WednesdayFragment()
            3 -> myFragment = ThursdayFragment()
            4 -> myFragment = FridayFragment()
            5 -> myFragment = SaturdayFragment()
            6 -> myFragment = SundayFragment()

        }
        return myFragment
    }

}