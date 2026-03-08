package xyz.hajsori.pillars.events

import io.papermc.paper.event.player.ChatEvent
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import xyz.hajsori.pillars.Pillars


class ChatListener(val plugin: Pillars) : Listener {
    @EventHandler
    fun onChat(event: ChatEvent) {
        val players = arrayOfNulls<Player>(8)
        repeat(event.player.world.playerCount) { i ->
            players[i] = event.player.world.players[i]
        }
        players.shuffle()

        GameReady(plugin, Location(event.player.world, 0.0, 64.0, 0.0), players)
    }
}
