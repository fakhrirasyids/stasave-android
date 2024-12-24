package com.fakhrirasyids.stasave.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.fakhrirasyids.stasave.core.BuildConfig
import com.fakhrirasyids.stasave.core.data.StasaveRepositoryImpl
import com.fakhrirasyids.stasave.core.data.local.LocalDataSource
import com.fakhrirasyids.stasave.core.data.local.LocalDataSourceImpl
import com.fakhrirasyids.stasave.core.data.local.datastore.StasavePreferences
import com.fakhrirasyids.stasave.core.data.local.room.StasaveDatabase
import com.fakhrirasyids.stasave.core.data.local.room.dao.MediaDao
import com.fakhrirasyids.stasave.core.data.local.room.dao.SavedMediaDao
import com.fakhrirasyids.stasave.core.domain.repository.StasaveRepository
import com.fakhrirasyids.stasave.core.domain.usecase.AllMediaInteractor
import com.fakhrirasyids.stasave.core.domain.usecase.AllMediaUseCase
import com.fakhrirasyids.stasave.core.domain.usecase.SavedMediaInteractor
import com.fakhrirasyids.stasave.core.domain.usecase.SavedMediaUseCase
import com.fakhrirasyids.stasave.core.domain.usecase.WhatsappUriInteractor
import com.fakhrirasyids.stasave.core.domain.usecase.WhatsappUriUseCase
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "stasave_preferences")

val dataStoreModules = module {
    single { StasavePreferences.get(androidContext().dataStore) }
}

val databaseModules = module {
    factory { get<StasaveDatabase>().mediaDao() }
    factory { get<StasaveDatabase>().savedMediaDao() }
    single {
        val passphrase: ByteArray =
            SQLiteDatabase.getBytes(BuildConfig.STASAVE_DATABASE_KEY.toCharArray())
        val factory = SupportFactory(passphrase)

        Room.databaseBuilder(
            androidContext(),
            StasaveDatabase::class.java, BuildConfig.DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}

val dataSourceModules = module {
    factory<LocalDataSource> {
        LocalDataSourceImpl(
            get<MediaDao>(),
            get<SavedMediaDao>(),
            get<StasavePreferences>(),
        )
    }
}

val repositoryModules = module {
    single<StasaveRepository> { StasaveRepositoryImpl(get<LocalDataSource>()) }
}

val useCaseModules = module {
    factory<AllMediaUseCase> { AllMediaInteractor(get<StasaveRepository>()) }
    factory<SavedMediaUseCase> { SavedMediaInteractor(get<StasaveRepository>()) }
    factory<WhatsappUriUseCase> { WhatsappUriInteractor(get<StasaveRepository>()) }
}