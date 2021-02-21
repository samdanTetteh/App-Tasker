package com.ijikod.apptasker

import com.ijikod.apptasker.di.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

open class AppTaskerApplication: DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return  DaggerApplicationComponent.factory().create(applicationContext)
    }
}