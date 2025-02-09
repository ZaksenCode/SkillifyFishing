package me.zaksen.skillify_fishing.config

import com.charleskorn.kaml.YamlComment
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.zaksen.skillify_core.config.Mysql
import net.kyori.adventure.text.Component

@Serializable
data class MainConfig(
    @SerialName("database_connection")
    val dbConnection: Mysql = Mysql(),

    @SerialName("profiles_table")
    val playersTable: String = "players",

    @SerialName("levels_map")
    @YamlComment(
        "Stores values in the form “experience - level",
        "Experience is the value of the experience from which the level starts.",
        "Level is the level that starts with the specified experience value (weird, right?)."
    )
    val levelsMap: Map<Long, Long> = mapOf(
        Pair(100, 2),
        Pair(200, 3),
        Pair(400, 4),
        Pair(600, 5),
        Pair(800, 6),
        Pair(1000, 7),
        Pair(1250, 8),
        Pair(1500, 9),
        Pair(1750, 10),
        Pair(2000, 11),
        Pair(2400, 12),
        Pair(2800, 13),
        Pair(3200, 14),
        Pair(3600, 15),
        Pair(4000, 16),
        Pair(4500, 17),
        Pair(5000, 18),
        Pair(5500, 19),
        Pair(6000, 20)
    ),

    @SerialName("fishing_action_experience")
    val fishingExperience: FishingActionExperience = FishingActionExperience(2, 10),

    @SerialName("messages")
    val messages: Messages = Messages()
)

@Serializable
data class FishingActionExperience(
    @SerialName("min")
    val min: Long,

    @SerialName("max")
    val max: Long
)

@Serializable
data class Messages(
    @SerialName("too_low_level")
    val tooLowLevel: Component = Component.text("<red>Your level is too low to use this fishing rod!")
)