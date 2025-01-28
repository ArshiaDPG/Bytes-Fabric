package net.digitalpear.bytes.common.datagens;

import net.digitalpear.bytes.common.entity.TurtleVariant;
import net.digitalpear.bytes.init.TurtleVariants;
import net.digitalpear.bytes.init.data.ByteRegistryKeys;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ByteTurtleVariantProvider extends FabricDynamicRegistryProvider {
    public ByteTurtleVariantProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
        TurtleVariants.variants.forEach(sheepVariantRegistryKey -> add(registries, entries, sheepVariantRegistryKey));
    }

    private void add(RegistryWrapper.WrapperLookup registries, Entries entries, RegistryKey<TurtleVariant> resourceKey) {
        RegistryWrapper.Impl<TurtleVariant> configuredFeatureRegistryLookup = registries.getOrThrow(ByteRegistryKeys.TURTLE_VARIANT);

        entries.add(resourceKey, configuredFeatureRegistryLookup.getOrThrow(resourceKey).value());
    }
    @Override
    public String getName() {
        return "turtle_variant";
    }
}
