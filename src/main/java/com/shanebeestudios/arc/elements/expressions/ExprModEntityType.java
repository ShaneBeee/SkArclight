package com.shanebeestudios.arc.elements.expressions;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.shanebeestudios.arc.api.data.ModEntityType;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Name("Modded Entity Type")
@Description("Gets the entity type of a modded entity. Uses their namespaced keys. Also supports vanilla entities.")
@Examples({"set {_e} to mod entity type of last spawned entity",
        "set {_e} to mod entity type of {_entity}",
        "if mod entity type of target entity = tfc:turkey:",
        "if mod entity type of target entity = minecraft:cow:"})
@Since("INSERT VERSION")
public class ExprModEntityType extends SimplePropertyExpression<Entity, ModEntityType> {

    static {
        register(ExprModEntityType.class, ModEntityType.class, "mod[ded] entity type", "entities");
    }

    @Override
    public @Nullable ModEntityType convert(Entity entity) {
        return ModEntityTypes.fromEntity(entity);
    }

    @Override
    public @NotNull Class<? extends ModEntityType> getReturnType() {
        return ModEntityType.class;
    }

    @Override
    protected @NotNull String getPropertyName() {
        return "mod entity type";
    }

}
