package net.digitalpear.bytes.common.blocks;


import net.minecraft.block.Block;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemUsage;

public class TurtleToteBlockItem extends BlockItem {


    public TurtleToteBlockItem(Block block, Settings settings) {
        super(block, settings);
    }

    public void onItemEntityDestroyed(ItemEntity entity) {
        ContainerComponent containerComponent = entity.getStack().set(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT);
        if (containerComponent != null) {
            ItemUsage.spawnItemContents(entity, containerComponent.iterateNonEmptyCopy());
        }
    }
}
