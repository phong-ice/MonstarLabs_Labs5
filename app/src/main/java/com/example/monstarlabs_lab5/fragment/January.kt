package com.example.monstarlabs_lab5.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.monstarlabs_lab5.CommunicationAdapterWeek
import com.example.monstarlabs_lab5.HandingDate
import com.example.monstarlabs_lab5.HandingDate.sortListDateMatchesWeek
import com.example.monstarlabs_lab5.HandingDate.week
import com.example.monstarlabs_lab5.HandingDate.yearGlobal
import com.example.monstarlabs_lab5.R
import com.example.monstarlabs_lab5.adapter.AdapterListDay
import com.example.monstarlabs_lab5.adapter.AdapterListWeek
import com.example.monstarlabs_lab5.databinding.FragmentJanuaryBinding
import java.text.SimpleDateFormat
import java.util.*

class January(val pos: Int) : Fragment(), CommunicationAdapterWeek {

    private val haftCount = HandingDate.TOTAL_MONTH / 2

    private val binding: FragmentJanuaryBinding by lazy {
        FragmentJanuaryBinding.inflate(
            layoutInflater
        )
    }
    private lateinit var adapterWeek: AdapterListWeek
    private lateinit var listWeek: MutableList<String>
    private lateinit var dates: MutableList<Date>
    private lateinit var datesByMonth: MutableList<Date>
    private lateinit var datesByMonthUseForAdapter: MutableList<Date>
    private lateinit var adapterListDay: AdapterListDay


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        listWeek = HandingDate.week
        dates = HandingDate.getCalendarByYear(getYear(pos))
        var month = when {
            getMonth(pos) < 10 -> "0${getMonth(pos)}"
            else -> "${getMonth(pos)}"
        }
        Log.i("test123","$month - ${getYear(pos)}")
        datesByMonth = HandingDate.getDaysByMonth(month, dates)
        datesByMonthUseForAdapter = HandingDate.getDaysByMonth(month, dates)
        adapterWeek = AdapterListWeek(this, listWeek)
        binding.lvWeek.apply {
            layoutManager = GridLayoutManager(requireContext(), 7)
            adapter = adapterWeek
        }

        makeDayMatchesWeek()
        adapterListDay = AdapterListDay(datesByMonthUseForAdapter, month)
        binding.lvDay.apply {
            adapter = adapterListDay
            layoutManager = GridLayoutManager(requireContext(), 7)
        }

    }

    override fun SortWeek(tv: String) {
        val weeks = HandingDate.week
        val weekOder: MutableList<String> = mutableListOf()
        val index = weeks.indexOf(tv)
        for (i in index until weeks.size) {
            weekOder.add(weeks[i])
        }
        if (weekOder.size != weeks.size) {
            for (week in weeks) {
                if (!weekOder.contains(week)) {
                    weekOder.add(week)
                }
            }
        }
        listWeek.clear()
        listWeek.addAll(weekOder)
        makeDayMatchesWeek()
        adapterWeek.notifyDataSetChanged()
        adapterListDay.notifyDataSetChanged()
    }

    public fun reload(){
        adapterWeek.notifyDataSetChanged()
        adapterListDay.notifyDataSetChanged()
    }

    private fun makeDayMatchesWeek() {
        datesByMonthUseForAdapter.clear()
        for (i in 0 until listWeek.size) {
            val weekOfFirtDate = SimpleDateFormat("EE").format(datesByMonth[0])
            if (listWeek[i] == weekOfFirtDate) {
                datesByMonthUseForAdapter.addAll(datesByMonth.sortListDateMatchesWeek(i))
                break
            }
        }
    }

    private fun getMonth(pos: Int): Int {
        when {
            haftCount > pos -> {
                val range = haftCount - pos
                return if (range >= HandingDate.monthGlobal) {
                    val x = range - HandingDate.monthGlobal
                    when {
                        x == 0 -> 12
                        x >= 12 -> 12 - (x % 12)
                        else -> 12 - x
                    }
                } else {
                    HandingDate.monthGlobal - range
                }
            }
            haftCount < pos -> {
                val range = pos - haftCount
                return if (range > 12 - HandingDate.monthGlobal) {
                    val x = range + HandingDate.monthGlobal
                    return when{
                        x % 12 == 0 -> 12
                        else ->x % 12
                    }
                } else {
                    HandingDate.monthGlobal + range
                }
            }
            else -> {
                return HandingDate.monthGlobal
            }
        }
    }

    private fun getYear(pos: Int): Int {
        when {
            haftCount > pos -> {
                val range = haftCount - pos
                return if (range >= HandingDate.monthGlobal) {
                    val x = range - HandingDate.monthGlobal
                    when {
                        x < 12 -> yearGlobal - 1
                        else -> yearGlobal - 1 - (x / 12)
                    }
                } else {
                    yearGlobal
                }
            }
            haftCount < pos -> {
                val range = pos - haftCount
                return if (range > 12 - HandingDate.monthGlobal) {
                    val x = range + HandingDate.monthGlobal
                   return when{
                       x % 12 == 0 -> yearGlobal + (x / 12) - 1
                       else ->  yearGlobal + (x / 12)
                   }
                } else {
                    yearGlobal
                }
            }
            else -> {
                return HandingDate.yearGlobal
            }
        }
    }

}