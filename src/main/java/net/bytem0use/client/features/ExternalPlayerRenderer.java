package net.bytem0use.client.features;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.client.render.entity.feature.*;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

//Courtesy of Maketendo
@Environment(EnvType.CLIENT)
public class ExternalPlayerRenderer extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {

    private final Identifier playerSkin;

    public ExternalPlayerRenderer(EntityRendererFactory.Context ctx, Identifier skin, boolean slim) {
        super(ctx, new PlayerEntityModel<>(ctx.getPart(slim ? EntityModelLayers.PLAYER_SLIM : EntityModelLayers.PLAYER), slim), 0.5F);
        this.playerSkin = skin;

        // Add vanilla-like layers
        this.addFeature(new ArmorFeatureRenderer<>(
                this,
                new BipedEntityModel<>(ctx.getPart(slim ? EntityModelLayers.PLAYER_SLIM_INNER_ARMOR : EntityModelLayers.PLAYER_INNER_ARMOR)),
                new BipedEntityModel<>(ctx.getPart(slim ? EntityModelLayers.PLAYER_SLIM_OUTER_ARMOR : EntityModelLayers.PLAYER_OUTER_ARMOR)),
                ctx.getModelManager()
        ));
        this.addFeature(new HeldItemFeatureRenderer<>(this, ctx.getHeldItemRenderer()));
        this.addFeature(new StuckArrowsFeatureRenderer<>(ctx, this));
        this.addFeature(new Deadmau5FeatureRenderer(this));
        this.addFeature(new CapeFeatureRenderer(this));
        this.addFeature(new HeadFeatureRenderer<>(this, ctx.getModelLoader(), ctx.getHeldItemRenderer()));
        this.addFeature(new ElytraFeatureRenderer<>(this, ctx.getModelLoader()));
        this.addFeature(new ShoulderParrotFeatureRenderer<>(this, ctx.getModelLoader()));
        this.addFeature(new TridentRiptideFeatureRenderer<>(this, ctx.getModelLoader()));
        this.addFeature(new StuckStingersFeatureRenderer<>(this));
    }

    @Override
    public void render(AbstractClientPlayerEntity player, float yaw, float tickDelta,
                       MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        this.setModelPose(player);
        super.render(player, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    private void setModelPose(AbstractClientPlayerEntity player) {
        PlayerEntityModel<AbstractClientPlayerEntity> model = this.getModel();

        if (player.isSpectator()) {
            model.setVisible(false);
            model.head.visible = true;
            model.hat.visible = true;
        } else {
            model.setVisible(true);
            model.hat.visible = player.isPartVisible(PlayerModelPart.HAT);
            model.jacket.visible = player.isPartVisible(PlayerModelPart.JACKET);
            model.leftPants.visible = player.isPartVisible(PlayerModelPart.LEFT_PANTS_LEG);
            model.rightPants.visible = player.isPartVisible(PlayerModelPart.RIGHT_PANTS_LEG);
            model.leftSleeve.visible = player.isPartVisible(PlayerModelPart.LEFT_SLEEVE);
            model.rightSleeve.visible = player.isPartVisible(PlayerModelPart.RIGHT_SLEEVE);
        }

        BipedEntityModel.ArmPose mainPose = getArmPose(player, Hand.MAIN_HAND);
        BipedEntityModel.ArmPose offPose = getArmPose(player, Hand.OFF_HAND);

        if (mainPose.isTwoHanded()) {
            offPose = player.getOffHandStack().isEmpty() ? BipedEntityModel.ArmPose.EMPTY : BipedEntityModel.ArmPose.ITEM;
        }

        if (player.getMainArm() == Arm.RIGHT) {
            model.rightArmPose = mainPose;
            model.leftArmPose = offPose;
        } else {
            model.rightArmPose = offPose;
            model.leftArmPose = mainPose;
        }
    }

    private static BipedEntityModel.ArmPose getArmPose(AbstractClientPlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if (stack.isEmpty()) return BipedEntityModel.ArmPose.EMPTY;

        if (player.getActiveHand() == hand && player.getItemUseTimeLeft() > 0) {
            UseAction action = stack.getUseAction();
            if (action == UseAction.BLOCK) return BipedEntityModel.ArmPose.BLOCK;
            if (action == UseAction.BOW) return BipedEntityModel.ArmPose.BOW_AND_ARROW;
            if (action == UseAction.SPEAR) return BipedEntityModel.ArmPose.THROW_SPEAR;
            if (action == UseAction.CROSSBOW && hand == player.getActiveHand())
                return BipedEntityModel.ArmPose.CROSSBOW_CHARGE;
        } else if (!player.handSwinging && stack.isOf(Items.CROSSBOW) && CrossbowItem.isCharged(stack)) {
            return BipedEntityModel.ArmPose.CROSSBOW_HOLD;
        }

        return BipedEntityModel.ArmPose.ITEM;
    }

    @Override
    public Identifier getTexture(AbstractClientPlayerEntity entity) {
        return playerSkin;
    }

    @Override
    protected void scale(AbstractClientPlayerEntity player, MatrixStack matrices, float tickDelta) {
        matrices.scale(0.9375F, 0.9375F, 0.9375F);
    }

    @Override
    protected void setupTransforms(AbstractClientPlayerEntity entity, MatrixStack matrices, float animationProgress, float bodyYaw, float tickDelta) {
        float f = entity.getLeaningPitch(tickDelta);
        super.setupTransforms(entity, matrices, animationProgress, bodyYaw, tickDelta);
        if (f > 0.0F) {
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(MathHelper.lerp(f, 0.0F, -90.0F - entity.getPitch())));
        }
    }
}
