package me.zaksen.skillify_fishing

import org.bukkit.NamespacedKey
import org.bukkit.plugin.java.JavaPlugin

class FishingKeys(
    plugin: JavaPlugin
) {
    val fishingRodItemKey = NamespacedKey(plugin, "fishing_rod")
    val fishingRodIdKey = NamespacedKey(plugin, "fishing_rod_id")
}