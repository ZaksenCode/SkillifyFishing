package me.zaksen.skillify_fishing

import me.zaksen.skillify_core.SkillifyCore
import me.zaksen.skillify_core.api.config.loadConfig
import me.zaksen.skillify_fishing.config.MainConfig
import me.zaksen.skillify_fishing.database.FishingSkillRepository
import me.zaksen.skillify_fishing.event.PlayerListener
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class SkillifyFishing : JavaPlugin() {

    private lateinit var mainConfig: MainConfig
    private lateinit var fishingRepository: FishingSkillRepository

    override fun onEnable() {
        mainConfig = loadConfig(File(dataFolder, "config.yml"))

        fishingRepository = FishingSkillRepository(
            mainConfig.db_connection.host,
            mainConfig.db_connection.port,
            mainConfig.db_connection.base,
            mainConfig.db_connection.table,
            mainConfig.db_connection.user,
            mainConfig.db_connection.pass,
            mainConfig.playersTable
        )

        Bukkit.getPluginManager().registerEvents(
            PlayerListener(
                SkillifyCore.getCore().playersRepository,
                fishingRepository
            ),
            this
        )
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
