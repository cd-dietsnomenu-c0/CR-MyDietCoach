package com.wsoteam.mydietcoach.tracker.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class DayAdapter(var count: Int, val numbersLosesDays: MutableList<Int>, val currentDay : Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var residue = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return DayVH(inflater, parent)
    }

    override fun getItemCount(): Int {
        residue = count % 5
        var count = count / 5
        if (residue > 0){
            count += 1
        }
        return count
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as DayVH).bind(getSpanCount(position), getDayState(position))
    }

    private fun getDayState(position: Int): Any {
        var states = mutableListOf<Int>(DayConfig.UNUSED, DayConfig.UNUSED, DayConfig.UNUSED, DayConfig.UNUSED, DayConfig.UNUSED)
        var max = (position + 1) * 5
        var min = max - 4
        for (i in numbersLosesDays.indices){
            if (numbersLosesDays[i] in min..max){
                states[numbersLosesDays[i]] = DayConfig.LOSE
            }
        }
        if (currentDay in (min - 1)..max){
            for (i in (min - 1) until currentDay){
                if (states[i] != DayConfig.LOSE){
                    states[i] = DayConfig.CHECKED
                }
            }
            for (i in (currentDay + 1)..max){
                states[i] = DayConfig.UNUSED
            }
        }else if (currentDay < min -1){
            for (i in states.indices){
                states[i] = DayConfig.UNUSED
            }
        }else if (currentDay > max){
            for (i in states.indices){
                if (states[i] != DayConfig.UNUSED){
                    states[i] = DayConfig.CHECKED
                }
            }
        }
    }

    private fun getSpanCount(position: Int): Int {
        if (position < itemCount - 1){
            return 5
        }else if (residue > 0){
            return residue
        }else {
            return 0
        }
    }
}