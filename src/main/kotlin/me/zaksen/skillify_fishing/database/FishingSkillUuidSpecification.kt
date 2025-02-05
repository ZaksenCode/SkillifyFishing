package me.zaksen.skillify_fishing.database

import me.zaksen.skillify_fishing.database.dao.FishingSkill
import java.util.UUID

class FishingSkillUuidSpecification(
    private val uuid: UUID
): FishingSkillSpecification {
    override fun specified(value: FishingSkill): Boolean {
        return value.profile.uuid == uuid
    }

    override fun toSqlClauses(): String {
        return "uuid = $uuid"
    }
}