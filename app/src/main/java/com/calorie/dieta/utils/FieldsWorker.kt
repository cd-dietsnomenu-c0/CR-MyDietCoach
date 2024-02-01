package com.calorie.dieta.utils

object FieldsWorker {

    fun isCorrect(text: String): Boolean {
        var string = text.replace("\\\\s+".toRegex(), "")
        return string != ""
    }
}