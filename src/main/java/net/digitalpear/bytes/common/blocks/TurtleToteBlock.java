package net.digitalpear.bytes.common.blocks;

import com.mojang.serialization.MapCodec;
import net.digitalpear.bytes.Bytes;
import net.digitalpear.bytes.init.ByteStats;
import net.minecraft.block.*;
import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;


public class TurtleToteBlock extends BlockWithEntity implements Waterloggable {
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final EnumProperty<Direction> FACING = Properties.FACING;
    public static final BooleanProperty OPEN = Properties.OPEN;

    private static final Text UNKNOWN_CONTENTS_TEXT = Text.translatable("container.shulkerBox.unknownContents");
    public static final MapCodec<TurtleToteBlock> CODEC = createCodec(TurtleToteBlock::new);

    private final int rows;

    protected static final Map<Direction, VoxelShape> SHAPE = Map.of(
            Direction.DOWN, Block.createCuboidShape(3, 0, 3, 13, 10, 13),
            Direction.SOUTH, Block.createCuboidShape(3, 3, 6, 13, 13, 16),
            Direction.NORTH, Block.createCuboidShape(3, 3, 0, 13, 13, 10),
            Direction.EAST, Block.createCuboidShape(6, 3, 3, 16, 13, 13),
            Direction.WEST, Block.createCuboidShape(0, 3, 3, 10, 13, 13),
            Direction.UP, Block.createCuboidShape(3, 6, 3, 13, 16, 13)
    );

    public TurtleToteBlock(AbstractBlock.Settings properties) {
        this(properties, 1);
    }

    public TurtleToteBlock(AbstractBlock.Settings properties, int rows) {
        super(properties);
        this.rows = rows;
        this.setDefaultState(this.getDefaultState().with(FACING, Direction.DOWN).with(WATERLOGGED, false).with(OPEN, false));
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, OPEN, WATERLOGGED);
    }


    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (world instanceof ServerWorld serverWorld) {
            BlockEntity var8 = world.getBlockEntity(pos);
            if (var8 instanceof TurtleToteBlockEntity turtleToteBlockEntity) {
                player.openHandledScreen(turtleToteBlockEntity);
                player.incrementStat(ByteStats.OPEN_TURTLE_TOTE);
                PiglinBrain.onGuardedBlockInteracted(serverWorld, player, true);
            }
        }

        return ActionResult.SUCCESS;
    }
    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof TurtleToteBlockEntity) {
            ((TurtleToteBlockEntity)blockEntity).tick();
        }
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE.get(state.get(FACING));
    }


    @Override
    protected boolean canPathfindThrough(BlockState state, NavigationType type) {
        return false;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getDefaultState() : Fluids.EMPTY.getDefaultState();
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        World world = ctx.getWorld();
        BlockPos pos = ctx.getBlockPos();
        return super.getPlacementState(ctx).with(WATERLOGGED, world.isWater(pos)).with(FACING, ctx.getSide().getOpposite());
    }

    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof TurtleToteBlockEntity turtleToteBlockEntity) {
            if (!world.isClient && player.isCreative() && !turtleToteBlockEntity.isEmpty()) {
                ItemStack itemStack = new ItemStack(this);
                itemStack.applyComponentsFrom(blockEntity.createComponentMap());
                ItemEntity itemEntity = new ItemEntity(world, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, itemStack);
                itemEntity.setToDefaultPickupDelay();
                world.spawnEntity(itemEntity);
            } else {
                turtleToteBlockEntity.generateLoot(player);
            }
        }

        return super.onBreak(world, pos, state, player);
    }
    protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            super.onStateReplaced(state, world, pos, newState, moved);
            if (blockEntity instanceof TurtleToteBlockEntity) {
                world.updateComparators(pos, state.getBlock());
            }
        }
    }

    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
        super.appendTooltip(stack, context, tooltip, options);
        if (stack.contains(DataComponentTypes.CONTAINER_LOOT)) {
            tooltip.add(UNKNOWN_CONTENTS_TEXT);
        }

        int i = 0;
        int j = 0;

        for (ItemStack itemStack : stack.getOrDefault(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT).iterateNonEmpty()) {
            ++j;
            if (i <= 4) {
                ++i;
                tooltip.add(Text.translatable("container.shulkerBox.itemCount", itemStack.getName(), itemStack.getCount()));
            }
        }

        if (j - i > 0) {
            tooltip.add(Text.translatable("container.shulkerBox.more", j - i).formatted(Formatting.ITALIC));
        }

    }
    protected boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    protected int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos));
    }
    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new TurtleToteBlockEntity(pos, state, this.rows);
    }
}
