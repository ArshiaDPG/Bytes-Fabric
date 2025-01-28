package net.digitalpear.bytes;

import net.digitalpear.bytes.init.*;
import net.digitalpear.bytes.init.data.ByteRegistryKeys;
import net.digitalpear.bytes.init.data.BytesTrackedData;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.function.EnchantWithLevelsLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.function.SetDamageLootFunction;
import net.minecraft.loot.function.SetLootTableLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

import java.util.List;

public class Bytes implements ModInitializer {

    public static final String MOD_ID = "bytes";

    public static Identifier id(String value){
        return Identifier.of(MOD_ID, value);
    }
    public static final List<RegistryKey<LootTable>> TOTE_CHEST_IDS = List.of(
            LootTables.UNDERWATER_RUIN_SMALL_CHEST,
            LootTables.UNDERWATER_RUIN_BIG_CHEST
    );
    public static final List<RegistryKey<LootTable>> TOTE_ENTITY_IDS = List.of(
            EntityType.DROWNED.getLootTableKey().get()
    );

    /*
        Turtle Totes were originally created by notblue
        https://modrinth.com/datapack/turtle-totes
     */

    @Override
    public void onInitialize() {
        ByteBlocks.init();
        ByteBlockEntityType.init();
        ByteItems.init();
        ByteStats.init();
        ByteEntityType.init();
        ByteRegistryKeys.init();
        BytesTrackedData.init();
        TurtleVariants.init();

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(fabricItemGroupEntries -> {
            fabricItemGroupEntries.addBefore(Items.SHULKER_BOX, ByteBlocks.TURTLE_TOTE, ByteBlocks.NETHERITE_TURTLE_TOTE);
        });
        LootTableEvents.MODIFY.register((registryKey, builder, lootTableSource, wrapperLookup) -> {
            LeafEntry.Builder<?> entry = ItemEntry.builder(ByteBlocks.TURTLE_TOTE).apply(SetLootTableLootFunction.builder(ByteBlockEntityType.TURTLE_TOTE, ByteLootTables.TURTLE_TOTE_OCEAN_RUINS));
            if (lootTableSource.isBuiltin()){
                if (TOTE_CHEST_IDS.contains(registryKey)){
                    builder.pool(LootPool.builder()
                            .with(ItemEntry.builder(Items.TURTLE_HELMET).weight(3).apply(EnchantWithLevelsLootFunction.builder(wrapperLookup, UniformLootNumberProvider.create(10 ,14))).apply(SetDamageLootFunction.builder(UniformLootNumberProvider.create(0.1F, 0.8F))))
                            .with(entry.weight(2))
                            .with(ItemEntry.builder(Items.TURTLE_SCUTE).weight(6).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(4, 6))))
                            .build());
                }
                else if (TOTE_ENTITY_IDS.contains(registryKey)) {
                    builder.pool(LootPool.builder().with(entry).conditionally(RandomChanceLootCondition.builder(ConstantLootNumberProvider.create(0.02f))).build());
                }
            }
        });
    }
}
