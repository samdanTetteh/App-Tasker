package com.ijikod.apptasker.util

import java.text.SimpleDateFormat
import java.util.*

object Extentions {

    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String{
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }
}