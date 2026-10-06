package net.bytem0use.common.packets;

import net.bytem0use.common.config.PlayerFlightConfig;
import net.bytem0use.common.utils.FlyingState;
import net.bytem0use.common.utils.PlayerFlightInterface;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.block.BlockState;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class FlightToggleC2SPacket {
    public static final Identifier ID = new Identifier("aspects", "toggle_flight");

    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        server.execute(() -> {
            PlayerFlightInterface flyingPlayer = (PlayerFlightInterface) player;
            FlyingState currentState = flyingPlayer.getFlightState();
            if (currentState != FlyingState.GROUND || player.getAbilities().allowFlying) {
                FlyingState newState = currentState == FlyingState.GROUND ? FlyingState.HOVERING : FlyingState.GROUND;
                flyingPlayer.setFlightState(newState);
                if (newState == FlyingState.HOVERING && player.isSneaking()) {
                    flyingPlayer.setTakeoffTicks(5);
                    ServerWorld world = (ServerWorld)player.getWorld();
                    world.spawnParticles(ParticleTypes.POOF, player.getX(), player.getY(), player.getZ(), 40, (double)0.5F, 0.2, (double)0.5F, 0.15);
                    world.spawnParticles(ParticleTypes.CLOUD, player.getX(), player.getY(), player.getZ(), 20, 0.4, 0.1, 0.4, 0.1);
                    world.playSound((PlayerEntity) null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_ENDER_DRAGON_FLAP, SoundCategory.PLAYERS, 1.5F, 1.2F);
                    world.playSound((PlayerEntity) null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_DRAGON_FIREBALL_EXPLODE, SoundCategory.PLAYERS, 1.8F, 1.2F);
                    if (PlayerFlightConfig.INSTANCE.breakBlocksOnTakeoff) {
                        int radius = 3;
                        BlockPos playerPos = player.getBlockPos();

                        for(int x = -radius; x <= radius; ++x) {
                            for(int z = -radius; z <= radius; ++z) {
                                if (x * x + z * z <= radius * radius) {
                                    BlockPos targetPos = null;
                                    BlockState groundState = null;

                                    for(int yOffset = 2; yOffset >= -4; --yOffset) {
                                        BlockPos checkPos = playerPos.add(x, yOffset, z);
                                        BlockState state = world.getBlockState(checkPos);
                                        if (!state.isAir() && state.getFluidState().isEmpty()) {
                                            float hardness = state.getHardness(world, checkPos);
                                            if (hardness > 0.0F && hardness < 50.0F) {
                                                targetPos = checkPos;
                                                groundState = state;
                                                break;
                                            }

                                            if (hardness == 0.0F) {
                                                world.removeBlock(checkPos, false);
                                            }
                                        }
                                    }

                                    if (targetPos != null && groundState != null) {
                                        FallingBlockEntity debris = FallingBlockEntity.spawnFromBlock(world, targetPos, groundState);
                                        debris.dropItem = false;
                                        debris.timeFalling = 1;
                                        double dirX = (double)x;
                                        double dirZ = (double)z;
                                        double dist = Math.sqrt(dirX * dirX + dirZ * dirZ);
                                        if (dist > (double)0.0F) {
                                            dirX /= dist;
                                            dirZ /= dist;
                                        }

                                        double upwardSpeed = 0.4 + world.random.nextDouble() * 0.6;
                                        double outwardSpeed = 0.1 + world.random.nextDouble() * 0.15;
                                        debris.setVelocity(dirX * outwardSpeed, upwardSpeed, dirZ * outwardSpeed);
                                        debris.velocityModified = true;
                                        world.removeBlock(targetPos, false);
                                    }
                                }
                            }
                        }
                    }
                }

                player.getAbilities().flying = newState == FlyingState.HOVERING;
                player.sendAbilitiesUpdate();
            }
        });
    }
}
