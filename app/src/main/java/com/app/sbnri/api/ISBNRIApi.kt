package com.app.sbnri.api

import androidx.lifecycle.LiveData
import com.app.sbnri.data.models.GitData
import com.app.sbnri.data.models.GitDataItem
import com.app.sbnri.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface ISBNRIApi {

    @GET ("orgs/octokit/repos")
    suspend fun getData(@Query("page") pageNumber: Int, @Query ("per_page") count: Int = Constants.dataCount ): MutableList<GitDataItem>

}