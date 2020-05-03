package com.wsoteam.mydietcoach.common

import com.wsoteam.mydietcoach.POJOS.Global

object GlobalHolder {

    private lateinit var global : Global

    fun setGlobal(global: Global){
        this.global = global
    }

    fun getGlobal() : Global{
        return global
    }


}