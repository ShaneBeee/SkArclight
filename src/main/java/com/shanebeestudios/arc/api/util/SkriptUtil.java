package com.shanebeestudios.arc.api.util;

import ch.njol.skript.localization.Language;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.function.Consumer;

public class SkriptUtil {

    @SuppressWarnings("unchecked")
    public static void addToLangFile(Consumer<Map<String, String>> consumer) {
        try {
            Field field = Language.class.getDeclaredField("defaultLanguage");
            field.setAccessible(true);
            Map<String, String> langMap = (Map<String, String>) field.get(null);

            consumer.accept(langMap);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
