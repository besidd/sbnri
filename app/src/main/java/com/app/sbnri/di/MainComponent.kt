package com.app.sbnri.di

import com.app.sbnri.ui.MainActivity
import dagger.Component

@Component(modules = [MainModule::class])
interface MainComponent {

    fun inject(activity: MainActivity)

}