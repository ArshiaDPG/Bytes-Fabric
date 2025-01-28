package net.digitalpear.bytes.common.entity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.registry.RegistryCodecs;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

import java.util.Objects;

public class TurtleVariant {

    public static final Codec<TurtleVariant> CODEC = RecordCodecBuilder.create((instance) ->
            instance.group(
                    Identifier.CODEC.fieldOf("texture").forGetter((turtleVariant) -> turtleVariant.texture),
                    RegistryCodecs.entryList(RegistryKeys.BIOME).fieldOf("biomes").orElse(RegistryEntryList.empty()).forGetter(turtleVariant -> turtleVariant.biomes)
            ).apply(instance, TurtleVariant::new));

    private final Identifier texture;
    private final RegistryEntryList<Biome> biomes;

    public TurtleVariant(Identifier texture, RegistryEntryList<Biome> biomes){
        this.texture = texture;
        this.biomes = biomes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TurtleVariant that = (TurtleVariant) o;
        return Objects.equals(texture, that.texture) && Objects.equals(biomes, that.biomes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(texture, biomes);
    }

    public Identifier getTexture() {
        return texture;
    }

    public RegistryEntryList<Biome> getBiomes() {
        return biomes;
    }
}
