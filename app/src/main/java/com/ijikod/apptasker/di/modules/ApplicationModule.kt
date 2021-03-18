package com.ijikod.apptasker.di.modules


import android.content.Context
import androidx.room.Room
import com.ijikod.apptasker.data.repository.TaskRepository
import com.ijikod.apptasker.data.repository.TasksRepository
import com.ijikod.apptasker.data.source.TaskDataSource
import com.ijikod.apptasker.data.source.persistance.AppTaskerDataBase
import com.ijikod.apptasker.data.source.persistance.DATABASE_NAME
import com.ijikod.apptasker.data.source.persistance.TasksLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object ApplicationModule {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class LocalDataSource


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

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): AppTaskerDataBase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppTaskerDataBase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideDispatcher() = Dispatchers.IO
}


@Module
@InstallIn(ApplicationComponent::class)
object TasksRepositoryModule {

    @Singleton
    @Provides
    fun providesTasksRepository(
        @ApplicationModule.LocalDataSource localTaskDataSource: TaskDataSource,
        ioDispatcher: CoroutineDispatcher
    ): TaskRepository {
        return TasksRepository(
            localTaskDataSource,
            ioDispatcher
        )
    }
}




