package com.app.sbnri.repositories

import android.util.Log
import android.util.Pair
import androidx.annotation.Nullable
import androidx.lifecycle.LiveData
import com.app.sbnri.api.ISBNRIApi
import com.app.sbnri.data.db.SBNRIDatabase
import com.app.sbnri.data.models.GitDataItem
import com.app.sbnri.utils.SBNRIApplication
import javax.inject.Inject

class AppRepository(val db: SBNRIDatabase) {

    @Inject
    @Nullable
    lateinit var api: ISBNRIApi

    init {
        SBNRIApplication.appComponent.inject(this)
    }

    suspend fun getApiData(pageNumber: Int): MutableList<GitDataItem> = api.getData(pageNumber)

    suspend fun saveData(list: MutableList<GitDataItem>) = db.getSbnriDao().saveData(list)

    fun getSavedData(): LiveData<MutableList<GitDataItem>> {
        return db.getSbnriDao().getAllSavedData()
    }

}