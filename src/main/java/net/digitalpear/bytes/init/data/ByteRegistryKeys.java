package net.digitalpear.bytes.init.data;

import net.digitalpear.bytes.common.entity.TurtleVariant;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class ByteRegistryKeys {

    public static final RegistryKey<Registry<TurtleVariant>> TURTLE_VARIANT = of("turtle_variant");

    private static <T> RegistryKey<Registry<T>> of(String id) {
        return RegistryKey.ofRegistry(Identifier.ofVanilla(id));
    }

    public static void init() {
        DynamicRegistries.registerSynced(TURTLE_VARIANT, TurtleVariant.CODEC);
    }
}
