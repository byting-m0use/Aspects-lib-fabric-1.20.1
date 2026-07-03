package net.bytem0use.common.api.abilities.passive;

import net.bytem0use.Aspects;
import net.bytem0use.common.api.abilities.base.CorePassive;
import net.bytem0use.core_power.ability.ImpactDamagePassive;
import net.bytem0use.common.api.type.PassiveTags;
import net.bytem0use.common.api.type.PowersTag;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class PassiveRegistry {

    public static final CorePassive IMPACT_DAMAGE = registerPassive(
            "impact_damage", new ImpactDamagePassive(
                    StatusEffectCategory.HARMFUL, 5555, PassiveTags.IMPACT_DAMAGE, PowersTag.DESTRUCTIVE));

    public static CorePassive registerPassive(String id, CorePassive passive) {
        return Registry.register(Registries.STATUS_EFFECT, id, passive);
    }

    public static void register() {
        Aspects.LOGGER.info("Registering Passives for " + Aspects.MOD_ID);
    }

}
