package com.example.project_umbrella

import android.app.Application
import com.example.project_umbrella.data.AppContainer
import com.example.project_umbrella.data.AppDataContainer

class UmbrellaApplication: Application() {

    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}