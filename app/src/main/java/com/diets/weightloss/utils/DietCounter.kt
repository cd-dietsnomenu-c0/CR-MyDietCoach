package com.diets.weightloss.utils

import android.util.Log
import com.diets.weightloss.POJOS.schema.Schema

object DietCounter {

    fun count(list : List<Schema>){
        var counter = 0
        for (i in list.indices){
            counter += list[i].reverseDietIndexes.size
        }
        Log.e("LOL", "Diets -- $counter")
    }
}