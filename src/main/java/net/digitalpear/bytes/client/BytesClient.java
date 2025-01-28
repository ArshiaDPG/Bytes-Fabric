package net.digitalpear.bytes.client;

import net.digitalpear.bytes.common.entity.BabyIronGolemEntityRenderer;
import net.digitalpear.bytes.init.ByteEntityType;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class BytesClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ByteEntityType.BABY_IRON_GOLEM_ENTITY, BabyIronGolemEntityRenderer::new);
    }
}
