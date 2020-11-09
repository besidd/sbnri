package com.app.sbnri.utils

import android.app.Application
import android.content.Context
import com.app.sbnri.di.DaggerMainComponent

class SBNRIApplication : Application() {
    companion object {
        lateinit var context: Context
        lateinit var app: Application
        val appComponent = DaggerMainComponent.create()
    }
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        app = this
    }
}