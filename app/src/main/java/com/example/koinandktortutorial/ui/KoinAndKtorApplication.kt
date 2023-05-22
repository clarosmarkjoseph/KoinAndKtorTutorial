package com.example.koinandktortutorial.ui

import android.app.Application
import com.example.koinandktortutorial.domain.di.activityModule
import com.example.koinandktortutorial.domain.di.appModules
import com.example.koinandktortutorial.domain.di.ktorModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class KoinAndKtorApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@KoinAndKtorApplication)
            modules(appModules, ktorModule)
        }
    }
}