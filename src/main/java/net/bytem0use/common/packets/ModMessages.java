package net.bytem0use.common.packets;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class ModMessages {
    public static void registerPackets() {
        ServerPlayNetworking.registerGlobalReceiver(FlightToggleC2SPacket.ID, FlightToggleC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(FlightAccelerateC2SPacket.ID, FlightAccelerateC2SPacket::receive);
    }
}
