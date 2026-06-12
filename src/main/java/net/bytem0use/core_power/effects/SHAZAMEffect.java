package net.bytem0use.core_power.effects;

import net.bytem0use.common.api.abilities.base.PowerAPI;
import net.bytem0use.common.api.type.PowersTag;
import net.bytem0use.core_power.particle.ModParticles;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import java.util.Objects;

public class SHAZAMEffect extends PowerAPI {

    public SHAZAMEffect(StatusEffectCategory category, int color, PowersTag pTag) {
        super(category, color, pTag);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if(entity instanceof PlayerEntity player) {
            if(entity instanceof ServerPlayerEntity serverPlayer){
            ServerWorld world = serverPlayer.getServerWorld();

            Random random = world.getRandom();

            if (!entity.getWorld().isClient && player.hasStatusEffect(this)) {

                player.getAbilities().allowFlying = true;

                int i = MathHelper.clamp(0, 0, 64);
                double f2 = Math.cos(player.getBodyYaw() * ((float) Math.PI / 180F)) * (0.1F + 0.21F * (float) i);
                double f3 = Math.sin(player.getBodyYaw() * ((float) Math.PI / 180F)) * (0.1F + 0.21F * (float) i);
                double f4 = Math.cos(player.getBodyYaw() * ((float) Math.PI / 180F)) * (0.4F + 0.21F * (float) i);
                double f5 = Math.sin(player.getBodyYaw() * ((float) Math.PI / 180F)) * (0.4F + 0.21F * (float) i);
                float f6 = (0.3F * 0.45F) * ((float) i * 0.2F + 1F);
                float f7 = (0.3F * 0.45F) * ((float) i * 0.2F + 6F);

                world.spawnParticles(ParticleTypes.SMOKE, player.getX() + f2, player.getY() + (double) f6, player.getZ() + f3, 1, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D, 0.0D);
                world.spawnParticles(ParticleTypes.SMOKE, player.getX() - f2, player.getY() + (double) f6, player.getZ() - f3, 1, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D, 0.0D);
                world.spawnParticles(ParticleTypes.SMOKE, player.getX() + f4, player.getY() + (double) f7, player.getZ() + f5, 1, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D, 0.0D);
                world.spawnParticles(ParticleTypes.SMOKE, player.getX() - f4, player.getY() + (double) f7, player.getZ() - f5, 1, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D, 0.0D);
                world.spawnParticles(ModParticles.SHAZAM_PARTICLE, player.getX() + f2, player.getY() + (double) f6, player.getZ() + f3, 1, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D, 0.0D);
                world.spawnParticles(ModParticles.SHAZAM_PARTICLE, player.getX() - f2, player.getY() + (double) f6, player.getZ() - f3, 1, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D, 0.0D);
                world.spawnParticles(ModParticles.SHAZAM_PARTICLE, player.getX() + f4, player.getY() + (double) f7, player.getZ() + f5, 1, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D, 0.0D);
                world.spawnParticles(ModParticles.SHAZAM_PARTICLE, player.getX() - f4, player.getY() + (double) f7, player.getZ() - f5, 1, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D, 0.0D);
            }
            }
            player.sendAbilitiesUpdate();
        }
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if(entity instanceof PlayerEntity player) {
            if(entity instanceof ServerPlayerEntity serverPlayer) {
                if (!entity.getWorld().isClient && player.hasStatusEffect(this)) {
                    player.getAbilities().allowFlying = false;
                }
            }
            player.sendAbilitiesUpdate();
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
