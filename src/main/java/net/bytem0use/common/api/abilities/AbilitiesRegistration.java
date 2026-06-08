package net.bytem0use.common.api.abilities;

import net.minecraft.nbt.NbtCompound;

public abstract class AbilitiesRegistration {

    public abstract void writeNBT(NbtCompound nbt);

    public static class Builder {

    }
}
