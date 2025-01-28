package net.digitalpear.bytes.mixin;

import net.digitalpear.bytes.init.ByteTags;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LargeEntitySpawnHelper;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.brain.sensor.GolemLastSeenSensor;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collections;
import java.util.List;

@Mixin(VillagerEntity.class)
public abstract class IronGolemSummonOverrideMixin extends MerchantEntity {

    public IronGolemSummonOverrideMixin(EntityType<? extends MerchantEntity> entityType, World world) {
        super(entityType, world);
    }

    @Shadow public abstract boolean canSummonGolem(long time);

    @Inject(method = "summonGolem", at = @At("HEAD"), cancellable = true)
    private void init(ServerWorld world, long time, int requiredCount, CallbackInfo ci){
        if (world.getRandom().nextFloat() < 0.7){
            if (this.canSummonGolem(time)) {
                double boundingBoxExpansion = 5.0;
                Box box = this.getBoundingBox().expand(boundingBoxExpansion, boundingBoxExpansion, boundingBoxExpansion);
                List<VillagerEntity> list = world.getNonSpectatingEntities(VillagerEntity.class, box);
                List<VillagerEntity> list2 = list.stream().filter((villager) -> villager.canSummonGolem(time)).limit(5L).toList();
                if (list2.size() >= requiredCount-2) {
                    if (!LargeEntitySpawnHelper.trySpawnAt(generateGolem(), SpawnReason.MOB_SUMMONED, world, this.getBlockPos(), 10, 8, 6, LargeEntitySpawnHelper.Requirements.IRON_GOLEM, false).isEmpty()) {
                        list.forEach(GolemLastSeenSensor::rememberIronGolem);
                    }
                }
            }
            ci.cancel();
        }
    }
    @Unique
    private static <T extends MobEntity> EntityType<T> generateGolem(){
        List<EntityType<?>> validGolems = Registries.ENTITY_TYPE.stream().filter(entityType -> entityType.isIn(ByteTags.EntityTypes.CUSTOM_VILLAGER_SPAWNED_GOLEMS)).toList();
        if (validGolems.isEmpty()){
            return (EntityType<T>) EntityType.IRON_GOLEM;
        } else if (validGolems.size() == 1) {
            return (EntityType<T>) validGolems.get(0);
        }
        Collections.shuffle(validGolems);
        return (EntityType<T>) validGolems.stream().findFirst().get();
    }
}
