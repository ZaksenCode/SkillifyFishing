package me.zaksen.skillify_fishing.item.rod

import me.zaksen.skillify_core.api.config.loadConfig
import me.zaksen.skillify_core.api.config.saveConfig
import me.zaksen.skillify_core.api.util.extensions.loadDirectoryFiles
import me.zaksen.skillify_core.api.util.extensions.toNamespacedKey
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import java.io.File

class RodRegistry(
    private val rodsDirectory: File,
    private val rodIdKey: NamespacedKey
) {
    private val registeredRods: MutableMap<NamespacedKey, RodData> = mutableMapOf()

    fun reload() {
        registeredRods.clear()

        val allFiles = rodsDirectory.loadDirectoryFiles()

        if(allFiles.isEmpty()) {
            saveConfig(rodsDirectory, "example_rod.yml", RodData(
                NamespacedKey.fromString("skillifycore:example_rod")!!,
                ItemStack(Material.FISHING_ROD, 1),
                null,
                NamespacedKey.fromString("skillifycore:example")!!,
                5
            ))
            allFiles.add(File(rodsDirectory, "example_rod.yml"))
        }

        allFiles.forEach {
            val rodData = loadConfig<RodData>(it)
            registeredRods[rodData.rodId] = rodData
        }
    }

    fun getRod(key: NamespacedKey): RodData? {
        return registeredRods[key]
    }

    fun getRod(stack: ItemStack): RodData? {
        if(!stack.hasItemMeta()) {
            return null
        }

        val pdc = stack.itemMeta.persistentDataContainer
        val key = pdc.get(rodIdKey, PersistentDataType.STRING) ?: return null

        return getRod(key.toNamespacedKey())
    }

    fun getRegisteredRods(): Map<NamespacedKey, RodData> {
        return registeredRods;
    }
}