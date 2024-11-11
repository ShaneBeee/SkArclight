package com.shanebeestudios.arc.elements.type;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.classes.Serializer;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import ch.njol.yggdrasil.Fields;
import com.shanebeestudios.arc.api.data.ModEntityType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.StreamCorruptedException;

@SuppressWarnings("unused")
public class Types {

    static {
        Classes.registerClass(new ClassInfo<>(ModEntityType.class, "modentitytype")
                .user("mod ?entity ?types?")
                .name("Mod Entity Type")
                .description("Represents the entity type of a modded entity.")
                .since("1.0.0")
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
                })
                .supplier(ModEntityType.supplier())
                .serializer(new Serializer<>() {
                    @Override
                    public @NotNull Fields serialize(ModEntityType entityType) {
                        Fields fields = new Fields();
                        fields.putObject("key", entityType.toString());
                        return fields;
                    }

                    @SuppressWarnings("NullableProblems")
                    @Override
                    public void deserialize(ModEntityType o, Fields f) {
                        assert false;
                    }

                    @SuppressWarnings("NullableProblems")
                    @Override
                    protected ModEntityType deserialize(Fields fields) throws StreamCorruptedException {
                        String key = fields.getObject("key", String.class);
                        if (key == null) {
                            throw new StreamCorruptedException("ModEntityType key == null");
                        }
                        ModEntityType modEntityType = ModEntityType.parse(key);
                        if (modEntityType == null) {
                            throw new StreamCorruptedException("ModEntityType invalid: " + key);
                        }
                        return modEntityType;
                    }

                    @Override
                    public boolean mustSyncDeserialization() {
                        return false;
                    }

                    @Override
                    protected boolean canBeInstantiated() {
                        return false;
                    }
                }));
    }

}
