package net.digitalpear.bytes.common.datagens;


import net.digitalpear.bytes.common.blocks.TurtleToteBlockEntity;
import net.digitalpear.bytes.init.ByteBlocks;
import net.digitalpear.bytes.init.ByteStats;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ByteLanguageProvider extends FabricLanguageProvider {

    public ByteLanguageProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }


    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add(ByteBlocks.TURTLE_TOTE, "Turtle Tote");
        translationBuilder.add(ByteBlocks.NETHERITE_TURTLE_TOTE, "Netherite Turtle Tote");
        translationBuilder.add(TurtleToteBlockEntity.TURTLE_TOTE_CONTAINER_NAME, "Turtle Tote");
        translationBuilder.add(ByteStats.OPEN_TURTLE_TOTE, "Open Turtle Tote");
    }
}
