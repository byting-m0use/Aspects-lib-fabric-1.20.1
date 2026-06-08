package net.bytem0use.common.api.abilities.base;

import net.bytem0use.common.api.type.AbilityCategory;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.server.network.ServerPlayerEntity;

@Environment(EnvType.CLIENT)
public abstract class CoreAbilitiesBase extends KeyBinding {

    public CoreAbilitiesBase(String translationKey, int code, String category) {
        super(translationKey, code, category);
    }

    public CoreAbilitiesBase(String translationKey, InputUtil.Type type, int code, String category) {
        super(translationKey, type, code, category);
    }

    public CoreAbilitiesBase(String translationKey, int code, String category, AbilityCategory pCategory) {
        super(translationKey, code, category);
    }

    public void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
        });
    }

    public boolean hasPowerEffect(ServerPlayerEntity player, PowerAPI power) {
        if(player.hasStatusEffect(power) );
        return true;
    }

    public void register() {
        registerKeyInputs();
    }
}
