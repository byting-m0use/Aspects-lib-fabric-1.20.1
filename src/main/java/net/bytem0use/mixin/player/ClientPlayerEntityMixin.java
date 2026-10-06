package net.bytem0use.mixin.player;

import net.bytem0use.common.utils.FlyingState;
import net.bytem0use.common.utils.PlayerFlightInterface;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;

@Environment(EnvType.CLIENT)
@Mixin({PlayerEntity.class})
public class ClientPlayerEntityMixin implements PlayerFlightInterface {

    @Override
    public FlyingState getFlightState() {
        return null;
    }

    @Override
    public void setFlightState(FlyingState state1) {

    }

    @Override
    public int getFlightTicks() {
        return 0;
    }

    @Override
    public void setFlightTicks(int var1) {

    }

    @Override
    public boolean isFlying() {
        return false;
    }

    @Override
    public void getIsFlying(boolean bool1) {

    }

    @Override
    public int getFlightSpeed() {
        return 0;
    }

    @Override
    public void setFlightSpeed(int var1) {

    }

    @Override
    public boolean isHovering() {
        return false;
    }

    @Override
    public void getIsHovering(boolean bool2) {

    }

    @Override
    public boolean clientIsLocalPlayer(boolean bool) {
        return false;
    }

    @Override
    public void setTakeoffTicks(int int2) {

    }

    @Override
    public boolean isFlightAccelerating() {
        return false;
    }

    @Override
    public void setFlightAccelerating(boolean var1) {

    }
}
