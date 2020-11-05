package com.app.sbnri.di

import com.app.sbnri.ui.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [MainModule::class])
interface MainComponent {

    fun inject(activity: MainActivity)

}