package com.ijikod.apptasker.util

import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

object Extentions {

    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }


    fun View.setupSnackBar(
        lifecycleOwner: LifecycleOwner,
        snackBarEvent: LiveData<Int>,
        timeLength: Int
    ) {
        snackBarEvent.observe(lifecycleOwner, {
            showSnackBar(context.getString(it), timeLength)
        })
    }

    fun View.showSnackBar(text: String, timeLength: Int){
        Snackbar.make(this, text, timeLength).show()
    }

}