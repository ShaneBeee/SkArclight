package com.shanebeestudios.arc;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import com.shanebeestudios.arc.api.data.ModEntityType;
import com.shanebeestudios.arc.api.data.ModdedAliases;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

@SuppressWarnings("unused")
public class SkArclight extends JavaPlugin {

    private static SkArclight instance;
    private SkriptAddon addon;

    @Override
    public void onEnable() {
        if (instance != null) {
            throw new IllegalStateException("SkArclight already has an instance running.");
        }
        instance = this;

        this.addon = Skript.registerAddon(this);
        ModdedAliases.setupAliases();
        ModEntityType.init();
        try {
            this.addon.loadClasses("com.shanebeestudios.arc.elements");
            this.addon.setLanguageFileDirectory("lang");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
