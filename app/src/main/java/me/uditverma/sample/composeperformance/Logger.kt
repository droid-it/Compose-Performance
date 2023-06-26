package me.uditverma.sample.composeperformance

import android.util.Log

object Logger {
    private const val TAG = "ComposePerformance"

    fun d(message: String, filter: LogFilter? = null) {
        Log.d("$TAG-${filter ?: ""}", message)
    }

//    fun d(data: Any?) {
//        Log.d(TAG, "object is : ${data?.toString()}")
//    }
}

enum class LogFilter {
    ReAllocation,
    Recomposition
}