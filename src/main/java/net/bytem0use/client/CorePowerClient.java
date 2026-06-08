package net.bytem0use.client;

import net.bytem0use.common.api.abilities.base.PowerAPI;
import net.bytem0use.core_power.keybind.CorePowerKeybindings;
import net.bytem0use.core_power.ability.SHAZAM;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.entity.effect.StatusEffectInstance;

public class CorePowerClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        CorePowerKeybindings.register();

        SHAZAM.registerAbility();

        ServerPlayerEvents.COPY_FROM.register((oldPlayer, newPlayer, alive) -> {
            if (!alive) {
                for (StatusEffectInstance effect : oldPlayer.getStatusEffects()) {
                    if (effect.getEffectType() instanceof PowerAPI) {
                        newPlayer.addStatusEffect(new StatusEffectInstance(effect));
                    }
                }
            }
        });
    }
}
