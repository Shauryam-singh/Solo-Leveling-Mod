package com.legit.solo_leveling.gui;

import com.legit.solo_leveling.SoloLevelingMod;
import com.legit.solo_leveling.capability.PlayerLevelCapability;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SoloLevelingMod.MOD_ID, value = Dist.CLIENT)
public class StatsGuiScreen extends Screen {

    private final Player player;
    private static final int WIDTH = 300; // Width of the GUI
    private static final int HEIGHT = 180; // Increased height for buttons
    private Button closeButton; // Button to close the GUI
    private Button skillsButton; // Button to open skills menu
    private Button levelUpButton; // Button to level up the player

    // Constructor
    public StatsGuiScreen(Player player) {
        super(new TextComponent("Player Stats"));
        this.player = player;
    }

    @Override
    protected void init() {
        // Initialize buttons here
        closeButton = addRenderableWidget(new Button(this.width / 2 - 50, this.height / 2 + 80, 100, 20,
                new TextComponent("Close"), button -> {
            // Action on button click
            Minecraft.getInstance().setScreen(null); // Close the GUI
        }));

        skillsButton = addRenderableWidget(new Button(this.width / 2 - 50, this.height / 2 + 50, 100, 20,
                new TextComponent("Skills"), button -> {
            // Open the skills menu
            Minecraft.getInstance().setScreen(new SkillsGuiScreen(player));
        }));
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        // Render background
        drawCustomBackground(poseStack);
        drawTitle(poseStack); // Draw title
        drawStats(poseStack); // Draw stats

        super.render(poseStack, mouseX, mouseY, partialTicks); // Call to super to render other elements
    }

    @Override
    public boolean isPauseScreen() {
        return false; // Make sure the game doesn't pause when the GUI is open
    }

    private void drawCustomBackground(PoseStack poseStack) {
        // Fill the background with a lighter semi-transparent blue color (ARGB format)
        fill(poseStack, (this.width - WIDTH) / 2, (this.height - HEIGHT) / 2,
                (this.width + WIDTH) / 2, (this.height + HEIGHT) / 2, 0xAA0000FF); // Lighter semi-transparent blue
    }

    private void drawTitle(PoseStack poseStack) {
        Minecraft minecraft = Minecraft.getInstance();
        String titleText = "Status";
        int textColor = 0xFFFFFFFF; // White text

        // Draw the title at the top center
        int titleWidth = minecraft.font.width(titleText);
        minecraft.font.draw(poseStack, new TextComponent(titleText),
                (this.width - titleWidth) / 2, (this.height - HEIGHT) / 2 + 10, textColor);
    }

    private void drawStats(PoseStack poseStack) {
        Minecraft minecraft = Minecraft.getInstance();

        player.getCapability(PlayerLevelCapability.INSTANCE).ifPresent(cap -> {
            String playerName = player.getName().getString();
            String job = "Warrior"; // Replace with actual job
            String title = "Champion"; // Replace with actual title
            int hp = 100; // Replace with actual HP
            int level = cap.getLevel();
            int currentXp = cap.getXp();
            int requiredXp = cap.getRequiredXp();
            int fatigue = 0; // Replace with actual fatigue
            int manaPoints = 100; // Replace with actual MP

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
