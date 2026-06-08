package net.bytem0use.core_power.keybind;

import net.bytem0use.core_power.CorePowerModEffects;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class CorePowerKeybindings {
    public static final String KEY_CATEGORY_ORIGINS = "key.category.origins.origins_mod";
    public static final String CLIMB = "key.origins.climb";
    public static final String WEB_ATTACH = "key.origins.web_attach";

    public static KeyBinding climb;
    public static KeyBinding web_attach;

    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if( climb.wasPressed() && client.player.hasStatusEffect(CorePowerModEffects.SPEEDSTER)) {

                assert client.player != null;
                client.player.sendMessage(Text.literal("It was pressed. Woo"));
            }
        });
    }

    public static void register() {
        climb = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                CLIMB,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_G,
                KEY_CATEGORY_ORIGINS
        ));

        web_attach = KeyBindingHelper.registerKeyBinding(new KeyBinding
                (WEB_ATTACH, InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_O,
                KEY_CATEGORY_ORIGINS));

        registerKeyInputs();
    }

}
