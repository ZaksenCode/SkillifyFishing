package me.zaksen.skillify_fishing.item

import me.zaksen.skillify_core.api.item.CustomItemRegistry
import me.zaksen.skillify_fishing.FishingKeys
import me.zaksen.skillify_fishing.config.MainConfig
import me.zaksen.skillify_fishing.database.FishingSkillRepository
import me.zaksen.skillify_fishing.item.rod.RodRegistry

class CustomItemLoader(
    private val registry: CustomItemRegistry,
    private val rodRegistry: RodRegistry,
    private val fishingRepository: FishingSkillRepository,
    private val config: MainConfig
) {

    fun loadItems() {
        rodRegistry.getRegisteredRods().forEach {
            registry.registerItemWithStack(
                it.key,
                BasicFishingItem(
                    rodRegistry,
                    fishingRepository,
                    config
                )
            ) { it.value.rodStack }
        }
    }
}