package net.bytem0use.common.registry;

import net.bytem0use.CorePower;
import net.bytem0use.common.api.abilities.base.PowerAPI;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.util.Identifier;

public class AbilityPowerRegistry {

    public static final SimpleRegistry<PowerAPI> REGISTRY = FabricRegistryBuilder.createSimple
            (RegistryKey.<PowerAPI>ofRegistry(new Identifier(CorePower.MOD_ID, "core_power"))).buildAndRegister();

    public static <T extends PowerAPI> T register(T entry) {
        return Registry.register(REGISTRY, entry.toString(), entry);
    }


}
