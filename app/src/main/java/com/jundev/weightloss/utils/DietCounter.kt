package com.jundev.weightloss.utils

import android.util.Log
import com.jundev.weightloss.model.schema.Schema

object DietCounter {

    fun count(list : List<Schema>){
        var counter = 0
        for (i in list.indices){
            counter += list[i].reverseDietIndexes.size
        }
        Log.e("LOL", "Diets -- $counter")
    }
}