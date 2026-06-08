package net.bytem0use.core_power.effects;

import net.bytem0use.common.api.abilities.base.PowerAPI;
import net.bytem0use.common.api.type.PassiveList;
import net.bytem0use.common.api.type.PowersTag;
import net.minecraft.entity.effect.StatusEffectCategory;

public class SpeedsterEffect extends PowerAPI {
    private int amplifier;
    private int duration;

    public SpeedsterEffect(StatusEffectCategory category, int color, PowersTag tag, PassiveList passive) {
        super(category, color, tag, passive);
    }
}
