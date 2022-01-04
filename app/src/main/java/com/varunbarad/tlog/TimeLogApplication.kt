package com.varunbarad.tlog

import android.app.Application
import com.varunbarad.tlog.util.Dependencies

class TimeLogApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Initialize database
        Dependencies.getTimeLogDatabase(this)
    }
}
