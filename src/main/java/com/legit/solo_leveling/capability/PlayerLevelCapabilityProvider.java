package com.legit.solo_leveling.capability;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

public class PlayerLevelCapabilityProvider implements ICapabilityProvider {
    private final PlayerLevelCapability instance = new PlayerLevelCapability();
    private final LazyOptional<PlayerLevelCapability> optional = LazyOptional.of(() -> instance);

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, net.minecraft.core.Direction side) {
        return cap == PlayerLevelCapability.INSTANCE ? optional.cast() : LazyOptional.empty();
    }
}
