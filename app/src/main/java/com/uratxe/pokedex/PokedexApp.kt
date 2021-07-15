package com.uratxe.pokedex

import android.app.Application
import com.unatxe.commons.utils.PreferencesManager
import com.uratxe.animelist.features.list.data.AnimeApiDataSource
import com.uratxe.animelist.features.list.data.AnimeDBDatasource
import com.uratxe.animelist.features.list.data.AnimeDataSource
import com.uratxe.animelist.features.list.data.AnimeRepository
import com.uratxe.pokedex.data.*
import com.uratxe.pokedex.features.list.presentation.PokemonListVM
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
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
        single {
            OkHttpClient.Builder().build()
        }
        single {
            Retrofit.Builder()
                .callFactory(OkHttpClient.Builder().build())
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
        }
    }

    val DB = "DB"
    val API = "API"

    val pokemonModule = module {
        single { (get() as Retrofit).create(PokemonService::class.java) }
        single<PokemonDataSource>(named(API)) { PokemonApiDataSource(get()) }
        single<PokemonDataSource>(named(DB)) { PokemonDBDataSource() }
        single { PokemonRepository(get(named(API)), get(named(DB))) }
        viewModel { PokemonListVM(androidApplication(),get()) }
    }


}