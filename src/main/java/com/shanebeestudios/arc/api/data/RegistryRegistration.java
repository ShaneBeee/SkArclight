package com.shanebeestudios.arc.api.data;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.registrations.Classes;
import com.shanebeestudios.arc.api.util.Util;
import org.bukkit.GameEvent;
import org.bukkit.Keyed;
import org.bukkit.Registry;
import org.bukkit.Sound;
import org.bukkit.Statistic;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Biome;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.generator.structure.Structure;
import org.bukkit.generator.structure.StructureType;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;

import java.util.HashMap;
import java.util.Map;

public class RegistryRegistration {

    private static final Map<ClassInfo<? extends Keyed>, Registry<? extends Keyed>> BY_CLASS_INFO = new HashMap<>();

    public static void init() {
        Util.log("Registering Registries:");
        register(Biome.class, Registry.BIOME);
        register(Enchantment.class, Registry.ENCHANTMENT);
        register(Statistic.class, Registry.STATISTIC);
        if (Skript.isRunningMinecraft(1, 16, 5)) {
            register(Attribute.class, Registry.ATTRIBUTE);
            register(Sound.class, Registry.SOUNDS);
        }
        if (Skript.isRunningMinecraft(1, 17, 1)) {
            register(GameEvent.class, Registry.GAME_EVENT);
        }
        if (Skript.isRunningMinecraft(1, 19, 4)) {
            register(Structure.class, Registry.STRUCTURE);
            register(StructureType.class, Registry.STRUCTURE_TYPE);
            register(TrimMaterial.class, Registry.TRIM_MATERIAL);
            register(TrimPattern.class, Registry.TRIM_PATTERN);
        }
    }

    private static void register(Class<? extends Keyed> typeClass, Registry<? extends Keyed> registry) {
        ClassInfo<? extends Keyed> exactClassInfo = Classes.getExactClassInfo(typeClass);
        if (exactClassInfo == null) return;
        BY_CLASS_INFO.put(exactClassInfo, registry);
        Util.log(" - &r'&b%s&r'", exactClassInfo.getDocName());
    }

    @SuppressWarnings("unchecked")
    public static <T extends Keyed> Registry<T> get(ClassInfo<?> classInfo) {
        return (Registry<T>) BY_CLASS_INFO.get(classInfo);
    }

}
