package me.zaksen.skillify_fishing.database

import me.zaksen.skillify_core.api.database.TableBuilder
import java.sql.Connection

class FishingSkillTableBuilder(
    private val connection: Connection,
    private val table: String,
    private val playersTable: String
): TableBuilder {
    override fun buildTable() {
        try {
            val statement = connection.prepareStatement("""
                CREATE TABLE IF NOT EXISTS $table (
                    fishing_skill_id            INT UNSIGNED NOT NULL AUTO_INCREMENT,
                    player_id                   INT UNSIGNED NOT NULL,
                    experience                  INT UNSIGNED NOT NULL,
                    PRIMARY KEY (fishing_skill_id),
                    FOREIGN KEY (player_id) REFERENCES $playersTable(player_id)
                );
            """)
            statement.execute()
            statement.close()
        } catch (e: Exception) { }
    }
}