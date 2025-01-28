package net.digitalpear.bytes.common.datagens;


import net.digitalpear.bytes.Bytes;
import net.digitalpear.bytes.common.blocks.TurtleToteBlock;
import net.digitalpear.bytes.init.ByteBlocks;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Block;
import net.minecraft.client.data.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

import java.util.Optional;

public class  ByteModelProvider extends FabricModelProvider {
    public static final Model TURTLE_TOTE = block("base_turtle_tote", TextureKey.TOP, TextureKey.BOTTOM, TextureKey.SIDE);

    private static Model block(String parent, TextureKey... requiredTextureKeys) {
        return new Model(Optional.of(Bytes.id("block/" + parent)), Optional.empty(), requiredTextureKeys);
    }

    public ByteModelProvider(FabricDataOutput output) {
        super(output);
    }



    public void registerTurtleTote(BlockStateModelGenerator blockStateModelGenerator, Block tote){
        Identifier toteModel = TURTLE_TOTE.upload(tote, TextureMap.sideTopBottom(tote), blockStateModelGenerator.modelCollector);
        Identifier toteModelOpen = TURTLE_TOTE.upload(tote, "_open", TextureMap.sideTopBottom(tote).put(TextureKey.TOP, TextureMap.getSubId(tote, "_top_open")), blockStateModelGenerator.modelCollector);
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(tote).coordinate(BlockStateModelGenerator.createBooleanModelMap(TurtleToteBlock.OPEN, toteModelOpen, toteModel)).coordinate(createDownDefaultRotationStates()));
    }

    public static BlockStateVariantMap createDownDefaultRotationStates() {
        return BlockStateVariantMap.create(TurtleToteBlock.FACING)
                .register(Direction.DOWN, BlockStateVariant.create())
                .register(Direction.UP, BlockStateVariant.create().put(VariantSettings.X, VariantSettings.Rotation.R180))
                .register(Direction.NORTH, BlockStateVariant.create().put(VariantSettings.X, VariantSettings.Rotation.R270))
                .register(Direction.SOUTH, BlockStateVariant.create().put(VariantSettings.X, VariantSettings.Rotation.R90))
                .register(Direction.WEST, BlockStateVariant.create().put(VariantSettings.X, VariantSettings.Rotation.R90).put(VariantSettings.Y, VariantSettings.Rotation.R90))
                .register(Direction.EAST, BlockStateVariant.create().put(VariantSettings.X, VariantSettings.Rotation.R90).put(VariantSettings.Y, VariantSettings.Rotation.R270));
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        registerTurtleTote(blockStateModelGenerator, ByteBlocks.TURTLE_TOTE);
        registerTurtleTote(blockStateModelGenerator, ByteBlocks.NETHERITE_TURTLE_TOTE);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {

    }
}
