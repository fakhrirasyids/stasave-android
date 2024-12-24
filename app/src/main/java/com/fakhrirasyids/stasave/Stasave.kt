package com.fakhrirasyids.stasave

import android.app.Application
import com.fakhrirasyids.stasave.core.di.dataSourceModules
import com.fakhrirasyids.stasave.core.di.dataStoreModules
import com.fakhrirasyids.stasave.core.di.databaseModules
import com.fakhrirasyids.stasave.core.di.repositoryModules
import com.fakhrirasyids.stasave.core.di.useCaseModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Stasave: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@Stasave)
            modules(
                dataStoreModules,
                databaseModules,
                dataSourceModules,
                repositoryModules,
                useCaseModules,
            )
        }
    }
}