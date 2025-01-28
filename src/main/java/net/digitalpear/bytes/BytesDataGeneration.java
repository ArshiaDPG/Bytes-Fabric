package net.digitalpear.bytes;

import net.digitalpear.bytes.common.datagens.*;
import net.digitalpear.bytes.common.datagens.loot.ByteBlockLootTableProvider;
import net.digitalpear.bytes.common.datagens.loot.ByteChestLootTableProvider;
import net.digitalpear.bytes.common.datagens.loot.ByteEntityLootTableProvider;
import net.digitalpear.bytes.common.datagens.tags.ByteBiomeTagProvider;
import net.digitalpear.bytes.common.datagens.tags.ByteBlockTagProvider;
import net.digitalpear.bytes.common.datagens.tags.ByteEntityTypeTagProvider;
import net.digitalpear.bytes.common.datagens.tags.ByteItemTagProvider;
import net.digitalpear.bytes.init.TurtleVariants;
import net.digitalpear.bytes.init.data.ByteRegistryKeys;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;

public class BytesDataGeneration implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(ByteBlockTagProvider::new);
        pack.addProvider(ByteItemTagProvider::new);
        pack.addProvider(ByteBiomeTagProvider::new);
        pack.addProvider(ByteEntityTypeTagProvider::new);

        pack.addProvider(ByteBlockLootTableProvider::new);
        pack.addProvider(ByteChestLootTableProvider::new);
        pack.addProvider(ByteEntityLootTableProvider::new);

        pack.addProvider(ByteModelProvider::new);
        pack.addProvider(ByteRecipeProvider::new);
        pack.addProvider(ByteLanguageProvider::new);

        pack.addProvider(ByteTurtleVariantProvider::new);
    }

    @Override
    public void buildRegistry(RegistryBuilder registryBuilder) {
        registryBuilder.addRegistry(ByteRegistryKeys.TURTLE_VARIANT, TurtleVariants::bootstrap);
    }
}
