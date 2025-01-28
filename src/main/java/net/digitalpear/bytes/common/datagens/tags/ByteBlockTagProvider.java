package net.digitalpear.bytes.common.datagens.tags;


import net.digitalpear.bytes.init.ByteBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class ByteBlockTagProvider extends FabricTagProvider<Block> {


    public ByteBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, RegistryKeys.BLOCK, registriesFuture);
    }


    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(ByteBlocks.TURTLE_TOTE, ByteBlocks.NETHERITE_TURTLE_TOTE);
        getOrCreateTagBuilder(BlockTags.PREVENT_MOB_SPAWNING_INSIDE).add(ByteBlocks.TURTLE_TOTE, ByteBlocks.NETHERITE_TURTLE_TOTE);
        getOrCreateTagBuilder(BlockTags.NEEDS_DIAMOND_TOOL).add(ByteBlocks.NETHERITE_TURTLE_TOTE);
    }
}
