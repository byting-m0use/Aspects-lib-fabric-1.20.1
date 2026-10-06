package net.bytem0use.client.ability;

import net.bytem0use.common.packets.FlightAccelerateC2SPacket;
import net.bytem0use.common.packets.FlightToggleC2SPacket;
import net.bytem0use.common.utils.FlyingState;
import net.bytem0use.common.utils.PlayerFlightInterface;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;

@Environment(EnvType.CLIENT)
public class FlightHandler {
    private static boolean wasJumpPressed = false;
    private static long lastJumpTime = 0L;
    private static boolean wasAccelerating = false;
    private static float lastForward = 0.0F;
    private static float lastSideways = 0.0F;
    private static boolean wasSneakPressed = false;

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register((ClientTickEvents.EndTick)(client) -> {
            if (client.player != null) {
                PlayerFlightInterface omniPlayer = (PlayerFlightInterface) client.player;
                omniPlayer.clientIsLocalPlayer(true);
                boolean isJumpPressed = client.options.jumpKey.isPressed();
                if (isJumpPressed && !wasJumpPressed) {
                    long now = System.currentTimeMillis();
                    if (now - lastJumpTime < 300L) {
                        //if (client.player.isSneaking() && omniPlayer.getFlightState() == FlyingState.GROUND && client.player.getAbilities().allowFlying) {
                            //omniPlayer.setTakeoffTicks(5);
                        //}

                        if (!client.player.isSneaking() && omniPlayer.getFlightState() == FlyingState.GROUND && client.player.getAbilities().allowFlying) {
                            omniPlayer.setTakeoffTicks(5);
                        }

                        ClientPlayNetworking.send(FlightToggleC2SPacket.ID, PacketByteBufs.empty());
                        lastJumpTime = 0L;
                    } else {
                        lastJumpTime = now;
                    }
                }

                wasJumpPressed = isJumpPressed;

                //An if statement that is what makes the player fly in the direction of the mouse when ctrl is held
                if (omniPlayer.getFlightState() != FlyingState.GROUND) {
                    boolean isAccelerating = client.options.sprintKey.isPressed() && !client.player.horizontalCollision;
                    omniPlayer.setFlightAccelerating(isAccelerating);
                    if (isAccelerating != wasAccelerating) {
                        PacketByteBuf buf = PacketByteBufs.create();
                        buf.writeBoolean(isAccelerating);
                        ClientPlayNetworking.send(FlightAccelerateC2SPacket.ID, buf);
                        wasAccelerating = isAccelerating;
                    }
                } else {
                    wasAccelerating = false;
                }

                float forward = client.player.input.movementForward;
                float sideways = client.player.input.movementSideways;
                //if (forward != lastForward || sideways != lastSideways) {
                    //lastForward = forward;
                    //lastSideways = sideways;
                    //omniPlayer.setHoverForward(forward);
                    //omniPlayer.setHoverSideways(sideways);
                    //PacketByteBuf buf = PacketByteBufs.create();
                    //buf.writeFloat(forward);
                    //buf.writeFloat(sideways);
                    //ClientPlayNetworking.send(HoverInputC2SPacket.ID, buf);
                //}

                //wasSneakPressed = isSneakPressed;
            }
        });
    }
}
