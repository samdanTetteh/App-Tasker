package com.ijikod.apptasker.data.persistance

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ijikod.apptasker.data.models.Task

/**
 * The Room database for this app
 *
 */
@Database(entities = arrayOf(Task::class), version = 1)
abstract class AppTaskerDataBase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    companion object {

        // for singleton access to the database
        @Volatile
        private var instance: AppTaskerDataBase? = null

        fun getInstance(context: Context): AppTaskerDataBase {
            return instance ?: synchronized(this) {
                instance ?: buildAppTaskerDataBase(context).also {
                    instance = buildAppTaskerDataBase(context)
                }
            }
        }


        private fun buildAppTaskerDataBase(context: Context): AppTaskerDataBase {
            return Room.databaseBuilder(context, AppTaskerDataBase::class.java, DATABASE_NAME)
                .build()
        }

    }

}

const val DATABASE_NAME = "AppTasker.db"