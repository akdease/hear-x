package com.hearx.app.config

import com.hearx.app.activities.MainActivity
import com.hearx.app.activities.ResultsActivity
import com.hearx.app.activities.TestActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(testActivity: TestActivity)
    fun inject(resultsActivity: ResultsActivity)
}