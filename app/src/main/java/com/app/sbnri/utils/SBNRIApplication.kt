package com.app.sbnri.utils

import android.app.Application
import com.app.sbnri.di.DaggerMainComponent

class SBNRIApplication : Application() {
    val appComponent = DaggerMainComponent.create()
}