package net.bytem0use.core_power.particle;

import net.bytem0use.CorePower;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModParticles {

    public static final DefaultParticleType SHAZAM_PARTICLE = registerParticle("shazam_particle", FabricParticleTypes.simple());

    private static DefaultParticleType registerParticle(String name, DefaultParticleType particleType) {
        return Registry.register(Registries.PARTICLE_TYPE, Identifier.of(CorePower.MOD_ID, name), particleType);
    }

    public static void registerParticles() {
        CorePower.LOGGER.info("Registering Particles for " + CorePower.MOD_ID);
    }
}
