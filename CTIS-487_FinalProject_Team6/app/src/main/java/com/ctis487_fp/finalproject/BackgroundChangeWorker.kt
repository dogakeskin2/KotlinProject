package com.ctis487_fp.finalproject

import android.content.Context
import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

class BackgroundChangeWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val index = inputData.getInt("index", -1)

        val color = when (index) {
            0 -> ContextCompat.getColor(applicationContext, R.color.soft_red)
            1 -> ContextCompat.getColor(applicationContext, R.color.soft_purple)
            2 -> ContextCompat.getColor(applicationContext, R.color.soft_blue)
            3 -> ContextCompat.getColor(applicationContext, R.color.soft_green)
            else -> ContextCompat.getColor(applicationContext, R.color.soft_yellow)
        }

        saveBackgroundColor(color)

        return Result.success()
    }


    private fun saveBackgroundColor(color: Int) {
        val sharedPref = applicationContext.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        sharedPref.edit().putInt("background_color", color).apply()
    }
}
