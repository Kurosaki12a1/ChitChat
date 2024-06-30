package com.kuro.chitchat.data.entity

import com.kuro.chitchat.util.Constants.DATABASE_NAME
import org.jetbrains.exposed.sql.Table

object Users : Table(DATABASE_NAME) {
    val id = text("id")
    val name = text("name")
    val emailAddress = text("emailAddress").uniqueIndex()
    val profilePhoto = text("profilePhoto").nullable()

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}
