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
        if (event.message().toString() != "TextComponentImpl{content=\"start\", style=StyleImpl{obfuscated=not_set, bold=not_set, strikethrough=not_set, underlined=not_set, italic=not_set, color=null, shadowColor=null, clickEvent=null, hoverEvent=null, insertion=null, font=null}, children=[]}") {
            return
        }

        val players = arrayOfNulls<Player>(8)
        repeat(event.player.world.playerCount) { i ->
            players[i] = event.player.world.players[i]
        }
        players.shuffle()

        GameReady(plugin, Location(event.player.world, 0.0, 64.0, 0.0), players)
    }
}
