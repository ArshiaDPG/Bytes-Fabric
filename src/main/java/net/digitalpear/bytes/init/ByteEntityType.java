package net.digitalpear.bytes.init;

import net.digitalpear.bytes.Bytes;
import net.digitalpear.bytes.common.entity.BabyIronGolemEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public class ByteEntityType {

    public static final EntityType<BabyIronGolemEntity> BABY_IRON_GOLEM_ENTITY = register("baby_iron_golem", EntityType.Builder.create(BabyIronGolemEntity::new, SpawnGroup.MISC).dimensions(0.8F, 1.8F).maxTrackingRange(10));

    private static <T extends Entity> EntityType<T> register(String name, EntityType.Builder<T> type) {
        RegistryKey<EntityType<?>> key = RegistryKey.of(RegistryKeys.ENTITY_TYPE, Bytes.id(name));
        return Registry.register(Registries.ENTITY_TYPE, key, type.build(key));
    }

    public static void init() {
        FabricDefaultAttributeRegistry.register(BABY_IRON_GOLEM_ENTITY, BabyIronGolemEntity.createBabyIronGolemAttributes());
    }
}
