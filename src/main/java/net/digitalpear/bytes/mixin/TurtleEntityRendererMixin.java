package net.digitalpear.bytes.mixin;

import net.digitalpear.bytes.common.entity.TurtleVariant;
import net.digitalpear.bytes.common.entity.accessor.VariantAccess;
import net.digitalpear.bytes.init.TurtleVariants;
import net.minecraft.client.render.entity.TurtleEntityRenderer;
import net.minecraft.client.render.entity.state.TurtleEntityRenderState;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TurtleEntityRenderer.class)
public class TurtleEntityRendererMixin {

    @Inject(method = "getTexture(Lnet/minecraft/client/render/entity/state/TurtleEntityRenderState;)Lnet/minecraft/util/Identifier;", at = @At("RETURN"), cancellable = true)
    private void changeTexture(TurtleEntityRenderState turtleEntityRenderState, CallbackInfoReturnable<Identifier> cir){
        if (!(turtleEntityRenderState instanceof VariantAccess<?>)) {
            return;
        }
        VariantAccess<TurtleVariant> turtleVariantAccess = (VariantAccess<TurtleVariant>) turtleEntityRenderState;
        if (turtleVariantAccess.getVariant() != null){
            cir.setReturnValue(turtleVariantAccess.getVariant().value().getTexture().withSuffixedPath(".png"));
        }
    }

    @Inject(method = "updateRenderState(Lnet/minecraft/entity/passive/TurtleEntity;Lnet/minecraft/client/render/entity/state/TurtleEntityRenderState;F)V", at = @At("HEAD"), cancellable = true)
    private void applyChanges(TurtleEntity turtleEntity, TurtleEntityRenderState turtleEntityRenderState, float f, CallbackInfo ci){
        if (turtleEntity instanceof VariantAccess<?> && turtleEntityRenderState instanceof VariantAccess<?>){
            VariantAccess<TurtleVariant> entityAccess = (VariantAccess<TurtleVariant>) turtleEntity;
            VariantAccess<TurtleVariant> renderStateAccess = (VariantAccess<TurtleVariant>) turtleEntityRenderState;
            renderStateAccess.setVariant(entityAccess.getVariant());
        }
    }
}
