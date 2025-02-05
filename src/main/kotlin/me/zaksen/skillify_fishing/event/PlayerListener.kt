package me.zaksen.skillify_fishing.event

import me.zaksen.skillify_core.database.PlayerProfileRepository
import me.zaksen.skillify_core.database.PlayerProfileUuidSpecification
import me.zaksen.skillify_fishing.database.FishingSkillRepository
import me.zaksen.skillify_fishing.database.FishingSkillUuidSpecification
import me.zaksen.skillify_fishing.database.dao.FishingSkill
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerFishEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.slf4j.Logger

class PlayerListener(
    private val profileRepository: PlayerProfileRepository,
    private val fishingRepository: FishingSkillRepository,
    private val logger: Logger
): Listener {

    @EventHandler
    private fun addSkillData(event: PlayerJoinEvent) {
        val playerProfile = fishingRepository.query(FishingSkillUuidSpecification(event.player.uniqueId))

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

    @EventHandler
    private fun processFishingLogic(event: PlayerFishEvent) {
        if(event.state != PlayerFishEvent.State.CAUGHT_FISH) {
            return
        }

        val query = fishingRepository.query(FishingSkillUuidSpecification(event.player.uniqueId))

        if(query.isEmpty()) {
            logger.warn("Unable to process fish event for player ${event.player.name} -> no fishing skill or player profile!")
            return
        }

        val skillInfo = query.first()
        val rodStack = if(event.player.inventory.itemInMainHand.type == Material.FISHING_ROD) {
            event.player.inventory.itemInMainHand
        } else {
            event.player.inventory.itemInOffHand
        }

        fishingRepository.update(FishingSkill(
            skillInfo.id,
            skillInfo.profile,
            skillInfo.experience + 1
        ))

        logger.info("${event.player.name} caught fish!\nNew experience: ${skillInfo.experience + 1}")
    }
}