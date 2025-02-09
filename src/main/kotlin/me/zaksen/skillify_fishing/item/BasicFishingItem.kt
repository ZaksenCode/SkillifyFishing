package me.zaksen.skillify_fishing.item

import me.zaksen.skillify_core.api.item.CustomItem
import me.zaksen.skillify_fishing.config.MainConfig
import me.zaksen.skillify_fishing.database.FishingSkillRepository
import me.zaksen.skillify_fishing.database.FishingSkillUuidSpecification
import me.zaksen.skillify_fishing.item.rod.RodRegistry
import org.bukkit.event.player.PlayerInteractEvent

class BasicFishingItem(
    private val rodRegistry: RodRegistry,
    private val fishingRepository: FishingSkillRepository,
    private val config: MainConfig
): CustomItem() {
    override fun onUse(event: PlayerInteractEvent) {
        val stack = event.item ?: return
        val rod = rodRegistry.getRod(stack) ?: return

        val player = event.player
        val query = fishingRepository.query(FishingSkillUuidSpecification(player.uniqueId))

        if(query.isEmpty()) {
            return
        }

        val fishingSkill = query.first()
        val playerLevel = fishingSkill.getLevel(config.levelsMap)

        if(playerLevel < rod.requiredLevel) {
            event.isCancelled = true
            player.sendActionBar(config.messages.tooLowLevel)
        }
    }
}