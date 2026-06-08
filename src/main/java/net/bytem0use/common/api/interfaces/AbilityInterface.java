package net.bytem0use.common.api.interfaces;

import net.bytem0use.common.api.abilities.base.PowerAPI;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;

public interface AbilityInterface {

    boolean hasPower(PowerAPI power, PlayerEntity playerEntity, PlayerAbilities abilities);
}
