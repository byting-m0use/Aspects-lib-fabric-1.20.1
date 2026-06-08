package net.bytem0use.core_power.ability;

import net.bytem0use.common.api.abilities.base.CorePassive;
import net.bytem0use.common.api.abilities.base.PowerAPI;
import net.bytem0use.common.api.type.PassiveList;
import net.bytem0use.common.api.type.PassiveTags;
import net.bytem0use.common.api.type.PowersTag;
import net.bytem0use.common.core.CorePowerDamageSources;
import net.bytem0use.core_power.CorePowerModEffects;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class ImpactDamagePassive extends CorePassive {

    public ImpactDamagePassive(StatusEffectCategory category, int color, PassiveTags tagP, PowersTag tag) {
        super(category, color, tagP, tag);
    }

    public static boolean shouldDamageCollidedEntity(int level, Random random) {
        return level > 0 && random.nextFloat() < 0.15F * level;
    }

    public static int getDamageAmount(int level, Random random) {
        return level > 10 ? level - 10 : 1 + random.nextInt(4);
    }

    @Override
    public boolean hasPassiveTag(PassiveList passive) {
        return super.hasPassiveTag(passive);
    }

    public static void sprintingCollisionWithSpeedster(LivingEntity entity, ClientPlayerEntity client, PowerAPI power, World world) {
        if(client.hasStatusEffect(CorePowerModEffects.SPEEDSTER) && client.isSprinting() && client.collidesWith(entity) && power.hasPassiveTag(PassiveList.IMPACT_DAMAGE)){
            DamageSource damageSource = new DamageSource(
                    world.getRegistryManager()
                            .get(RegistryKeys.DAMAGE_TYPE)
                            .entryOf(CorePowerDamageSources.HIGH_SPEED_IMPACT_DAMAGE));
            entity.damage(damageSource, 5.0f);
        }
    }

}
