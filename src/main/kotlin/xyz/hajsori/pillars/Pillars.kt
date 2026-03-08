package xyz.hajsori.pillars

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import xyz.hajsori.pillars.events.ChatListener


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
                player.give(listOf(ItemStack(Material.entries.filter { material ->
                    material.isItem && !forbiddenItems.contains(material)
                }.random())), false)
            }
        }, 0, 60)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
