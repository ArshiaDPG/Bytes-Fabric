package net.digitalpear.bytes.init;

import net.digitalpear.bytes.Bytes;
import net.digitalpear.bytes.common.blocks.TurtleToteBlock;
import net.digitalpear.bytes.common.blocks.TurtleToteBlockItem;
import net.digitalpear.bytes.init.data.TurtleToteDispenserBehavior;
import net.minecraft.block.*;
import net.minecraft.block.dispenser.BlockPlacementDispenserBehavior;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;

import java.util.function.BiFunction;
import java.util.function.Function;

public class ByteBlocks {

    public static Block register(String name, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings, BiFunction<Block, Item.Settings, Item> itemFunction, Item.Settings itemSettings){
        Block block = Blocks.register(RegistryKey.of(RegistryKeys.BLOCK, Bytes.id(name)), factory, settings);
        Items.register(block, itemFunction, itemSettings);
        return block;
    }
    public static Block register(String name, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings){
        return register(name, factory, settings, BlockItem::new, new Item.Settings());
    }



    public static final Block TURTLE_TOTE = register("turtle_tote", TurtleToteBlock::new,
            AbstractBlock.Settings.create().sounds(BlockSoundGroup.TUFF)
                    .strength(1.5f, 20).nonOpaque().pistonBehavior(PistonBehavior.DESTROY)
                    .mapColor(MapColor.EMERALD_GREEN).suffocates((blockState, blockGetter, blockPos) -> false),
            TurtleToteBlockItem::new,
            new Item.Settings().maxCount(1)
    );

    public static final Block NETHERITE_TURTLE_TOTE = register("netherite_turtle_tote", TurtleToteBlock::new,
            AbstractBlock.Settings.create().requiresTool().strength(30.0F, 1200.0F).sounds(BlockSoundGroup.NETHERITE)
                    .strength(7).nonOpaque().pistonBehavior(PistonBehavior.DESTROY)
                    .mapColor(MapColor.EMERALD_GREEN).suffocates((blockState, blockGetter, blockPos) -> false),
            TurtleToteBlockItem::new,
            new Item.Settings().maxCount(1).fireproof()
    );

    public static void init(){
        DispenserBlock.registerBehavior(TURTLE_TOTE, new TurtleToteDispenserBehavior());
        DispenserBlock.registerBehavior(NETHERITE_TURTLE_TOTE, new TurtleToteDispenserBehavior());
    }
}
