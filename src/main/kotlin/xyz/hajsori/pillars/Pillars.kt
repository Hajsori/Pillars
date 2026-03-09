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
        val scheduler = this.server.scheduler

        server.pluginManager.registerEvents(ChatListener(this), this)

        scheduler.runTaskTimer(this, { task ->
            for (player in this.server.worlds[0].players) {
                val randomItem = ItemStack(Material.entries.filter { material ->
                    material.isItem && !forbiddenItems.contains(material)
                }.random())
                if (randomItem == ItemStack(Material.POTION) || randomItem == ItemStack(Material.SPLASH_POTION) || randomItem == ItemStack(Material.LINGERING_POTION) ) {
                    val randomEffect = Registry.POTION.keyStream().toList().random()

                    if (randomItem.itemMeta is PotionMeta) {
                        val meta = randomItem.itemMeta as PotionMeta
                        meta.basePotionType = Registry.POTION.get(randomEffect)
                        randomItem.itemMeta = meta
                    }
                }

                player.give(listOf(randomItem), false)
            }
        }, 0, 60)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
