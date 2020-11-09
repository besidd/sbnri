package com.app.sbnri.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.app.sbnri.data.models.GitDataItem
import com.app.sbnri.utils.Constants
import javax.inject.Inject

@Database(
    entities = [GitDataItem::class],
    version = 1
)
@TypeConverters(Converters::class)

abstract class SBNRIDatabase : RoomDatabase() {

    abstract fun getSbnriDao(): SBNRIDao

    companion object {
        private var instance: SBNRIDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDataBase(context).also {
                instance = it
            }
        }

        private fun createDataBase(context: Context): SBNRIDatabase = Room.databaseBuilder(
            context.applicationContext,
            SBNRIDatabase::class.java,
            Constants.DATABASE_NAME
        ).build()

    }

}