package com.example.monstarlabs_lab5

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.monstarlabs_lab5.HandingDate.TOTAL_MONTH
import com.example.monstarlabs_lab5.HandingDate.monthGlobal
import com.example.monstarlabs_lab5.HandingDate.yearGlobal
import com.example.monstarlabs_lab5.adapter.PagerAdapter
import com.example.monstarlabs_lab5.databinding.ActivityMainBinding
import com.example.monstarlabs_lab5.fragment.January
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var adapterPager: PagerAdapter
    private val haftCount = TOTAL_MONTH / 2

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        adapterPager = PagerAdapter(supportFragmentManager)
        binding.viewpager2.adapter = adapterPager
        binding.viewpager2.currentItem = TOTAL_MONTH / 2
        binding.viewpager2.setPageTransformer(true, ZoomOutPageTransformer())
        binding.viewpager2.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            @SuppressLint("SetTextI18n")
            override fun onPageSelected(position: Int) {

                when (getMonth(position)) {
                    1 -> binding.tvMonth.text = "January"
                    2 -> binding.tvMonth.text = "February"
                    3 -> binding.tvMonth.text = "March"
                    4 -> binding.tvMonth.text = "April"
                    5 -> binding.tvMonth.text = "May"
                    6 -> binding.tvMonth.text = "June"
                    7 -> binding.tvMonth.text = "July"
                    8 -> binding.tvMonth.text = "August"
                    9 -> binding.tvMonth.text = "September"
                    10 -> binding.tvMonth.text = "October"
                    11 -> binding.tvMonth.text = "November"
                    else -> binding.tvMonth.text = "December"
                }
                binding.tvTextYear.text = getYear(position).toString()
                var january = (binding.viewpager2.adapter as PagerAdapter).instantiateItem(
                    binding.viewpager2,
                    position
                ) as January
                january.reload()
            }

            override fun onPageScrollStateChanged(state: Int) {}

        })
        binding.tvMonth.text = SimpleDateFormat("MMMM").format(Calendar.getInstance().time)
        binding.tvTextYear.text = SimpleDateFormat("yyyy").format(Calendar.getInstance().time)
        binding.tvDateNow.text =
            SimpleDateFormat("EEEE dd/MMMM/yyyy").format(Calendar.getInstance().time)
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
                return if (range >= 12 - HandingDate.monthGlobal) {
                    val x = range + HandingDate.monthGlobal
                    when {
                        x <= 12 -> x
                        else -> x % 12
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
                    return when {
                        x % 12 == 0 -> yearGlobal + (x / 12) - 1
                        else -> yearGlobal + (x / 12)
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