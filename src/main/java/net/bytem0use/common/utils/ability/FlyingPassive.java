package net.bytem0use.common.utils.ability;

import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;

public interface FlyingPassive {

    boolean isFlying(ServerPlayerEntity player);

    default void createFlyingParticles(ServerPlayerEntity serverPlayer) {}

    void setFlyingAngles(LivingEntity livingEntity);
}
