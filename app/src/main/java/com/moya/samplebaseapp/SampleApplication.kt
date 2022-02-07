package com.moya.samplebaseapp

import android.app.Application
import com.moya.logging.Logger
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Logger.initLogger(BuildConfig.DEBUG)
    }

}