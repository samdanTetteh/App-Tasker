package com.ijikod.apptasker.di

import android.content.Context
import com.ijikod.apptasker.AppTaskerApplication
import com.ijikod.apptasker.di.modules.AddTaskModule
import com.ijikod.apptasker.di.modules.ApplicationModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Main component for the application
 *
 */


@Singleton
@Component (
   modules = [
       ApplicationModule::class,
       AddTaskModule::class,
       AndroidSupportInjectionModule::class
   ]
)
interface ApplicationComponent : AndroidInjector<AppTaskerApplication> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context) : ApplicationComponent
    }

}