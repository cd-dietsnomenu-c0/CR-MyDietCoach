package com.wsoteam.mydietcoach.common

import com.squareup.moshi.Moshi
import com.wsoteam.mydietcoach.App
import com.wsoteam.mydietcoach.POJOS.Global

object GlobalHolder {

    private lateinit var global : Global

    fun setGlobal(global: Global){
        this.global = global
    }

    fun getGlobal() : Global{
        return if (this::global.isInitialized) {
            global
        }else{
            getAsyncDietData()!!
        }
    }

    private fun getAsyncDietData(): Global? {
        var json: String
        var moshi = Moshi.Builder().build()
        var jsonAdapter = moshi.adapter(Global::class.java)
        try {
            var inputStream = App.getContext().assets.open("adb.json")
            var size = inputStream.available()
            var buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, charset("UTF-8"))
            return jsonAdapter.fromJson(json)
        } catch (e: Exception) {
        }
        return null
    }


}