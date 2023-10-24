package com.shanebeestudios.arc.elements.type;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import com.shanebeestudios.arc.api.data.ModEntityType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Types {

    static {
        Classes.registerClass(new ClassInfo<>(ModEntityType.class, "modentitytype")
                .user("mod ?entity ?types?")
                .name("Mod Entity Type")
                .description("Represents the entity type of a modded entity.")
                .since("INSERT VERSION")
                .parser(new Parser<>() {

                    @SuppressWarnings("NullableProblems")
                    @Override
                    @Nullable
                    public ModEntityType parse(String string, ParseContext context) {
                        return ModEntityType.parse(string);
                    }

                    @Override
                    public @NotNull String toString(ModEntityType modEntityType, int flags) {
                        return modEntityType.toString();
                    }

                    @Override
                    public @NotNull String toVariableNameString(ModEntityType modEntityType) {
                        return modEntityType.toString();
                    }
                }));
    }

}
