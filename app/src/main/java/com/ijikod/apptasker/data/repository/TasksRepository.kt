package com.ijikod.apptasker.data.repository

import com.ijikod.apptasker.data.source.persistance.TasksLocalDataSource
import com.ijikod.apptasker.di.ApplicationModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class TasksRepository @Inject constructor (
    @ApplicationModule.LocalDataSource private val localDataSource: TasksLocalDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : Repository {


}