package com.ijikod.apptasker.di

import android.content.Context
import androidx.room.Room
import com.ijikod.apptasker.data.persistance.AppTaskerDataBase
import com.ijikod.apptasker.data.persistance.DATABASE_NAME
import com.ijikod.apptasker.data.repository.Repository
import com.ijikod.apptasker.data.repository.TasksRepository
import com.ijikod.apptasker.data.source.TaskDataSource
import com.ijikod.apptasker.data.source.local.TasksLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton

@Module(includes = arrayOf(ApplicationModuleBinds::class))
object ApplicationModule {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class LocalDataSource

    @JvmStatic
    @Singleton
    @LocalDataSource
    @Provides
    fun provideTasksLocalDataSource(
        dataBase: AppTaskerDataBase,
        ioDispatcher: CoroutineDispatcher
    ): TaskDataSource {
        return TasksLocalDataSource(
            dataBase.taskDao(),
            ioDispatcher
        )
    }


    @JvmStatic
    @Singleton
    @Provides
    fun provideDataBae(context: Context): AppTaskerDataBase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppTaskerDataBase::class.java,
            DATABASE_NAME
        ).build()
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideDispatcher() = Dispatchers.IO
}

@Module
abstract class ApplicationModuleBinds {

    @Singleton
    @Binds
    abstract fun bindRepository(repository: TasksRepository) : Repository
}

