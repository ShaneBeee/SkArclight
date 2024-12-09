package com.shanebeestudios.arc.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.sections.EffSecSpawn;
import ch.njol.skript.util.Direction;
import ch.njol.util.Kleenean;
import com.shanebeestudios.arc.api.data.ModEntityType;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Name("Spawn Modded Entity")
@Description({"Spawn a modded entity.",
    "Keys with/without spaces/underscores/slashes are supported."})
@Examples({"mod spawn tfc:turkey at player",
    "mod spawn tfc:dog at location(1,100,1)",
    "mod spawn tfc:chest_boat/chestnut at {_loc}",
    "mod spawn tfc chest boat chestnut at {_loc}"})
@Since("1.0.0")
public class EffModSpawnEntity extends Effect {

    static {
        Skript.registerEffect(EffModSpawnEntity.class,
            "mod[ded] spawn [%-number% of] %modentitytype% [%directions% %locations%]");
    }

    private Expression<Number> amount;
    private Expression<ModEntityType> type;
    private Expression<Location> location;

    @SuppressWarnings({"unchecked"})
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        this.amount = (Expression<Number>) exprs[0];
        this.type = (Expression<ModEntityType>) exprs[1];
        this.location = Direction.combine((Expression<? extends Direction>) exprs[2], (Expression<? extends Location>) exprs[3]);
        return true;
    }

    @SuppressWarnings({"DataFlowIssue"})
    @Override
    protected void execute(Event event) {
        int amount = this.amount != null ? this.amount.getSingle(event).intValue() : 1;
        ModEntityType type = this.type.getSingle(event);
        Location location = this.location.getSingle(event);

        for (int i = 0; i < amount; i++) {
            EffSecSpawn.lastSpawned = type.spawn(location);
        }
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean d) {
        String amount = this.amount != null ? (this.amount.toString(e, d) + " of ") : "";
        return "mod spawn " + amount + this.type.toString(e, d) + this.location.toString(e, d);
    }

}
