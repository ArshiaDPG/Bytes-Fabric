package net.digitalpear.bytes.common.datagens.loot;

import net.digitalpear.bytes.init.ByteLootTables;
import net.digitalpear.bytes.init.ByteTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.TagEntry;
import net.minecraft.loot.function.EnchantRandomlyLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.function.SetDamageLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class ByteChestLootTableProvider extends SimpleFabricLootTableProvider {

    public ByteChestLootTableProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(output, registryLookup, LootContextTypes.CHEST);
    }


    @Override
    public void accept(BiConsumer<RegistryKey<LootTable>, LootTable.Builder> biConsumer) {
        biConsumer.accept(ByteLootTables.TURTLE_TOTE_OCEAN_RUINS, LootTable.builder()
                .pool(LootPool.builder()
                        .rolls(UniformLootNumberProvider.create(3, 4))
                        .with(ItemEntry.builder(net.minecraft.item.Items.BREAD).weight(20).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2, 4))))
                        .with(ItemEntry.builder(net.minecraft.item.Items.TURTLE_SCUTE).weight(7).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(3, 5))))
                        .with(ItemEntry.builder(net.minecraft.item.Items.COAL).weight(12).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2, 6))))
                        .with(TagEntry.expandBuilder(ItemTags.FISHES).weight(8).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(3, 6))))
                )
                .pool(LootPool.builder()
                        .with(TagEntry.expandBuilder(ItemTags.BOATS).weight(8))
                        .with(TagEntry.expandBuilder(ItemTags.CHEST_BOATS).weight(7))
                        .with(ItemEntry.builder(net.minecraft.item.Items.FISHING_ROD).weight(2).apply(SetDamageLootFunction.builder(UniformLootNumberProvider.create(0.1F, 0.8F))).apply(EnchantRandomlyLootFunction.create()))
                        .with(TagEntry.expandBuilder(ByteTags.Items.TURTLE_TOTE_MUSIC_DISCS).weight(1))
                )
        );
    }
}
