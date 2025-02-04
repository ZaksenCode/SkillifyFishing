package me.zaksen.skillify_fishing.database

import me.zaksen.skillify_core.api.database.RepositorySpecification
import me.zaksen.skillify_core.api.database.SqlSpecification
import me.zaksen.skillify_fishing.database.dao.FishingSkill

interface FishingSkillSpecification: RepositorySpecification<FishingSkill>, SqlSpecification