package com.balc2013.albanote

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class GlobalApplication : Application() {
    companion object {
        var diffServerTime = 0L
        fun getCurrentTime() = System.currentTimeMillis() - diffServerTime
    }

    override fun onCreate() {
        super.onCreate()

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}