package net.bytem0use.common.attributes;

import net.bytem0use.CorePower;
import net.bytem0use.common.api.type.AbilityType;
import net.bytem0use.common.api.type.PowersTag;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class CorePowerAttributes extends EntityAttribute {

    protected CorePowerAttributes(String translationKey, double fallback, PowersTag pCategory, AbilityType pType) {
        super(translationKey, fallback);
    }

    public static void registerAttribute(String name, CorePowerAttributes attribute) {
        Registry.register(Registries.ATTRIBUTE, Identifier.of("origins", name), attribute);
    }

    public static void registerAttributes() {
        CorePower.LOGGER.info("Registering Attributes for " + CorePower.MOD_ID);
    }
}
