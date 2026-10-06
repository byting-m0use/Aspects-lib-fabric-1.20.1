package net.bytem0use.common.packets;

import net.bytem0use.common.utils.PlayerFlightInterface;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class FlightAccelerateC2SPacket {
    public static final Identifier ID = new Identifier("tf", "flight_accelerate");

    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        boolean isAccelerating = buf.readBoolean();
        server.execute(() -> ((PlayerFlightInterface)player).setFlightAccelerating(isAccelerating));
    }
}
