package com.shanebeestudios.arc.api.data;

import ch.njol.skript.Skript;
import ch.njol.skript.aliases.Aliases;
import ch.njol.skript.aliases.AliasesProvider;
import ch.njol.skript.aliases.InvalidMinecraftIdException;
import com.shanebeestudios.arc.api.util.SkriptUtil;
import com.shanebeestudios.arc.api.util.Util;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Biome;

import java.util.HashMap;
import java.util.Locale;

public class SkriptRegistrations {

    public static void registerStuff() {
        registerCustomAliases();
        ModEntityType.registerCustomEntityTypes();
        registerBiomes();
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

    private static void registerBiomes() {
        SkriptUtil.addToLangFile(langMap -> {
            int biomes = 0;
            for (Biome biome : Biome.values()) {
                if (biome == Biome.CUSTOM) continue;

                NamespacedKey key = biome.getKey();
                if (key.getNamespace().contains("minecraft")) continue;

                String acrKey = key.toString().replace(":", "_").toLowerCase(Locale.ROOT);
                langMap.put("biomes." + acrKey, key.toString());
                biomes++;
            }
            Util.log("Registered &b%s &7Modded Biomes", biomes);
        });
    }

}
