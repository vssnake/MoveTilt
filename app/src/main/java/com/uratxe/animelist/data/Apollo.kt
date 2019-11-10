package com.uratxe.animelist.data

import com.apollographql.apollo.ApolloClient
import okhttp3.OkHttpClient


object Apollo {


    private val clienUrl = "https://graphql.anilist.co"

    private val okHttpClient by lazy{ OkHttpClient() }

    val apolloClient by lazy {ApolloClient.builder()
        .serverUrl(clienUrl)
        .okHttpClient(okHttpClient)
        .build()}

}