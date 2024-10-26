package com.legit.solo_leveling.capability;

import com.legit.solo_leveling.gui.LevelUpGuiScreen;
import com.legit.solo_leveling.network.LevelUpPacket;
import com.legit.solo_leveling.network.NetworkHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.network.PacketDistributor;

public class PlayerLevelCapability implements INBTSerializable<CompoundTag> {
    public static final Capability<PlayerLevelCapability> INSTANCE = CapabilityManager.get(new CapabilityToken<>() {});

    private int xp = 0;
    private int level = 1;
    private int requiredXp = 100; // Default initial required XP

    public int getXp() { return xp; }
    public int getLevel() { return level; }

    // Calculate required XP based on the current level
    public int getRequiredXp() {
        return 100 * level; // Adjust this formula if needed
    }

    // Method to add XP and handle leveling up
    public void addXp(int amount, Player player) {
        this.xp += amount;
        if (this.xp >= getRequiredXp()) {
            levelUp(player); // Pass the player reference
        }
        // Save or sync capability changes here if necessary
    }

    // Level up method
    public void levelUp(Player player) {
        this.level++; // Increment the player's level
        this.xp -= getRequiredXp(); // Subtract the required XP for the current level

        // Send the LevelUpPacket to the client to open the GUI
        NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), new LevelUpPacket());

        // Optionally increase the required XP for the next level
        applyLevelUpBenefits(player); // Pass the player reference
    }

    // Example method to apply benefits
    private void applyLevelUpBenefits(Player player) {
        // Implement benefits for leveling up, e.g., increasing health or unlocking abilities
        // Example: player.setHealth(player.getHealth() + 2.0F);
    }

    // Setters for xp, level, and required XP
    public void setXp(int xp) {
        this.xp = xp;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    // Optional method to set required XP directly
    public void setRequiredXp(int requiredXp) {
        this.requiredXp = requiredXp; // Directly set the required XP
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("xp", xp);
        tag.putInt("level", level);
        tag.putInt("requiredXp", getRequiredXp()); // Store calculated required XP
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        this.xp = tag.getInt("xp");
        this.level = tag.getInt("level");
        this.requiredXp = tag.getInt("requiredXp"); // Read required XP from NBT
    }
}
