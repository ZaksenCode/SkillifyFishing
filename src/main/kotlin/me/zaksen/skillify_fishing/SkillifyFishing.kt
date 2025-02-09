package me.zaksen.skillify_fishing

import me.zaksen.skillify_core.SkillifyCore
import me.zaksen.skillify_core.api.config.loadConfig
import me.zaksen.skillify_core.api.item.CustomItemRegistry
import me.zaksen.skillify_core.api.subplugin.SubPlugin
import me.zaksen.skillify_fishing.config.MainConfig
import me.zaksen.skillify_fishing.database.FishingSkillRepository
import me.zaksen.skillify_fishing.event.PlayerListener
import me.zaksen.skillify_fishing.item.CustomItemLoader
import me.zaksen.skillify_fishing.item.rod.RodRegistry
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File

class SkillifyFishing : JavaPlugin(), SubPlugin {

    private lateinit var mainConfig: MainConfig

    private val logger: Logger = LoggerFactory.getLogger("SkillifyFishing")
    private val keys: FishingKeys = FishingKeys(this)
    private val rodRegistry: RodRegistry = RodRegistry(File(dataFolder, "rods"), keys.fishingRodIdKey)
    private val fishingRepository: FishingSkillRepository by lazy {
        FishingSkillRepository(
            logger,
            mainConfig.dbConnection.host,
            mainConfig.dbConnection.port,
            mainConfig.dbConnection.base,
            mainConfig.dbConnection.table,
            mainConfig.dbConnection.user,
            mainConfig.dbConnection.pass,
            mainConfig.playersTable
        )
    }

    override fun onLoad() {
        mainConfig = loadConfig(File(dataFolder, "config.yml"))
        SkillifyCore.getCore().registerSubPlugin(this)
    }

    override fun loadItems(registry: CustomItemRegistry) {
        rodRegistry.reload()

        CustomItemLoader(
            registry,
            rodRegistry,
            fishingRepository,
            mainConfig
        ).loadItems()
    }

    override fun onEnable() {
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
