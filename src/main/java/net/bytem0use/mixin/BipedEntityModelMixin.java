package net.bytem0use.mixin;

import net.bytem0use.common.api.abilities.base.PowerAPI;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({BipedEntityModel.class})
public class BipedEntityModelMixin<T extends LivingEntity> {

    @Unique
    private PowerAPI powerAPI;

    @Unique
    private PlayerAbilities playerAbilities = new PlayerAbilities();

    @Unique
    public ModelPart rightArm;

    @Unique
    public ModelPart leftArm;

    @Unique
    public ModelPart body;

    @Inject(method = "positionRightArm", at = @At("HEAD"), cancellable = true)
    private void injectedCodeLine(LivingEntity entity, CallbackInfo ci) {
        if(entity.hasStatusEffect(powerAPI) && playerAbilities.flying) {
            this.rightArm.pitch = this.rightArm.pitch * 6.0F - (float) (Math.PI / 6);
            this.rightArm.yaw = this.rightArm.yaw * 4.2f - (float) (Math.PI / 6);

            ci.cancel();
        }



    }
}
