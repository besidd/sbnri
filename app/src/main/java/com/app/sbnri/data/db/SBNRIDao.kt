package com.app.sbnri.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.sbnri.data.models.GitDataItem

@Dao
interface SBNRIDao {
    @Query("SELECT * FROM gitdataitem")
    fun getAllSavedData(): LiveData<MutableList<GitDataItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveData(item: List<GitDataItem>)

    @Query("DELETE FROM gitdataitem")
    fun dropData()

}