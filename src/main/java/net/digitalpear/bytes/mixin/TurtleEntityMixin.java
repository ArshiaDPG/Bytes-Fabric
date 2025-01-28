package net.digitalpear.bytes.mixin;


import net.digitalpear.bytes.common.entity.TurtleVariant;
import net.digitalpear.bytes.common.entity.accessor.VariantAccess;
import net.digitalpear.bytes.init.TurtleVariants;
import net.digitalpear.bytes.init.data.ByteRegistryKeys;
import net.digitalpear.bytes.init.data.BytesTrackedData;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TurtleEntity.class)
public class TurtleEntityMixin extends AnimalEntity implements VariantAccess<TurtleVariant> {
    @Unique
    private static final TrackedData<RegistryEntry<TurtleVariant>> VARIANT = DataTracker.registerData(TurtleEntityMixin.class, BytesTrackedData.TURTLE_VARIANT);

    protected TurtleEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }



    @Inject(method = "onGrowUp", at = @At("HEAD"))
    private void setVariantAfterGrowing(CallbackInfo ci){
        this.setVariant(TurtleVariants.fromBiome(this.getRegistryManager(), this.getWorld().getBiome(this.getBlockPos())));
    }


    @Override
    public RegistryEntry<TurtleVariant> getVariant() {
        return this.dataTracker.get(VARIANT);
    }

    @Override
    public void setVariant(RegistryEntry<TurtleVariant> variant) {
        this.dataTracker.set(VARIANT, variant);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isIn(ItemTags.TURTLE_FOOD);
    }


    @Inject(at = @At("RETURN"), method = "initialize")
    private void init(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, EntityData entityData, CallbackInfoReturnable<EntityData> cir){
        if (this.isBaby() || spawnReason == SpawnReason.BREEDING){
            this.setVariant(this.getRegistryManager().getOrThrow(ByteRegistryKeys.TURTLE_VARIANT).getOrThrow(TurtleVariants.SEA));
        }
        else{
            this.setVariant(TurtleVariants.fromBiome(this.getRegistryManager(), this.getWorld().getBiome(this.getBlockPos())));
        }
    }

    @Inject(at = @At("HEAD"), method = "writeCustomDataToNbt")
    public void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
        nbt.putString("variant", this.getVariant().getKey().get().getValue().toString());
    }

    @Inject(at = @At("HEAD"), method = "readCustomDataFromNbt")
    public void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
        RegistryEntry<TurtleVariant> sheepVariant = this.getRegistryManager().getOrThrow(ByteRegistryKeys.TURTLE_VARIANT).getEntry(Identifier.tryParse(nbt.getString("variant"))).get();
        this.setVariant(sheepVariant);
    }
    @Inject(at = @At("HEAD"), method = "initDataTracker")
    private void addData(DataTracker.Builder builder, CallbackInfo ci){
        builder.add(VARIANT, this.getRegistryManager().getOrThrow(ByteRegistryKeys.TURTLE_VARIANT).getEntry(TurtleVariants.SEA.getValue()).get());
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return EntityType.TURTLE.create(world, SpawnReason.BREEDING);
    }
}
