package net.bytem0use.common.api.abilities.base;

import net.bytem0use.common.api.type.PassiveList;
import net.bytem0use.common.api.type.PowersTag;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public abstract class PowerAPI extends StatusEffect {
    public static PowersTag tag;
    public PassiveList passiveList;

    public PowerAPI(StatusEffectCategory category, int color, PowersTag pTag) {
        super(category, color);
        tag = pTag;
    }

    public PowerAPI(StatusEffectCategory category, int color, PowersTag tag, PassiveList passive) {
        super(category, color);
        PowerAPI.tag = tag;
    }

    public PowerAPI(StatusEffectCategory category, int color, PowersTag tag, PassiveList passive, PassiveList passive2) {
        super(category, color);
        PowerAPI.tag = tag;
    }

    public boolean hasPassiveTag(PassiveList passive) {
        return this.passiveList == passive;
    }
}
