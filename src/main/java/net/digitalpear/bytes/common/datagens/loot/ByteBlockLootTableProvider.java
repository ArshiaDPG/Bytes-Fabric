package net.digitalpear.bytes.common.datagens.loot;



import net.digitalpear.bytes.init.ByteBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.CopyComponentsLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ByteBlockLootTableProvider extends FabricBlockLootTableProvider {

    public ByteBlockLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {

        addDrop(ByteBlocks.TURTLE_TOTE, turtleToteDrops(ByteBlocks.TURTLE_TOTE));
        addDrop(ByteBlocks.NETHERITE_TURTLE_TOTE, turtleToteDrops(ByteBlocks.NETHERITE_TURTLE_TOTE));
    }

    public LootTable.Builder turtleToteDrops(Block drop) {
        return LootTable.builder().pool(this.addSurvivesExplosionCondition(drop, LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1.0F)).with(ItemEntry.builder(drop)
                        .apply(CopyComponentsLootFunction.builder(CopyComponentsLootFunction.Source.BLOCK_ENTITY)
                                .include(DataComponentTypes.CUSTOM_NAME).include(DataComponentTypes.CONTAINER)
                                .include(DataComponentTypes.LOCK).include(DataComponentTypes.CONTAINER_LOOT)))));
    }

}
