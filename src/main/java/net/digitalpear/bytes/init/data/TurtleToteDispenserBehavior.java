package net.digitalpear.bytes.init.data;

import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.item.AutomaticItemPlacementContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class TurtleToteDispenserBehavior extends FallibleItemDispenserBehavior {

    protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
        this.setSuccess(false);
        Item item = stack.getItem();
        if (item instanceof BlockItem) {
            Direction direction = pointer.state().get(DispenserBlock.FACING);
            BlockPos blockPos = pointer.pos().offset(direction);

            try {
                this.setSuccess(((BlockItem)item).place(new AutomaticItemPlacementContext(pointer.world(), blockPos, direction, stack, direction.getOpposite())).isAccepted());
            } catch (Exception ignored) {
            }
        }
        return stack;
    }
}
