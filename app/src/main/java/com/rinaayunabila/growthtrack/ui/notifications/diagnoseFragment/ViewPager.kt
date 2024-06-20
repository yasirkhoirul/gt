package com.rinaayunabila.growthtrack.ui.notifications.diagnoseFragment

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPager (activity: AppCompatActivity): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {

        return 4
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = DangerFragment()
            1 -> fragment = FoodFragment()
            2 -> fragment = PrinsipFragment()
            3 -> fragment = AngkaKecukupanGizi()
        }
        return fragment as Fragment
    }
}