package net.digitalpear.bytes.common.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.IronGolemEntityRenderer;
import net.minecraft.client.render.entity.model.IronGolemEntityModel;
import net.minecraft.client.render.entity.state.IronGolemEntityRenderState;
import net.minecraft.entity.passive.IronGolemEntity;

public class BabyIronGolemEntityRenderer extends IronGolemEntityRenderer {
    public BabyIronGolemEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }
}
