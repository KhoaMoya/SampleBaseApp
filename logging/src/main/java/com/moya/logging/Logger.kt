package com.moya.logging

import timber.log.Timber

object Logger {

    fun initLogger(isDebug: Boolean) {
        if (isDebug) {
            Timber.plant(DebugLogger())
        }
    }

    inline fun d(message: String, throwable: Throwable? = null) = Timber.d(throwable, message)

    inline fun i(message: String, throwable: Throwable? = null) = Timber.i(throwable, message)

    inline fun w(message: String, throwable: Throwable? = null) = Timber.w(throwable, message)

    inline fun e(message: String, throwable: Throwable? = null) = Timber.e(throwable, message)

    inline fun v(message: String, throwable: Throwable? = null) = Timber.v(throwable, message)

    private class DebugLogger : Timber.DebugTree() {
        override fun createStackElementTag(element: StackTraceElement): String {
            return String.format(
                "%s: %s, %s",
                super.createStackElementTag(element),
                element.methodName,
                element.lineNumber
            )
        }
    }

}