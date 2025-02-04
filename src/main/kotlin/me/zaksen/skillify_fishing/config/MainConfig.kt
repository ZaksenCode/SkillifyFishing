package me.zaksen.skillify_fishing.config

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.zaksen.skillify_core.config.Mysql

@Serializable
data class MainConfig(
    @SerialName("database_connection")
    val db_connection: Mysql = Mysql(),

    @SerialName("profiles_table")
    val playersTable: String = "players",
)
