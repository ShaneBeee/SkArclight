package com.shanebeestudios.arc;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import ch.njol.skript.util.Version;
import com.shanebeestudios.arc.api.data.SkriptRegistrations;
import com.shanebeestudios.arc.api.util.MessageFilter;
import com.shanebeestudios.arc.api.util.Util;
import com.shanebeestudios.arc.listeners.CommandListener;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

@SuppressWarnings("unused")
public class SkArclight extends JavaPlugin {

    private static SkArclight instance;
    private SkriptAddon addon;
    private final MessageFilter messageFilter = new MessageFilter();

    @Override
    public void onEnable() {
        // Disable filter after Skript has loaded item Aliases
        this.messageFilter.disable();

        if (Skript.getVersion().isSmallerThan(new Version(2, 9))) {
            Util.skriptError("SkArclight requires Skript 2.9+ to run.");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        long start = System.currentTimeMillis();
        if (instance != null) {
            throw new IllegalStateException("SkArclight already has an instance running.");
        }
        instance = this;

        this.addon = Skript.registerAddon(this);
        SkriptRegistrations.registerStuff();
        try {
            this.addon.loadClasses("com.shanebeestudios.arc.elements");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        registerBstatsMetrics();

        // Register Command Listener
        getServer().getPluginManager().registerEvents(new CommandListener(this), this);

        long finish = System.currentTimeMillis() - start;
        Util.log("Finished loading in &a%s&7ms", finish);
    }

    private void registerBstatsMetrics() {
        Metrics metrics = new Metrics(this, 20137);
        metrics.addCustomChart(new SimplePie("skript_version", () -> Skript.getVersion().toString()));
    }

    @Override
    public void onDisable() {
        instance = null;
        this.addon = null;
    }

    public static SkArclight getInstance() {
        return instance;
    }

    public SkriptAddon getAddon() {
        return this.addon;
    }

}
