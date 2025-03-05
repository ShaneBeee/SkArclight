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
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.shanebeestudios.arc.api.data.RegistryRegistration;
import com.shanebeestudios.arc.api.util.Util;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Name("Registry - Object from")
@Description({"Get an object from a Minecraft registry.",
    "When the plugin loads, it will tell you in console which registries were registered."})
@Examples({"set {_attribute} to \"some_mod:some_attribute\" from attribute type registry",
    "set {_biome} to \"some_mod:custom_biome\" from biome registry"})
@Since("INSERT VERSION")
public class ExprRegistryObject extends SimpleExpression<Object> {

    static {
        if (Skript.classExists("org.bukkit.Registry")) {
            Skript.registerExpression(ExprRegistryObject.class, Object.class, ExpressionType.COMBINED,
                "%string% from %*classinfo% registry");
        }
    }

    private Expression<String> key;
    private Literal<ClassInfo<?>> classInfo;
    private Registry<Keyed> registry;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        this.key = (Expression<String>) exprs[0];
        this.classInfo = (Literal<ClassInfo<?>>) exprs[1];
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
        String stringKey = this.key.getSingle(event);
        if (stringKey == null) return null;

        NamespacedKey key = Util.getNamespacedKey(stringKey, false);
        if (key == null) return null;

        Keyed object = this.registry.get(key);
        if (object != null) return new Object[]{object};

        return null;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<?> getReturnType() {
        return this.classInfo.getReturnType();
    }

    @Override
    public String toString(@Nullable Event e, boolean d) {
        return this.key.toString(e, d) + " from " + this.classInfo.toString(e, d) + " registry";
    }

}
