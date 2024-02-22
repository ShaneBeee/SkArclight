package com.shanebeestudios.arc.listeners;

import com.shanebeestudios.arc.SkArclight;
import com.shanebeestudios.arc.api.data.SkriptRegistrations;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

public class CommandListener implements Listener {

    private final SkArclight plugin;

    public CommandListener(SkArclight plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    private void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        reloadAliases(event.getMessage());
    }

    @EventHandler
    private void onServerCommand(ServerCommandEvent event) {
        reloadAliases(event.getCommand());
    }

    private void reloadAliases(String cmd) {
        if (cmd.startsWith("sk") && (cmd.contains("reload aliases") || cmd.contains("reload all"))) {
            Bukkit.getScheduler().runTaskLater(this.plugin, SkriptRegistrations::registerCustomAliases, 0);
        }
    }

}
