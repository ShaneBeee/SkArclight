package com.shanebeestudios.arc.api.data;

import ch.njol.skript.Skript;
import ch.njol.skript.aliases.Aliases;
import ch.njol.skript.aliases.AliasesProvider;
import ch.njol.skript.aliases.InvalidMinecraftIdException;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

import java.util.HashMap;

public class ModdedAliases {

    /**
     * Register aliases for modded items
     */
    public static void setupAliases() {
        AliasesProvider addonProvider = Aliases.getAddonProvider(Skript.getAddonInstance());
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
    }

}
