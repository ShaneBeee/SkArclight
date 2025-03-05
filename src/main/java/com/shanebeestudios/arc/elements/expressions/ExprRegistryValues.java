package com.shanebeestudios.arc.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.shanebeestudios.arc.api.data.RegistryRegistration;
import org.bukkit.Keyed;
import org.bukkit.Registry;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@Name("Registry - All Values")
@Description({"Get all values from a Minecraft registry.",
    "When the plugin loads, it will tell you in console which registries were registered."})
@Examples({"set {_attributes::*} to registry values of attribute type registry",
    "set {_biomes::*} to registry values of biome registry"})
@Since("INSERT VERSION")
public class ExprRegistryValues extends SimpleExpression<Object> {

    static {
        if (Skript.classExists("org.bukkit.Registry")) {
            Skript.registerExpression(ExprRegistryValues.class, Object.class, ExpressionType.COMBINED,
                "[all] registry values of %*classinfo% registry");
        }
    }

    private Literal<ClassInfo<?>> classInfo;
    private Registry<Keyed> registry;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.classInfo = (Literal<ClassInfo<?>>) exprs[0];
        ClassInfo<?> classInfo = this.classInfo.getSingle();
        Registry<Keyed> registry = RegistryRegistration.get(classInfo);
        if (registry == null) {
            Skript.error("No registry registered for '" + classInfo.getDocName() + "'");
            return false;
        }
        this.registry = registry;
        return true;
    }

    @Override
    protected @Nullable Object[] get(Event event) {
        List<Object> objects = new ArrayList<>();
        for (Keyed keyed : this.registry) {
            objects.add(keyed);
        }
        return objects.toArray();
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<?> getReturnType() {
        return this.classInfo.getReturnType();
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "registry values of " + this.classInfo.toString(event, debug) + " registry";
    }

}
