package com.shanebeestudios.arc.api.data;

import ch.njol.skript.Skript;
import ch.njol.skript.aliases.Aliases;
import ch.njol.skript.aliases.AliasesProvider;
import ch.njol.skript.aliases.AliasesProvider.AliasName;
import ch.njol.skript.aliases.InvalidMinecraftIdException;
import com.shanebeestudios.arc.api.util.Util;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

import java.util.HashMap;

public class SkriptRegistrations {

    public static void registerStuff() {
        registerCustomAliases();
        ModEntityType.registerCustomEntityTypes();
        if (Skript.classExists("org.bukkit.Registry")) {
            RegistryRegistration.init();
        }
    }

    /**
     * Register aliases for modded items
     */
    public static void registerCustomAliases() {
        AliasesProvider addonProvider = Aliases.getAddonProvider(Skript.getAddonInstance());
        int aliasStartCount = addonProvider.getAliasCount();

        for (Material material : Material.values()) {
            if (material.isLegacy()) continue;
            NamespacedKey namespacedKey = material.getKey();
            if (namespacedKey.getNamespace().contains("minecraft")) continue;

            String name = namespacedKey.toString();
            AliasName aliasName = new AliasName(name, name, 0);
            String nameNoChar = name.replaceAll("[:/_]", " ");
            AliasName aliasNameNoChar = new AliasName(nameNoChar, nameNoChar, 0);

            // Arclight makes Material enums as MODNAME_ITEMNAME
            String materialEnumName = name.replace(":", "_");
            try {
                addonProvider.addAlias(aliasName, materialEnumName, null, new HashMap<>());
                addonProvider.addAlias(aliasNameNoChar, materialEnumName, null, new HashMap<>());
                Util.debug("Registered item alias for: &r'&b%s&r'", name);
            } catch (InvalidMinecraftIdException ignore) {
            }
        }
        // Cut count in half since we're registering 2 names per item
        int count = (addonProvider.getAliasCount() - aliasStartCount) / 2;
        Util.log("Registered &b%s&7 Modded Item Aliases", count);
    }

}
