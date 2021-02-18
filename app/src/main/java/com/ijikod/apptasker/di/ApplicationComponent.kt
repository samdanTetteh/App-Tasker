package com.ijikod.apptasker.di

import android.content.Context
import com.ijikod.apptasker.AppTaskerApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

/**
 * Main component for the application
 *
 */

@Singleton
@Component (
   modules = arrayOf(
       ApplicationModule::class
   )
)
interface ApplicationComponent : AndroidInjector<AppTaskerApplication> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context) : ApplicationComponent
    }

}