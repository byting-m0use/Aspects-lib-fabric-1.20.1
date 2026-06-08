package net.bytem0use.core_power.ability;

import net.bytem0use.common.api.abilities.base.CoreAbilitiesBase;
import net.bytem0use.common.api.type.AbilityCategory;

public class FireballAbility1 extends CoreAbilitiesBase {

    public FireballAbility1(String translationKey, int code, String category, AbilityCategory pCategory) {
        super(translationKey, code, category, pCategory);
    }

    @Override
    public void registerKeyInputs() {
        super.registerKeyInputs();
    }

    @Override
    public void register() {
        registerKeyInputs();
    }
}
