package com.kuro.chitchat.di

import com.kuro.chitchat.ChitChatDatabase
import com.kuro.chitchat.ChitChatDatabase.Companion.Schema
import com.kuro.chitchat.data.database.DatabaseDriverFactory
import com.kuro.chitchat.data.entity.Users
import com.kuro.chitchat.data.repository.UserDataSourceImpl
import com.kuro.chitchat.domain.repository.UserDataSource
import com.kuro.chitchat.util.Constants.DB_USER_NAME
import com.kuro.chitchat.util.Constants.DB_USER_PASSWORD
import com.kuro.chitchat.util.Constants.DRIVER_CLASS_NAME
import com.kuro.chitchat.util.Constants.JDBC_URL
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.dsl.module

val koinModule = module {
    single { DatabaseDriverFactory() }
    single { get<DatabaseDriverFactory>().createDriver() }
    single { ChitChatDatabase(get()).apply { Schema.create(get()) } }
    single { get<ChitChatDatabase>().serverDatabaseQueries }
    single<UserDataSource> { UserDataSourceImpl(get()) }
}