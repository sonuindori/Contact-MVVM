package com.rdp.contactapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rdp.contactapp.view.allcontactFragment
import com.rdp.contactapp.view.businesscontactFragment
import com.rdp.contactapp.view.persoalcontactFragment

private const val NUM_TABS = 3

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return allcontactFragment()
            1 -> return persoalcontactFragment()
        }
        return businesscontactFragment()
    }
}