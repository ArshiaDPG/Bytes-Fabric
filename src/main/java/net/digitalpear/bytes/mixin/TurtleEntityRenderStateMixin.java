package net.digitalpear.bytes.mixin;

import net.digitalpear.bytes.common.entity.TurtleVariant;
import net.digitalpear.bytes.common.entity.accessor.VariantAccess;
import net.minecraft.client.render.entity.state.TurtleEntityRenderState;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(TurtleEntityRenderState.class)
public class TurtleEntityRenderStateMixin implements VariantAccess<TurtleVariant> {
    @Unique
    RegistryEntry<TurtleVariant> variant = null;

    @Override
    public RegistryEntry<TurtleVariant> getVariant() {
        return this.variant;
    }

    @Override
    public void setVariant(RegistryEntry<TurtleVariant> variant) {
        this.variant = variant;
    }
}
