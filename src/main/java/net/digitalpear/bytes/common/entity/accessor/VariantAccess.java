package net.digitalpear.bytes.common.entity.accessor;

import net.minecraft.registry.entry.RegistryEntry;

public interface VariantAccess<T> {

    RegistryEntry<T> getVariant();
    void setVariant(RegistryEntry<T> variant);
}
