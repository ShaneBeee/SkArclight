package com.shanebeestudios.arc.api.data;

import ch.njol.skript.Skript;
import ch.njol.skript.aliases.Aliases;
import ch.njol.skript.aliases.AliasesProvider;
import ch.njol.skript.aliases.InvalidMinecraftIdException;
import com.shanebeestudios.arc.api.util.Util;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

import java.util.HashMap;

public class SkriptRegistrations {

    public static void registerStuff() {
        registerCustomAliases();
        ModEntityType.registerCustomEntityTypes();
    }

    /**
     * Register aliases for modded items
     */
    public static void registerCustomAliases() {
        AliasesProvider addonProvider = Aliases.getAddonProvider(Skript.getAddonInstance());
        int aliasStartCount = addonProvider.getAliasCount();

        for (Material material : Material.values()) {
            if (material.isLegacy()) continue;
            NamespacedKey key = material.getKey();
            if (key.getNamespace().contains("minecraft")) continue;

            String name = key.toString();
            AliasesProvider.AliasName aliasName = new AliasesProvider.AliasName(name, name, 0);

            // Arclight names items like "minecraft:mod_item"
            String id = "minecraft:" + name.replace(":", "_");
            try {
                addonProvider.addAlias(aliasName, id, null, new HashMap<>());
            } catch (InvalidMinecraftIdException ignore) {
            }
        }
        Util.log("Registered &b%s&7 Modded Item Aliases", (addonProvider.getAliasCount() - aliasStartCount));
    }

}
