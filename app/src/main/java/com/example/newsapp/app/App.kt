package com.mohamed.prayerapp.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import com.app.data.remote.Constants
import com.example.newsapp.BuildConfig

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Constants.PrefKeys.BASE_URL = BuildConfig.BASE_URL
    }
}