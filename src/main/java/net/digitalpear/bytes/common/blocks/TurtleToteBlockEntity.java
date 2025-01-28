package net.digitalpear.bytes.common.blocks;


import net.digitalpear.bytes.init.ByteBlockEntityType;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.block.entity.ViewerCountManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.IntStream;

public class TurtleToteBlockEntity extends LootableContainerBlockEntity implements SidedInventory {

    private DefaultedList<ItemStack> inventory;
    private final ViewerCountManager stateManager;

    public static final String TURTLE_TOTE_CONTAINER_NAME = "container.turtle_tote";

    public final int INVENTORY_SIZE;
    private final int[] AVAILABLE_SLOTS;
    private final int rows;
    private static final List<ScreenHandlerType<GenericContainerScreenHandler>> CONTAINERS = List.of(
            ScreenHandlerType.GENERIC_9X1,
            ScreenHandlerType.GENERIC_9X2,
            ScreenHandlerType.GENERIC_9X3,
            ScreenHandlerType.GENERIC_9X4,
            ScreenHandlerType.GENERIC_9X5,
            ScreenHandlerType.GENERIC_9X6
    );
    public TurtleToteBlockEntity(BlockPos pos, BlockState state){
        this(pos, state, 1);
    }

    public TurtleToteBlockEntity(BlockPos pos, BlockState state, int rows) {
        super(ByteBlockEntityType.TURTLE_TOTE, pos, state);
        INVENTORY_SIZE = 9 * rows;
        AVAILABLE_SLOTS = IntStream.range(0, INVENTORY_SIZE).toArray();

        this.rows = rows;

        this.inventory = DefaultedList.ofSize(INVENTORY_SIZE, ItemStack.EMPTY);
        this.stateManager = new ViewerCountManager() {
            protected void onContainerOpen(World world, BlockPos pos, BlockState state) {
                TurtleToteBlockEntity.this.playSound(state, SoundEvents.ENTITY_TURTLE_EGG_CRACK);
                TurtleToteBlockEntity.this.setOpen(state, true);
            }

            protected void onContainerClose(World world, BlockPos pos, BlockState state) {
                TurtleToteBlockEntity.this.playSound(state, SoundEvents.ENTITY_TURTLE_EGG_CRACK);
                TurtleToteBlockEntity.this.setOpen(state, false);
            }

            protected void onViewerCountUpdate(World world, BlockPos pos, BlockState state, int oldViewerCount, int newViewerCount) {
            }

            protected boolean isPlayerViewing(PlayerEntity player) {
                if (player.currentScreenHandler instanceof GenericContainerScreenHandler) {
                    Inventory inventory = ((GenericContainerScreenHandler)player.currentScreenHandler).getInventory();
                    return inventory == TurtleToteBlockEntity.this;
                } else {
                    return false;
                }
            }
        };
    }


    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.writeNbt(nbt, registries);
        if (!this.writeLootTable(nbt)) {
            Inventories.writeNbt(nbt, this.inventory, registries);
        }

    }

    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.readNbt(nbt, registries);
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        if (!this.readLootTable(nbt)) {
            Inventories.readNbt(nbt, this.inventory, registries);
        }

    }

    public int size() {
        return INVENTORY_SIZE;
    }

    protected DefaultedList<ItemStack> getHeldStacks() {
        return this.inventory;
    }

    protected void setHeldStacks(DefaultedList<ItemStack> inventory) {
        this.inventory = inventory;
    }

    protected Text getContainerName() {
        return Text.translatable(TURTLE_TOTE_CONTAINER_NAME);
    }

    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new GenericContainerScreenHandler(CONTAINERS.get(this.rows-1), syncId, playerInventory, this, INVENTORY_SIZE/9);
    }

    public void onOpen(PlayerEntity player) {
        if (!this.removed && !player.isSpectator()) {
            this.stateManager.openContainer(player, this.getWorld(), this.getPos(), this.getCachedState());
        }

    }


    public void onClose(PlayerEntity player) {
        if (!this.removed && !player.isSpectator()) {
            this.stateManager.closeContainer(player, this.getWorld(), this.getPos(), this.getCachedState());
        }

    }

    public void tick() {
        if (!this.removed) {
            this.stateManager.updateViewerCount(this.getWorld(), this.getPos(), this.getCachedState());
        }
    }

    void setOpen(BlockState state, boolean open) {
        this.world.setBlockState(this.getPos(), state.with(BarrelBlock.OPEN, open), 3);
    }

    void playSound(BlockState state, SoundEvent soundEvent) {
        Vec3i vec3i = state.get(BarrelBlock.FACING).getVector();
        double d = (double)this.pos.getX() + 0.5 + (double)vec3i.getX() / 2.0;
        double e = (double)this.pos.getY() + 0.5 + (double)vec3i.getY() / 2.0;
        double f = (double)this.pos.getZ() + 0.5 + (double)vec3i.getZ() / 2.0;
        this.world.playSound(null, d, e, f, soundEvent, SoundCategory.BLOCKS, 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        return AVAILABLE_SLOTS;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        if (stack.getItem() instanceof BlockItem blockItem){
            if (blockItem.getBlock() instanceof TurtleToteBlock){
                return false;
            }
        }
        if (dir == null){
            return false;
        }
        return this.getWorld().getBlockState(this.getPos()).get(TurtleToteBlock.FACING) == dir;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        if (dir == null){
            return false;
        }
        return this.getWorld().getBlockState(this.getPos()).get(TurtleToteBlock.FACING) == dir;
    }

}
