package net.bytem0use.core_power;

import net.bytem0use.Aspects;
import net.bytem0use.common.api.type.PassiveList;
import net.bytem0use.common.api.type.PowersTag;
import net.bytem0use.core_power.effects.SHAZAMEffect;
import net.bytem0use.core_power.effects.SpeedsterEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class CorePowerModEffects {

    private static StatusEffect registerPower(String name, StatusEffect power) {
        return Registry.register(Registries.STATUS_EFFECT, new Identifier(Aspects.MOD_ID, name), power);
    }

    public static final StatusEffect SPEEDSTER = registerPower("speedster",
            new SpeedsterEffect(StatusEffectCategory.BENEFICIAL, 55550, PowersTag.ENHANCER, PassiveList.IMPACT_DAMAGE));

    public static final StatusEffect SHAZAM = registerPower("shazam",
            new SHAZAMEffect(StatusEffectCategory.BENEFICIAL, 55550, PowersTag.ENHANCER));

    public static void registerEffects() {
        Aspects.LOGGER.info("Registering Mod Effects for " + Aspects.MOD_ID);
    }

}
