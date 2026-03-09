package xyz.hajsori.pillars.events

import io.papermc.paper.entity.LookAnchor
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Registry
import org.bukkit.block.structure.Mirror
import org.bukkit.block.structure.StructureRotation
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.PotionMeta
import xyz.hajsori.pillars.PillarTypes
import xyz.hajsori.pillars.Pillars
import java.util.Random
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.floor
import kotlin.math.round
import kotlin.math.sin


class GameReady(plugin: Pillars, center: Location, players: Array<Player?>) : Event() {
    private val HANDLER_LIST = HandlerList()
    private val PILLAR_TYPE = PillarTypes.random().get(plugin)

    init {
        val extraWidth = floor(PILLAR_TYPE.getWidth() / 2.0)
        val count = players.count()
        val radius = (count * 10) / (2 * PI)
        val pillars = (0 until count).map { i ->
            val angle = 2 * PI * i / count
            val x = round(center.x + cos(angle) * radius)
            val z = round(center.z + sin(angle) * radius)

            Location(center.world, x - extraWidth, center.y - PILLAR_TYPE.getHeight(), z - extraWidth)
        }

        repeat(pillars.count()) { i ->
            val pillarLocation = pillars[i]
            val player = players[i]

            Bukkit.getStructureManager().loadStructure(plugin.getResource(PILLAR_TYPE.getFile())!!).place(
                pillarLocation, true,
                StructureRotation.NONE, Mirror.NONE, 0, 1F, Random()
            )

            if (player != null) {
                pillarLocation.y = 64.0
                pillarLocation.add(0.5 + extraWidth, 0.0, 0.5 + extraWidth)

                player.teleport(pillarLocation)
                player.lookAt(0.5, 64.0, 0.5, LookAnchor.FEET)
            }
        }

        plugin.gameStarted = true
        startGivingItems(plugin)
    }

    fun startGivingItems(plugin: Pillars) {
        val scheduler = plugin.server.scheduler

        scheduler.runTaskTimer(plugin, { task ->
            for (player in plugin.server.worlds[0].players) {
                val randomItem = ItemStack(Material.entries.filter { material ->
                    material.isItem && !plugin.forbiddenItems.contains(material)
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

    override fun getHandlers(): HandlerList {
        return HANDLER_LIST
    }
}