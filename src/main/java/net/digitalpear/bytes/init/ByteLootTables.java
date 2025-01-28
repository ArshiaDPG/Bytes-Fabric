package net.digitalpear.bytes.init;

import net.digitalpear.bytes.Bytes;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public class ByteLootTables {

    public static final RegistryKey<LootTable> TURTLE_TOTE_OCEAN_RUINS = register("chests/turtle_tote_ocean_ruins");


    private static RegistryKey<LootTable> register(String name){
        return RegistryKey.of(RegistryKeys.LOOT_TABLE, Bytes.id(name));
    }
}
