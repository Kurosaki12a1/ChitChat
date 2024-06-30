package com.kuro.chitchat.di

import com.kuro.chitchat.ChitChatDatabase
import com.kuro.chitchat.ChitChatDatabase.Companion.Schema
import com.kuro.chitchat.data.database.DatabaseDriverFactory
import com.kuro.chitchat.data.repository.UserDataSourceImpl
import com.kuro.chitchat.domain.repository.UserDataSource
import org.koin.dsl.module

val koinModule = module {
    single { DatabaseDriverFactory() }
    single { get<DatabaseDriverFactory>().createDriver() }
    single { ChitChatDatabase(get()).apply { Schema.create(get()) } }
    single { get<ChitChatDatabase>().serverDatabaseQueries }
    single<UserDataSource> { UserDataSourceImpl(get()) }
}