package net.bytem0use.aspects.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class ShazamParticle extends SpriteBillboardParticle {

    public ShazamParticle(ClientWorld clientWorld, double d, double e, double f, double g, double h, double i, SpriteProvider provider) {
        super(clientWorld, d, e, f, g, h, i);

        this.velocityMultiplier = 0.9f;

        this.maxAge = 50;
        this.setSpriteForAge(provider);

        this.red = 1f;
        this.green = 1f;
        this.blue = 1f;
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_LIT;
    }

    @Override
    public void move(double dx, double dy, double dz) {
        this.setBoundingBox(this.getBoundingBox().offset(dx, dy, dz));
        this.repositionFromBoundingBox();
    }

    @Environment(EnvType.CLIENT)
    public record Factory(SpriteProvider spriteProvider) implements ParticleFactory<DefaultParticleType> {

        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            return new ShazamParticle(clientWorld, d, e, f, g, h, i, this.spriteProvider);
        }
    }
}
