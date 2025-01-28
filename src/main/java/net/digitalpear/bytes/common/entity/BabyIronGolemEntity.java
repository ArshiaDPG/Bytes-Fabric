package net.digitalpear.bytes.common.entity;

import net.digitalpear.bytes.init.ByteTags;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.UseRemainderComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.conversion.EntityConversionContext;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.Objects;

public class BabyIronGolemEntity extends IronGolemEntity {
    private int age = 0;

    public BabyIronGolemEntity(EntityType<? extends IronGolemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.age = nbt.getInt("age");
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("age", this.age);
    }

    public static DefaultAttributeContainer.Builder createBabyIronGolemAttributes() {
        return createIronGolemAttributes()
                .add(EntityAttributes.MAX_HEALTH, 50.0)
                .add(EntityAttributes.MOVEMENT_SPEED, 0.3)
                .add(EntityAttributes.KNOCKBACK_RESISTANCE, 0.4)
                .add(EntityAttributes.ATTACK_DAMAGE, 7.5)
                .add(EntityAttributes.STEP_HEIGHT, 0.5)
                ;
    }

    @Override
    public void tick() {
        super.tick();
        if (age >= ageForGrowth()){
            becomeIronGolem();
        }
    }

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack ingotStack = player.getStackInHand(hand);
        if (ingotStack.isIn(ByteTags.Items.IRON_GOLEM_FOOD)){
            if (this.getHealth() >= this.getMaxHealth()) {
                this.age += foodGrowth();
                this.eat(player, hand, ingotStack);
                float g = 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F;
                this.playSound(SoundEvents.ENTITY_IRON_GOLEM_REPAIR, 1.0F, g);
                return ActionResult.SUCCESS;
            }

        }
        return super.interactMob(player, hand);
    }

    protected void eat(PlayerEntity player, Hand hand, ItemStack stack) {
        int i = stack.getCount();
        UseRemainderComponent useRemainderComponent = stack.get(DataComponentTypes.USE_REMAINDER);
        stack.decrementUnlessCreative(1, player);
        if (useRemainderComponent != null) {
            boolean var10003 = player.isInCreativeMode();
            Objects.requireNonNull(player);
            ItemStack itemStack = useRemainderComponent.convert(stack, i, var10003, player::giveOrDropStack);
            player.setStackInHand(hand, itemStack);
        }
    }

    public int ageForGrowth(){
        return 2400;
    }

    public int foodGrowth(){return 100;}

    public void becomeIronGolem(){
        IronGolemEntity golemEntity = this.convertTo(EntityType.IRON_GOLEM, EntityConversionContext.create(this, true, true), (ironGolemEntity) -> {
            ironGolemEntity.setHealth(ironGolemEntity.getMaxHealth() * (this.getHealth()/this.getMaxHealth()));
            ironGolemEntity.copyPositionAndRotation(this);
            ironGolemEntity.setPersistent();
        });
    }
}
