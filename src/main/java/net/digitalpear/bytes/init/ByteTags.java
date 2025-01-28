package net.digitalpear.bytes.init;

import net.digitalpear.bytes.Bytes;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.biome.Biome;

public class ByteTags {

    public static class Items {
        private static TagKey<Item> of(String id){
            return TagKey.of(RegistryKeys.ITEM, Bytes.id(id));
        }

        public static TagKey<Item> TURTLE_TOTE_MUSIC_DISCS = of("turtle_tote_music_discs");
        public static TagKey<Item> IRON_GOLEM_FOOD = of("iron_golem_food");
    }
    public static class Biomes{
        private static TagKey<Biome> of(String id){
            return TagKey.of(RegistryKeys.BIOME, Bytes.id(id));
        }

        public static TagKey<Biome> SPAWNS_COLD_TURTLES = of("spawns_cold_turtles");
        public static TagKey<Biome> SPAWNS_WARM_TURTLES = of("spawns_warm_turtles");
    }

    public static class EntityTypes {
        private static TagKey<EntityType<?>> of(String id){
            return TagKey.of(RegistryKeys.ENTITY_TYPE, Bytes.id(id));
        }

        public static TagKey<EntityType<?>> CUSTOM_VILLAGER_SPAWNED_GOLEMS = of("custom_villager_spawned_golems");

    }

}
