package com.jundev.weightloss.utils.res

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.jundev.weightloss.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class TextCutActvity : AppCompatActivity(R.layout.text_cut_activity) {

    private var job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var text = getADBText()


    }

    private  fun getADBText(): String? {
        var json: String
        return try {
            var inputStream = assets.open("adb.json")
            var size = inputStream.available()
            var buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, charset("UTF-8"))
            json
        } catch (e: Exception) {
            null
        }
    }
}