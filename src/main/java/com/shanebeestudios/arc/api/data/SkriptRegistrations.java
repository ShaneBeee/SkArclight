package com.shanebeestudios.arc.api.data;

import com.shanebeestudios.arc.api.util.SkriptUtil;
import com.shanebeestudios.arc.api.util.Util;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Biome;

import java.util.Locale;

public class SkriptRegistrations {

    public static void registerStuff() {
        ModdedAliases.registerCustomAliases();
        ModEntityType.registerCustomEntityTypes();
        registerBiomes();
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
