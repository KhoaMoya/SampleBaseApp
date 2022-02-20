package com.moya.samplebaseapp

import com.google.android.play.core.splitcompat.SplitCompatApplication
import com.moya.logging.Logger
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SampleApplication : SplitCompatApplication() {

    override fun onCreate() {
        super.onCreate()

        Logger.initLogger(BuildConfig.DEBUG)
    }

}