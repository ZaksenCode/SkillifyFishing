package me.zaksen.skillify_fishing.database.dao

import me.zaksen.skillify_core.database.dao.PlayerProfile

data class FishingSkill(
    val id: Long,
    val profile: PlayerProfile,
    val experience: Long
)