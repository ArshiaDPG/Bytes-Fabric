package net.digitalpear.bytes.init.data;

import net.digitalpear.bytes.common.entity.TurtleVariant;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.entry.RegistryEntry;

public class BytesTrackedData {
    public static final TrackedDataHandler<RegistryEntry<TurtleVariant>> TURTLE_VARIANT = TrackedDataHandler.create(PacketCodecs.registryEntry(ByteRegistryKeys.TURTLE_VARIANT));

    public static void init() {
        TrackedDataHandlerRegistry.register(TURTLE_VARIANT);
    }
}
