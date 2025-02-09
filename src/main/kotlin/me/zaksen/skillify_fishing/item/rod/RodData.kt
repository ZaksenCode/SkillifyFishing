package me.zaksen.skillify_fishing.item.rod

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.zaksen.skillify_core.api.config.serialization.ItemStackValue
import me.zaksen.skillify_core.api.config.serialization.NamespacedKeyValue
import me.zaksen.skillify_core.api.recipe.data.ShapedRecipe

@Serializable
data class RodData(
    @SerialName("rod_id")
    val rodId: NamespacedKeyValue,

    @SerialName("rod_stack")
    val rodStack: ItemStackValue,

    @SerialName("recipe")
    val recipe: ShapedRecipe? = null,

    @SerialName("loot_table_id")
    val lootTableId: NamespacedKeyValue,

    @SerialName("required_level")
    val requiredLevel: Long,
)