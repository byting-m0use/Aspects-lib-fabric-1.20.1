package net.bytem0use.common.api.abilities.base;

import net.bytem0use.common.api.type.AbilityCategory;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public abstract class CoreAbilitiesBase {
    public String id;

    public abstract void tick(ServerPlayerEntity player);

    public boolean isActivatable(CoreAbilitiesBase ability) {
        return false;
    }



    public void setId(String id) {
        this.id = id;
    }
}
