package net.bytem0use.client.features;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class OverlaySkinFeature extends FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {

    private static final Identifier OVERLAY_TEXTURE =
            new Identifier("miraculous_mod", "textures/entities/skin_overlay/ladybug_suit_overlay.png");

    public OverlaySkinFeature(FeatureRendererContext<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> context) {
        super(context);
    }

    @Override
    public void render(MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers,
                       int light,
                       AbstractClientPlayerEntity player,
                       float limbAngle,
                       float limbDistance,
                       float tickDelta,
                       float animationProgress,
                       float headYaw,
                       float headPitch) {

        if (player.isInvisible()) return;

        PlayerEntityModel<AbstractClientPlayerEntity> model = this.getContextModel();
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(OVERLAY_TEXTURE));

        model.setAngles(player, limbAngle, limbDistance, animationProgress, headYaw, headPitch);

        model.head.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV);
        model.hat.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV);
        model.body.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV);
        model.rightLeg.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV);
        model.leftLeg.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV);

        model.rightArm.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV);
        model.leftArm.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV);
        model.rightSleeve.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV);
        model.leftSleeve.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV);
    }
}
