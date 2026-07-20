package net.bytem0use.mixin.player;

import net.minecraft.entity.player.PlayerAbilities;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PlayerAbilities.class)
public interface PlayerAbilitiesAccessor {

    @Accessor("flySpeed")
    float core_power$getFlySpeed();

    @Accessor("flySpeed")
    void core_power$setFlySpeed(float speed);
}
