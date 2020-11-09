package com.app.sbnri.di

import android.app.Application
import android.content.Context
import com.app.sbnri.api.ISBNRIApi
import com.app.sbnri.data.db.SBNRIDatabase
import com.app.sbnri.repositories.AppRepository
import com.app.sbnri.utils.Constants
import com.app.sbnri.utils.SBNRIApplication
import com.app.sbnri.utils.SBNRIApplication.Companion.app
import com.app.sbnri.viewmodels.AppViewModel
import com.app.sbnri.viewmodels.ViewModelFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class MainModule {

    @Singleton
    @Provides
    fun providesHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient
            .Builder()
            .addInterceptor(logging)
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun providesGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun providesRetrofitInstance(client: OkHttpClient, factory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder().client(client)
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(factory)
            .build()

    }

    @Singleton
    @Provides
    fun providesRetrofitClient(retrofit: Retrofit) : ISBNRIApi {
        return retrofit.create(ISBNRIApi::class.java)
    }

    @Singleton
    @Provides
    fun providesAppContext(): Application = SBNRIApplication.app

    @Singleton
    @Provides
    fun providesRepository (app: Application): AppRepository = AppRepository(SBNRIDatabase(app))


    @Singleton
    @Provides
    fun providesViewModelFactory(app: Application) = ViewModelFactory(app)

    @Singleton
    @Provides
    fun providesViewModel (app: Application) = AppViewModel(app)
}