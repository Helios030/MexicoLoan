package com.neutron.mexicoloan.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class MainAdapter(fragmentManager: FragmentManager,fragmentList:List<Fragment>): FragmentPagerAdapter(fragmentManager) {

    var fm: FragmentManager? = null
    var fragmentList :List<Fragment>?=null
    init {
        this.fm = fragmentManager
        this.fragmentList=fragmentList
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList!![position]
    }

    override fun getCount(): Int {
        return fragmentList!!.size
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }

    override fun getItemId(position: Int): Long {
        return fragmentList!![position]!!.hashCode().toLong()
    }







}