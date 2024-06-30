package com.kuro.chitchat.data.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.asJdbcDriver
import com.kuro.chitchat.util.Constants.DB_USER_NAME
import com.kuro.chitchat.util.Constants.DB_USER_PASSWORD
import com.kuro.chitchat.util.Constants.DRIVER_CLASS_NAME
import com.kuro.chitchat.util.Constants.JDBC_URL
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource

class DatabaseDriverFactory {
    fun createDriver(): SqlDriver {
        val config = HikariConfig().apply {
            setJdbcUrl(JDBC_URL)
            driverClassName = DRIVER_CLASS_NAME
            username = DB_USER_NAME
            password = DB_USER_PASSWORD
        }
        val dataSource = HikariDataSource(config)
        return dataSource.asJdbcDriver()
    }
}