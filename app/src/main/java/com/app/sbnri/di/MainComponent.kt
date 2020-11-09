package com.app.sbnri.di

import com.app.sbnri.repositories.AppRepository
import com.app.sbnri.ui.MainActivity
import com.app.sbnri.viewmodels.AppViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [MainModule::class])
interface MainComponent {

    fun inject(activity: MainActivity)

    fun inject(viewmodel: AppViewModel)

    fun inject(repository: AppRepository)

}