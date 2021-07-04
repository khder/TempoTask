package com.tempo.task.utils

import android.content.Context
import android.net.ConnectivityManager

object NetworkUtils {
    fun isNetworkAvailable(context: Context):Boolean{
        val nInfo = (context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
        return nInfo != null && nInfo.isAvailable && nInfo.isConnected
    }
}