package net.digitalpear.bytes.common.datagens.tags;



import net.digitalpear.bytes.init.ByteTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ByteItemTagProvider extends FabricTagProvider<Item> {

    public ByteItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, RegistryKeys.ITEM, registriesFuture);
    }


    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(ByteTags.Items.TURTLE_TOTE_MUSIC_DISCS)
                .add(
                        net.minecraft.item.Items.MUSIC_DISC_CAT, net.minecraft.item.Items.MUSIC_DISC_CHIRP
                );
        getOrCreateTagBuilder(ByteTags.Items.IRON_GOLEM_FOOD).add(Items.IRON_INGOT);
    }
}
