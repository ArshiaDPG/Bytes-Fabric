package net.digitalpear.bytes.init;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;
@SuppressWarnings("all")
public class ByteStats {
    private static Identifier register(String id, StatFormatter formatter) {
        Identifier identifier = Identifier.ofVanilla(id);
        Registry.register(Registries.CUSTOM_STAT, id, identifier);
        Stats.CUSTOM.getOrCreateStat(identifier, formatter);
        return identifier;
    }

    public static final Identifier OPEN_TURTLE_TOTE = register("open_turtle_tote", StatFormatter.DEFAULT);

    public static void init() {

    }
}
