package me.zaksen.skillify_fishing.event

import me.zaksen.skillify_core.database.PlayerProfileRepository
import me.zaksen.skillify_core.database.PlayerProfileUuidSpecification
import me.zaksen.skillify_fishing.database.FishingSkillRepository
import me.zaksen.skillify_fishing.database.dao.FishingSkill
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerListener(
    private val profileRepository: PlayerProfileRepository,
    private val fishingRepository: FishingSkillRepository
): Listener {

    @EventHandler
    private fun addSkillData(event: PlayerJoinEvent) {
        val playerProfile = profileRepository.query(PlayerProfileUuidSpecification(event.player.uniqueId))

        if(playerProfile.isEmpty()) {
            val query = profileRepository.query(PlayerProfileUuidSpecification(event.player.uniqueId))

            if(query.isEmpty()) {
                return
            }

            val profile = query.first()

            fishingRepository.add(FishingSkill(
                -1,
                profile,
                0
            ))
        }
    }
}