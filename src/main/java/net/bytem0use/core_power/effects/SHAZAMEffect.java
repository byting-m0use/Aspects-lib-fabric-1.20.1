package net.bytem0use.core_power.effects;

import net.bytem0use.common.api.abilities.base.PowerAPI;
import net.bytem0use.common.api.type.PowersTag;
import net.bytem0use.core_power.particle.ModParticles;
import net.bytem0use.mixin.PlayerAbilitiesAccessor;
import net.minecraft.client.world.ClientWorld;
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

    public float flightSpeed = 4f;

    public SHAZAMEffect(StatusEffectCategory category, int color, PowersTag pTag) {
        super(category, color, pTag);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if(entity instanceof PlayerEntity player) {
            if(entity instanceof ServerPlayerEntity serverPlayer){
            ServerWorld world = serverPlayer.getServerWorld();

            //PlayerAbilities playerAbilities = player.getAbilities();

            Random random = world.getRandom();

            if (!entity.getWorld().isClient && player.hasStatusEffect(this)) {

                player.getAbilities().allowFlying = true;

                ((PlayerAbilitiesAccessor) player.getAbilities()).core_power$getFlySpeed();

                ((PlayerAbilitiesAccessor) player.getAbilities()).core_power$setFlySpeed(0.15f);

                int i = MathHelper.clamp(0, 0, 64);

                double f2 = Math.cos(player.getBodyYaw() * ((float) Math.PI / 180F)) * (0.1F + 0.21F * (float) i);
                double f3 = Math.sin(player.getBodyYaw() * ((float) Math.PI / 180F)) * (0.1F + 0.21F * (float) i);
                double f4 = Math.cos(player.getBodyYaw() * ((float) Math.PI / 180F)) * (0.4F + 0.21F * (float) i);
                double f5 = Math.sin(player.getBodyYaw() * ((float) Math.PI / 180F)) * (0.4F + 0.21F * (float) i);
                float f6 = (0.3F * 0.45F) * ((float) i * 0.2F + 1F);
                float f7 = (0.3F * 0.45F) * ((float) i * 0.2F + 6F);

                double f8 = Math.sin(player.getBodyYaw() * ((float) Math.PI / 180F)) * (.3F + 0.21F * (float) i);
                double f10 = Math.cos(player.getBodyYaw() * ((float) Math.PI / 180F)) * (0.3F + 0.21F * (float) i);

                //For head based particles
                double f11 = Math.cos(player.getHeadYaw() * ((float) Math.PI / 180F)) * (0.3F + 0.21F * (float) i);
                double f12 = Math.sin(player.getHeadYaw() * ((float) Math.PI / 180F)) * (.3F + 0.21F * (float) i);
                float f9 = (0.5F * 0.50F) * ((float) i * .9F + 7F);

                //spawns particles on players' right arm shoulder
                world.spawnParticles(ParticleTypes.CLOUD, player.getX() + f2, player.getY() + (double) f6, player.getZ() + f3, 1, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D, 0.0D);

                //spawns particles on players' right leg foot
                world.spawnParticles(ParticleTypes.CLOUD, player.getX() - f2, player.getY() + (double) f6, player.getZ() - f3, 1, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D, 0.0D);

                //spawns particles on players' right arm shoulder
                world.spawnParticles(ParticleTypes.CLOUD, player.getX() + f4, player.getY() + (double) f7, player.getZ() + f5, 1, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D, 0.0D);

                //spawns particles on players' left leg foot
                world.spawnParticles(ParticleTypes.CLOUD, player.getX() - f4, player.getY() + (double) f7, player.getZ() - f5, 1, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D, 0.0D);

                //Good for spawning particles surrounding the area of the player
                //world.spawnParticles(ParticleTypes.END_ROD, player.getX() - f11, player.getY() + (double) f9, player.getZ() - f8, 1, player.getHeadYaw() * 0.05D, player.getHeadYaw(), player.getHeadYaw() * 0.05D, 0.0D);

                world.spawnParticles(ParticleTypes.FLAME, player.getX()+ f11 + player.getHeadYaw(), player.getY() + f12 + player.getHeadYaw(), player.getZ() + f9 + player.getHeadYaw(), 1, random.nextGaussian() * 0.05D, -0.25, random.nextGaussian() * 0.05D, 0.0D);
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
