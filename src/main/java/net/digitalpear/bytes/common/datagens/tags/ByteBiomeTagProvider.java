package net.digitalpear.bytes.common.datagens.tags;

import net.digitalpear.bytes.init.ByteTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.world.biome.Biome;

import java.util.concurrent.CompletableFuture;

public class ByteBiomeTagProvider extends FabricTagProvider<Biome> {
    public ByteBiomeTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, RegistryKeys.BIOME, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(ByteTags.Biomes.SPAWNS_COLD_TURTLES)
                .forceAddTag(BiomeTags.OCEAN_RUIN_COLD_HAS_STRUCTURE)
                .forceAddTag(BiomeTags.SPAWNS_COLD_VARIANT_FROGS)
        ;
        getOrCreateTagBuilder(ByteTags.Biomes.SPAWNS_WARM_TURTLES)
                .forceAddTag(BiomeTags.OCEAN_RUIN_WARM_HAS_STRUCTURE)
                .forceAddTag(BiomeTags.SPAWNS_WARM_VARIANT_FROGS)
        ;
    }
}
