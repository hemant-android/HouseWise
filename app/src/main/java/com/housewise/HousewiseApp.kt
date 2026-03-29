package com.housewise

import android.app.Application
import com.housewise.core.utils.SessionManager

class HousewiseApp : Application() {
    
    companion object {
        lateinit var sessionManager: SessionManager
            private set
    }

    override fun onCreate() {
        super.onCreate()
        // Initialize SessionManager globally
        sessionManager = SessionManager(this)
    }
}