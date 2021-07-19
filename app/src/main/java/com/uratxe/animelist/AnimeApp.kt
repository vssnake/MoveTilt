package com.uratxe.animelist

import android.app.Application
import com.unatxe.commons.utils.PreferencesManager
import com.uratxe.animelist.data.AuthModule
import com.uratxe.animelist.features.list.AnimeListViewModel
import com.uratxe.animelist.features.list.data.AnimeApiDataSource
import com.uratxe.animelist.features.list.data.AnimeDBDatasource
import com.uratxe.animelist.features.list.data.AnimeDataSource
import com.uratxe.animelist.features.list.data.AnimeRepository
import com.uratxe.common.DataSourceTypes
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module

class AnimeApp : Application(){
    override fun onCreate() {
        super.onCreate()
        initKoin()
        PreferencesManager.init(this)
    }

    private fun initKoin(){
        startKoin {
            androidLogger()
            androidContext(this@AnimeApp)
            modules(listOf(appModule, animeModule))
        }
    }

    val appModule = module {
        single { AuthModule() }
        viewModel { AnimeListViewModel(get(), get()) }
    }

    val animeModule = module {
        single<AnimeDataSource>(named(DataSourceTypes.API)) { AnimeApiDataSource() }
        single<AnimeDataSource>(named(DataSourceTypes.DB)) { AnimeDBDatasource() }
        single { AnimeRepository(get(named(DataSourceTypes.API)), get(named(DataSourceTypes.DB))) }
    }


}