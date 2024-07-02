package com.kuro.chitchat.di

import com.kuro.chitchat.data.repository.UserDataSourceImpl
import com.kuro.chitchat.domain.repository.UserDataSource
import com.kuro.chitchat.util.Constants.DATABASE_NAME
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val koinModule = module {
    single {
        KMongo.createClient()
            .coroutine
            .getDatabase(DATABASE_NAME)
    }
    single<UserDataSource> { UserDataSourceImpl(get()) }
}