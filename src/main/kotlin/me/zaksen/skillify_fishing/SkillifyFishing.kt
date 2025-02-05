package me.zaksen.skillify_fishing

import me.zaksen.skillify_core.SkillifyCore
import me.zaksen.skillify_core.api.config.loadConfig
import me.zaksen.skillify_fishing.config.MainConfig
import me.zaksen.skillify_fishing.database.FishingSkillRepository
import me.zaksen.skillify_fishing.event.PlayerListener
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File

class SkillifyFishing : JavaPlugin() {

    private val logger: Logger = LoggerFactory.getLogger("SkillifyFishing")
    private lateinit var mainConfig: MainConfig
    private lateinit var fishingRepository: FishingSkillRepository

    override fun onEnable() {
        mainConfig = loadConfig(File(dataFolder, "config.yml"))

        fishingRepository = FishingSkillRepository(
            logger,
            mainConfig.dbConnection.host,
            mainConfig.dbConnection.port,
            mainConfig.dbConnection.base,
            mainConfig.dbConnection.table,
            mainConfig.dbConnection.user,
            mainConfig.dbConnection.pass,
            mainConfig.playersTable
        )

        Bukkit.getPluginManager().registerEvents(
            PlayerListener(
                SkillifyCore.getCore().playersRepository,
                fishingRepository,
                logger
            ),
            this
        )
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
