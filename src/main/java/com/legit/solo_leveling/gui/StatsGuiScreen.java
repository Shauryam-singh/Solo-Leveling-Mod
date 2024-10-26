package com.legit.solo_leveling.gui;

import com.legit.solo_leveling.SoloLevelingMod;
import com.legit.solo_leveling.capability.PlayerLevelCapability;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SoloLevelingMod.MOD_ID, value = Dist.CLIENT)
public class StatsGuiScreen extends Screen {

    private final Player player;

    // Define the width and height of the GUI
    private static final int WIDTH = 300; // Increased width for more stats
    private static final int HEIGHT = 150; // Increased height for more stats

    // Constructor
    public StatsGuiScreen(Player player) {
        super(new TextComponent("Player Stats"));
        this.player = player;
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        // Render background first to avoid overlay issues
        drawCustomBackground(poseStack);
        drawTitle(poseStack); // Draw title
        drawStats(poseStack); // Draw stats

        super.render(poseStack, mouseX, mouseY, partialTicks); // Call to super to render other elements
    }

    @Override
    public boolean isPauseScreen() {
        return false; // Make sure the game doesn't pause when the GUI is open
    }

    // Method to draw custom background
    private void drawCustomBackground(PoseStack poseStack) {
        // Fill the background with a lighter semi-transparent blue color (ARGB format)
        fill(poseStack, (this.width - WIDTH) / 2, (this.height - HEIGHT) / 2,
                (this.width + WIDTH) / 2, (this.height + HEIGHT) / 2, 0xAA0000FF); // Lighter semi-transparent blue
    }

    // Method to draw the title
    private void drawTitle(PoseStack poseStack) {
        Minecraft minecraft = Minecraft.getInstance();
        String titleText = "Status";
        int textColor = 0xFFFFFFFF; // White text

        // Draw the title at the top center
        int titleWidth = minecraft.font.width(titleText);
        minecraft.font.draw(poseStack, new TextComponent(titleText),
                (this.width - titleWidth) / 2, (this.height - HEIGHT) / 2 + 10, textColor);
    }

    // Method to draw the stats
    private void drawStats(PoseStack poseStack) {
        Minecraft minecraft = Minecraft.getInstance();

        // Get player level and XP from the capability
        player.getCapability(PlayerLevelCapability.INSTANCE).ifPresent(cap -> {
            // Retrieve player stats
            String playerName = player.getName().getString();
            String job = "Warrior"; // Replace with actual job
            String title = "Champion"; // Replace with actual title
            int hp = 100; // Replace with actual HP
            int level = cap.getLevel();
            int currentXp = cap.getXp();
            int requiredXp = cap.getRequiredXp();
            int fatigue = 0; // Replace with actual fatigue
            int manaPoints = 100; // Replace with actual MP

            // Customize text appearance
            int textColor = 0xFFFFFFFF; // White text

            // Draw left side stats
            minecraft.font.draw(poseStack, new TextComponent("Name: " + playerName),
                    (this.width - WIDTH) / 2 + 10, (this.height - HEIGHT) / 2 + 35, textColor);
            minecraft.font.draw(poseStack, new TextComponent("Job: " + job),
                    (this.width - WIDTH) / 2 + 10, (this.height - HEIGHT) / 2 + 50, textColor);
            minecraft.font.draw(poseStack, new TextComponent("Title: " + title),
                    (this.width - WIDTH) / 2 + 10, (this.height - HEIGHT) / 2 + 65, textColor);
            minecraft.font.draw(poseStack, new TextComponent("HP: " + hp),
                    (this.width - WIDTH) / 2 + 10, (this.height - HEIGHT) / 2 + 80, textColor);

            // Draw right side stats
            minecraft.font.draw(poseStack, new TextComponent("Level: " + level),
                    (this.width + WIDTH) / 2 - 100, (this.height - HEIGHT) / 2 + 35, textColor);
            minecraft.font.draw(poseStack, new TextComponent("XP: " + currentXp + "/" + requiredXp),
                    (this.width + WIDTH) / 2 - 100, (this.height - HEIGHT) / 2 + 50, textColor);
            minecraft.font.draw(poseStack, new TextComponent("Fatigue: " + fatigue),
                    (this.width + WIDTH) / 2 - 100, (this.height - HEIGHT) / 2 + 65, textColor);
            minecraft.font.draw(poseStack, new TextComponent("Mana Points: " + manaPoints),
                    (this.width + WIDTH) / 2 - 100, (this.height - HEIGHT) / 2 + 80, textColor);
        });
    }

    // Method to update the GUI every tick
    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Minecraft minecraft = Minecraft.getInstance();
            if (minecraft.screen instanceof StatsGuiScreen gui) {
                PoseStack poseStack = new PoseStack(); // Create a new PoseStack
                gui.render(poseStack, 0, 0, 0); // Force render to update GUI
            }
        }
    }
}
