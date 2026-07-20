package net.bytem0use.client;

import net.bytem0use.common.api.abilities.base.PowerAPI;
import net.bytem0use.aspects.particle.ModParticles;
import net.bytem0use.aspects.particle.ShazamParticle;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.entity.effect.StatusEffectInstance;

public class AspectsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        ParticleFactoryRegistry.getInstance().register(ModParticles.SHAZAM_PARTICLE, ShazamParticle.Factory::new);

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
