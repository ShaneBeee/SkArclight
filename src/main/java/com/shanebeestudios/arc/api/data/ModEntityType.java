package com.shanebeestudios.arc.api.data;

import com.shanebeestudios.arc.api.util.Util;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

public class ModEntityType {

    // Static Stuff

    private static final Map<String, ModEntityType> KEY_MAP = new HashMap<>();
    private static final Map<EntityType, ModEntityType> ENTITY_TYPE_MAP = new HashMap<>();
    private static Supplier<Iterator<ModEntityType>> SUPPLIER;

    public static void registerCustomEntityTypes() {
        int mcCount = 0;
        int modCount = 0;
        for (EntityType entityType : EntityType.values()) {
            if (entityType == EntityType.UNKNOWN) continue;
            NamespacedKey key = entityType.getKey();

            String keyString = key.toString();
            ModEntityType mod = new ModEntityType(entityType);
            KEY_MAP.put(keyString, mod);
            KEY_MAP.put(keyString.replaceAll("[:/_]", " "), mod);
            ENTITY_TYPE_MAP.put(entityType, mod);
            if (key.getNamespace().equalsIgnoreCase(NamespacedKey.MINECRAFT)) {
                mcCount++;
            } else {
                modCount++;
            }
        }
        Util.log("Registered &b%s &7Minecraft EntityTypes", mcCount);
        Util.log("Registered &b%s &7Modded EntityTypes", modCount);
    }

    public static ModEntityType parse(String string) {
        String lowerCase = string.toLowerCase(Locale.ROOT);
        if (KEY_MAP.containsKey(lowerCase)) {
            return KEY_MAP.get(lowerCase);
        }
        return null;
    }

    public static ModEntityType fromEntity(Entity entity) {
        EntityType type = entity.getType();
        if (ENTITY_TYPE_MAP.containsKey(type)) {
            return ENTITY_TYPE_MAP.get(type);
        }
        return null;
    }

    public static Supplier<Iterator<ModEntityType>> supplier() {
        if (SUPPLIER == null) {
            SUPPLIER = () -> ENTITY_TYPE_MAP.values().stream()
                .sorted(Comparator.comparing(ModEntityType::toString))
                .iterator();
        }
        return SUPPLIER;
    }

    // Class Stuff

    private final EntityType entityType;

    public ModEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public Entity spawn(Location location) {
        World world = location.getWorld();
        if (world == null) return null;

        return world.spawnEntity(location, this.entityType);
    }

    @Override
    public String toString() {
        return entityType.getKey().toString();
    }

}
