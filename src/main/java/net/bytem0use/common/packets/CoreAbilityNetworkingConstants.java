package net.bytem0use.common.packets;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class CoreAbilityNetworkingConstants {
    public static final Identifier ABILITY_KEYBIND_PACKET_ID = Identifier.of("core_power", "ability_keybinding");

    public static void send(ClientPlayerEntity player, Identifier channelName, PacketByteBuf buf) {
    }


}
