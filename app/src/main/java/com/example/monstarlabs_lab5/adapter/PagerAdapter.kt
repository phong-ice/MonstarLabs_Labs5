package com.example.monstarlabs_lab5.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.monstarlabs_lab5.HandingDate
import com.example.monstarlabs_lab5.fragment.January

class PagerAdapter(fa: FragmentManager) :
    FragmentStatePagerAdapter(
        fa,
        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
    ) {
    override fun getCount(): Int {
        return HandingDate.TOTAL_MONTH
    }

    override fun getItem(position: Int): Fragment {
        return January(position)
    }

}