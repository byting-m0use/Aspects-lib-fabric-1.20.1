package net.bytem0use.core_power.effects;

import net.bytem0use.common.api.abilities.base.PowerAPI;
import net.bytem0use.common.api.type.PowersTag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;

public class SHAZAMEffect extends PowerAPI {
    private final PlayerAbilities playerAbilities;

    public SHAZAMEffect(StatusEffectCategory category, int color, PowersTag pTag) {
        super(category, color, pTag);
        playerAbilities = new PlayerAbilities();
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if(entity instanceof PlayerEntity player) {
            if (!entity.getWorld().isClient && player.hasStatusEffect(this)) {
                playerAbilities.allowFlying = true;
                player.sendAbilitiesUpdate();
            }
        }
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if(entity instanceof PlayerEntity player) {
            if (!entity.getWorld().isClient && player.hasStatusEffect(this)) {
                playerAbilities.allowFlying = false;
                player.sendAbilitiesUpdate();
            }
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
