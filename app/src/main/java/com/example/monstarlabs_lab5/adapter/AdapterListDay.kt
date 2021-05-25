package com.example.monstarlabs_lab5.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.monstarlabs_lab5.CommunicationAdapterWeek
import com.example.monstarlabs_lab5.HandingDate
import com.example.monstarlabs_lab5.MainActivity
import com.example.monstarlabs_lab5.R
import com.example.monstarlabs_lab5.databinding.ItemDayNowBinding
import com.example.monstarlabs_lab5.databinding.ItemDayOfOtherMonthBinding
import com.example.monstarlabs_lab5.databinding.ItemListDayBinding
import java.text.SimpleDateFormat
import java.util.*

class AdapterListDay(var dates: MutableList<Date>, val month: String,val callback: () -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var pos: Int? = null
    var timeClick: Long = 0

    companion object {
        val TYPE_1 = 1
        val TYPE_2 = 2
        val TYPE_3 = 3
    }

    class ViewHolderDayNow(private val binding: ItemDayNowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvDay = binding.tvDay
    }

    class ViewHolderDay(private val binding: ItemListDayBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvDay = binding.tvDay
    }

    class ViewHolderDayOtherMonth(private val binding: ItemDayOfOtherMonthBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvDay = binding.tvDay
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_1 -> ViewHolderDay(
                ItemListDayBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            TYPE_2 -> ViewHolderDayNow(
                ItemDayNowBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> ViewHolderDayOtherMonth(
                ItemDayOfOtherMonthBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    @SuppressLint("ResourceAsColor", "ClickableViewAccessibility")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            TYPE_1 -> {
                val holderDayNow: ViewHolderDay = holder as ViewHolderDay
                holderDayNow.tvDay.isSelected = false
                if (HandingDate.dateFocus == SimpleDateFormat("EEEE dd/MM/yyyy").format(dates[position])) {
                    holderDayNow.tvDay.isSelected = true
                }
                holderDayNow.tvDay.text = SimpleDateFormat("dd").format(dates[position])
                holderDayNow.tvDay.setTextColor(Color.argb(255, 66, 63, 59))

                holderDayNow.tvDay.setOnClickListener {
                    if (holder.tvDay.isSelected) {
                        holderDayNow.tvDay.isSelected = false
                        HandingDate.dateFocus = ""
                        notifyDataSetChanged()
                    } else {
                        holderDayNow.tvDay.isSelected = true
                        HandingDate.dateFocus =
                            SimpleDateFormat("EEEE dd/MM/yyyy").format(dates[position])
                        notifyDataSetChanged()
                        callback()
                    }
                }
            }
            TYPE_2 -> {
                val holderDayNow: ViewHolderDayNow = holder as ViewHolderDayNow
                holderDayNow.tvDay.text = SimpleDateFormat("dd").format(dates[position])
                holderDayNow.tvDay.setTextColor(Color.argb(255, 255, 255, 255))
            }
            else -> {
                val holderDayNow: ViewHolderDayOtherMonth = holder as ViewHolderDayOtherMonth
                holderDayNow.tvDay.text = SimpleDateFormat("dd").format(dates[position])
                holderDayNow.tvDay.setTextColor(Color.argb(255, 66, 63, 59))
            }
        }
    }

    override fun getItemCount(): Int {
        return dates.size
    }

    @SuppressLint("SimpleDateFormat")
    override fun getItemViewType(position: Int): Int {
        val day = SimpleDateFormat("EEEE dd/MM/yyyy").format(dates[position])
        val monthCheck = SimpleDateFormat("MM").format(dates[position])
        val dayNow = SimpleDateFormat("EEEE dd/MM/yyyy").format(Calendar.getInstance().time)
        return when {
            day == dayNow -> TYPE_2
            month == monthCheck -> TYPE_1
            else -> TYPE_3
        }
    }

    private fun ranDomColor(): Int {
        val rnd = Random()
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    }
}