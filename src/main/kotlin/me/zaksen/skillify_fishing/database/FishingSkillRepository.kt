package me.zaksen.skillify_fishing.database

import me.zaksen.skillify_core.api.database.Repository
import me.zaksen.skillify_core.database.dao.PlayerProfile
import me.zaksen.skillify_fishing.database.dao.FishingSkill
import java.sql.Connection
import java.sql.DriverManager
import java.util.*

class FishingSkillRepository(
    host: String,
    port: String,
    base: String,
    private val table: String,
    user: String,
    pass: String,
    profilesTable: String
): Repository<FishingSkill, FishingSkillSpecification> {
    private val cache = mutableSetOf<FishingSkill>()
    private val connection: Connection = DriverManager.getConnection("jdbc:mysql://$host:$port/$base", user, pass)

    init {
        try {
            val statement = connection.prepareStatement("""
                SELECT * 
                FROM $table
                INNER JOIN $profilesTable 
                ON $table.player_id = $profilesTable.player_id;
            """)
            val set = statement.executeQuery()

            // Load values into cache
            while (set.next()) {
                val id = set.getLong("fishing_skill_id")
                val playerId = set.getLong("player_id")
                val uuid = set.getString("player_uuid")
                val name = set.getString("player_name")
                val registerTime = set.getTimestamp("player_register_time")
                val experience = set.getLong("fishing_experience")
                cache.add(FishingSkill(id, PlayerProfile(playerId, UUID.fromString(uuid), name, registerTime), experience))
            }
        } catch (_: Exception) {}
    }

    override fun add(value: FishingSkill) {
        try {
            val statement = connection.prepareStatement("""
                INSERT INTO $table (player_id, experience)
                VALUES (?, ?);
            """)
            statement.setLong(1, value.profile.id)
            statement.setLong(2, value.experience)
            statement.execute()
            statement.close()
            cache.add(value)
        } catch (_: Exception) {}
    }

    override fun remove(value: FishingSkill) {
        try {
            val statement = connection.prepareStatement("""
                DELETE FROM $table 
                WHERE fishing_skill_id = ?;
            """)
            statement.setLong(1, value.id)
            statement.execute()
            statement.close()
            cache.remove(value)
        } catch (_: Exception) {}
    }

    override fun update(value: FishingSkill) {
        try {
            val statement = connection.prepareStatement("""
                UPDATE $table
                SET player_id = ?, experience = ?
                WHERE fishing_skill_id = ?; 
            """)
            statement.setLong(1, value.profile.id)
            statement.setLong(2, value.experience)
            statement.setLong(3, value.id)
            statement.executeUpdate()
            statement.close()
            cache.removeIf { it.id == value.id }
            cache.add(value)
        } catch (_: Exception) {}
    }

    override fun query(specification: FishingSkillSpecification): Set<FishingSkill> {
        val result = mutableSetOf<FishingSkill>()

        result.addAll(cache.filter { specification.specified(it) })

        return result
    }
}