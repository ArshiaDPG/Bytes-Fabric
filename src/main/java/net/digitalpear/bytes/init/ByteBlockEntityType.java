package net.digitalpear.bytes.init;

import net.digitalpear.bytes.Bytes;
import net.digitalpear.bytes.common.blocks.TurtleToteBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ByteBlockEntityType {

    public static final BlockEntityType<TurtleToteBlockEntity> TURTLE_TOTE = register("turtle_tote", FabricBlockEntityTypeBuilder.create(TurtleToteBlockEntity::new, ByteBlocks.TURTLE_TOTE, ByteBlocks.NETHERITE_TURTLE_TOTE).build());

    public static <T extends BlockEntityType<?>> T register(String path, T blockEntityType) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, Bytes.id(path), blockEntityType);
    }

    public static void init() {

    }
}
