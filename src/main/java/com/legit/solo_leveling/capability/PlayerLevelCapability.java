package com.legit.solo_leveling.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.INBTSerializable;

public class PlayerLevelCapability implements INBTSerializable<CompoundTag> {
    public static final Capability<PlayerLevelCapability> INSTANCE = CapabilityManager.get(new CapabilityToken<>() {});

    private int xp = 0;
    private int level = 1;

    // Remove requiredXp as a field; use getRequiredXp() method instead
    public int getXp() { return xp; }
    public int getLevel() { return level; }

    // Calculate required XP based on the current level
    public int getRequiredXp() {
        return 100 * level; // Adjust this formula if needed
    }

    public void addXp(int amount, Player player) {
        this.xp += amount;
        if (this.xp >= getRequiredXp()) {
            levelUp(player); // Pass the player reference
        }
        // Save or sync capability changes here if necessary
    }

    public void levelUp(Player player) { // Accept Player parameter
        this.level++; // Increment the player's level
        this.xp -= getRequiredXp(); // Subtract the required XP for the current level

        // Optionally increase the required XP for the next level
        // No need for a separate field, just call the method
        // Notify the player of their level up
        player.sendMessage(new TextComponent("You've leveled up to level " + level + "!"), player.getUUID());

        // Apply benefits
        applyLevelUpBenefits(player); // Pass the player reference
    }

    // Example method to apply benefits
    private void applyLevelUpBenefits(Player player) {
        // Implement benefits for leveling up, e.g., increasing health or unlocking abilities
        // Example: player.setHealth(player.getHealth() + 2.0F);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("xp", xp);
        tag.putInt("level", level);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        this.xp = tag.getInt("xp");
        this.level = tag.getInt("level");
    }
}
