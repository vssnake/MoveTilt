package com.uratxe.pokedex

import android.app.Application
import com.unatxe.commons.data.EitherCallAdapterFactory
import com.unatxe.commons.utils.PreferencesManager
import com.uratxe.common.DataSourceTypes
import com.uratxe.pokedex.data.*
import com.uratxe.pokedex.data.services.PokemonService
import com.uratxe.pokedex.features.list.presentation.PokemonListVM
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class PokedexApp: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()

        PreferencesManager.init(this)
    }

    private fun initKoin(){
        startKoin {
            androidLogger()
            androidContext(this@PokedexApp)
            modules(listOf(appModule,pokemonModule))
        }
    }

    val appModule = module {
        single { OkHttpClient.Builder().build() }
        single {
            Retrofit.Builder()
                .client(get())
                .baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(EitherCallAdapterFactory())
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
        }
    }

    val pokemonModule = module {
        single { (get() as Retrofit).create(PokemonService::class.java) }
        single<PokemonDataSource>(named(DataSourceTypes.API)) { PokemonApiDataSource(get()) }
        single<PokemonDataSource>(named(DataSourceTypes.DB)) { PokemonDBDataSource() }
        single { PokemonRepository(get(named(DataSourceTypes.API)), get(named(DataSourceTypes.DB))) }
        viewModel { PokemonListVM(get(),get()) }
    }


}