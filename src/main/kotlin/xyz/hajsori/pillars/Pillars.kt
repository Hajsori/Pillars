package xyz.hajsori.pillars

import org.bukkit.Material
import org.bukkit.Registry
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.PotionMeta
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import xyz.hajsori.pillars.events.ChatListener
import kotlin.reflect.typeOf


class Pillars : JavaPlugin() {
    var gameStarted = false
    val forbiddenItems = listOf(
        Material.COMMAND_BLOCK,
        Material.CHAIN_COMMAND_BLOCK,
        Material.REPEATING_COMMAND_BLOCK,
        Material.COMMAND_BLOCK_MINECART,
        Material.JIGSAW,
        Material.STRUCTURE_BLOCK,
        Material.STRUCTURE_VOID,
        Material.BARRIER,
        Material.DEBUG_STICK,
        Material.TEST_INSTANCE_BLOCK,
        Material.TEST_BLOCK,
        Material.LIGHT
    )

    override fun onEnable() {
        server.pluginManager.registerEvents(ChatListener(this), this)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
