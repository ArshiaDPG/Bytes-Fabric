package net.digitalpear.bytes.common.datagens.tags;

import net.digitalpear.bytes.init.ByteEntityType;
import net.digitalpear.bytes.init.ByteTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.EntityTypeTags;

import java.util.concurrent.CompletableFuture;

public class ByteEntityTypeTagProvider extends FabricTagProvider<EntityType<?>> {
    public ByteEntityTypeTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, RegistryKeys.ENTITY_TYPE, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(ByteTags.EntityTypes.CUSTOM_VILLAGER_SPAWNED_GOLEMS)
                .add(ByteEntityType.BABY_IRON_GOLEM_ENTITY)
        ;
    }
}
