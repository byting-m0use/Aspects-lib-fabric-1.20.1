package net.bytem0use.common.api.abilities.base;

import net.bytem0use.common.api.type.PassiveTags;
import net.bytem0use.common.api.type.PowersTag;
import net.minecraft.entity.effect.StatusEffectCategory;

public class CorePassive extends PowerAPI{


    public CorePassive(StatusEffectCategory category, int color, PassiveTags tagP, PowersTag tag) {
        super(category, color, tag);
    }

    public void getPassive(CorePassive passive) {
    }

    public void applyPassive() {
    }
}
