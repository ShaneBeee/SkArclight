package com.shanebeestudios.arc.api.util;

import ch.njol.skript.Skript;
import ch.njol.skript.log.ErrorQuality;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

    private static final String PREFIX = "&7[&bSk&3Arclight&7] ";
    private static final String PREFIX_DEBUG = "&7[&bSk&3Arclight&7-&eDebug&7] ";
    private static final String PREFIX_ERROR = "&7[&bSk&3Arclight &cERROR&7] ";
    private static final Pattern HEX_PATTERN = Pattern.compile("<#([A-Fa-f\\d]){6}>");
    private static final boolean SKRIPT_IS_THERE = Bukkit.getPluginManager().getPlugin("Skript") != null;
    public static final boolean DEBUG = Boolean.parseBoolean(System.getProperty("skarclight.debug"));
    private static final ConsoleCommandSender CONSOLE = Bukkit.getConsoleSender();

    @SuppressWarnings("deprecation") // Paper deprecation
    public static String getColString(String string) {
        Matcher matcher = HEX_PATTERN.matcher(string);
        if (SKRIPT_IS_THERE) {
            while (matcher.find()) {
                final ChatColor hexColor = ChatColor.of(matcher.group().substring(1, matcher.group().length() - 1));
                final String before = string.substring(0, matcher.start());
                final String after = string.substring(matcher.end());
                string = before + hexColor + after;
                matcher = HEX_PATTERN.matcher(string);
            }
        } else {
            string = HEX_PATTERN.matcher(string).replaceAll("");
        }
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static void sendColMsg(CommandSender receiver, String format, Object... objects) {
        receiver.sendMessage(getColString(String.format(format, objects)));
    }

    /**
     * Log a message to console
     *
     * @param format  Format of message to log
     * @param objects Objects within format
     */
    public static void log(String format, Object... objects) {
        String log = String.format(format, objects);
        CONSOLE.sendMessage(getColString(PREFIX + log));
    }

    /**
     * Log a message to console if Debug property is enabled
     *
     * @param format  Format of debug message to log
     * @param objects Objects within format
     */
    public static void debug(String format, Object... objects) {
        if (DEBUG) {
            String log = String.format(format, objects);
            CONSOLE.sendMessage(getColString(PREFIX_DEBUG + log));
        }
    }

    public static void skriptError(String format, Object... objects) {
        String error = String.format(format, objects);
        Skript.error(getColString(PREFIX_ERROR + error), ErrorQuality.SEMANTIC_ERROR);
    }

    /**
     * Gets a Minecraft NamespacedKey from string
     * <p>If a namespace is not provided, it will default to "minecraft:" namespace</p>
     *
     * @param key   Key for new Minecraft NamespacedKey
     * @param error Whether to send a skript/console error if one occurs
     * @return new Minecraft NamespacedKey
     */
    @Nullable
    public static NamespacedKey getNamespacedKey(@Nullable String key, boolean error) {
        if (key == null) return null;
        if (!key.contains(":")) key = "minecraft:" + key;
        if (key.length() > 255) {
            if (error)
                skriptError("An invalid key was provided, key must be less than 256 characters: %s", key);
            return null;
        }
        key = key.toLowerCase();
        if (key.contains(" ")) {
            key = key.replace(" ", "_");
        }

        NamespacedKey namespacedKey = NamespacedKey.fromString(key);
        if (namespacedKey == null && error)
            skriptError("An invalid key was provided, that didn't follow [a-z0-9/._-:]. key: %s", key);
        return namespacedKey;
    }

}
