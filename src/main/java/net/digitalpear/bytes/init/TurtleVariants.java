package net.digitalpear.bytes.init;

import net.digitalpear.bytes.Bytes;
import net.digitalpear.bytes.common.entity.TurtleVariant;
import net.digitalpear.bytes.init.data.ByteRegistryKeys;
import net.minecraft.client.render.entity.TurtleEntityRenderer;
import net.minecraft.entity.passive.WolfVariant;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class TurtleVariants {

    public static List<RegistryKey<TurtleVariant>> variants = new ArrayList<>();

    public static final RegistryKey<TurtleVariant> SEA = of("sea");
    public static final RegistryKey<TurtleVariant> WARM = of("warm");
    public static final RegistryKey<TurtleVariant> COLD = of("cold");


    private static RegistryKey<TurtleVariant> of(String id) {
        RegistryKey<TurtleVariant> variant = RegistryKey.of(ByteRegistryKeys.TURTLE_VARIANT, Bytes.id(id));
        variants.add(variant);
        return variant;
    }
    static void register(Registerable<TurtleVariant> registry, RegistryKey<TurtleVariant> key, TagKey<Biome> biomeTag) {
        register(registry, key, key.getValue().getPath(), registry.getRegistryLookup(RegistryKeys.BIOME).getOrThrow(biomeTag));
    }
    static void register(Registerable<TurtleVariant> registry, RegistryKey<TurtleVariant> key, Identifier texturePath, TagKey<Biome> biomeTag) {
        register(registry, key, texturePath, registry.getRegistryLookup(RegistryKeys.BIOME).getOrThrow(biomeTag));
    }
    static void register(Registerable<TurtleVariant> registry, RegistryKey<TurtleVariant> key, String textureName, TagKey<Biome> biomeTag) {
        register(registry, key, textureName, registry.getRegistryLookup(RegistryKeys.BIOME).getOrThrow(biomeTag));
    }

    static void register(Registerable<TurtleVariant> registry, RegistryKey<TurtleVariant> key, String textureName, RegistryEntryList<Biome> biomes) {
        Identifier identifier = Bytes.id("textures/entity/turtle/" + textureName);
        register(registry, key, identifier, biomes);
    }
    static void register(Registerable<TurtleVariant> registry, RegistryKey<TurtleVariant> key, Identifier textureName, RegistryEntryList<Biome> biomes) {
        registry.register(key, new TurtleVariant(textureName, biomes));
    }

    public static RegistryEntry<TurtleVariant> fromBiome(DynamicRegistryManager dynamicRegistryManager, RegistryEntry<Biome> biome) {
        Registry<TurtleVariant> registry = dynamicRegistryManager.getOrThrow(ByteRegistryKeys.TURTLE_VARIANT);
        Optional<RegistryEntry.Reference<TurtleVariant>> var10000 = registry.streamEntries().filter((entry) -> entry.value().getBiomes().contains(biome)).findFirst().or(() -> registry.getOptional(SEA));
        Objects.requireNonNull(registry);
        return var10000.or(registry::getDefaultEntry).orElseThrow();
    }

    public static void bootstrap(Registerable<TurtleVariant> registry) {
        register(registry, SEA, Identifier.ofVanilla("textures/entity/turtle/big_sea_turtle"), BiomeTags.IS_OVERWORLD);
        register(registry, WARM, ByteTags.Biomes.SPAWNS_WARM_TURTLES);
        register(registry, COLD, ByteTags.Biomes.SPAWNS_COLD_TURTLES);
    }

    public static void init() {

    }
}
