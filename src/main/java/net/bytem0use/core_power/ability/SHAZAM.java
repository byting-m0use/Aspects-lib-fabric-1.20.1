package net.bytem0use.core_power.ability;

import net.bytem0use.common.api.abilities.base.CoreAbilitiesBase;
import net.bytem0use.common.api.type.AbilityCategory;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.lwjgl.glfw.GLFW;

public class SHAZAM extends CoreAbilitiesBase {
    public static final String KEY_CATEGORY_SHAZAM = "key.category.origins.shazam";
    private static final String SHAZAM = "key.origins.shazam";

    public static KeyBinding shazam;

    private static World world;
    private static LightningEntity lightningEntity;
    private static PlayerEntity player;

    public SHAZAM(String translationKey, int code, String category, AbilityCategory pCategory) {
        super(translationKey, code, category, pCategory);
    }


    public static void registerAbilityKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            //assert CoreAbilityNetworkingConstants.ABILITY_KEYBIND_PACKET_ID != null;
            //ClientPlayNetworking.send(CoreAbilityNetworkingConstants.ABILITY_KEYBIND_PACKET_ID, PacketByteBufs.empty());
        });
    }

    public static void registerAbility() {
        shazam = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                SHAZAM,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_H,
                KEY_CATEGORY_SHAZAM
        ));

        registerAbilityKeyInputs();
    }
}
