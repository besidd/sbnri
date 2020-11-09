package com.app.sbnri.viewmodels

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.sbnri.R
import com.app.sbnri.data.models.GitDataItem
import com.app.sbnri.repositories.AppRepository
import com.app.sbnri.utils.SBNRIApplication
import com.app.sbnri.utils.SBNRIApplication.Companion.app
import com.app.sbnri.utils.Utils
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class AppViewModel(app: Application) : AndroidViewModel(app) {

    var data: LiveData<MutableList<GitDataItem>> = MutableLiveData()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    var pageNumber = 1

    @Inject
    lateinit var repo: AppRepository

    init {
        SBNRIApplication.appComponent.inject(this)
    }

    fun getAllData() = viewModelScope.launch {
        isLoading.postValue(true)
        if (Utils().hasInternetConnection(app)) {
            fetchData()
        } else {
            data = repo.getSavedData()
            Toast.makeText(app, app.getString(R.string.no_internet), Toast.LENGTH_SHORT).show()
        }
        isLoading.postValue(false)
    }

    private suspend fun fetchData() {
        val newData = runBlocking {
            repo.getApiData(pageNumber)
        }
        newData.let {
            if (newData.isNotEmpty()) {
               pageNumber ++
                runBlocking {
                    repo.saveData(newData)
                }
                    data = repo.getSavedData()
            }
        }
    }

}