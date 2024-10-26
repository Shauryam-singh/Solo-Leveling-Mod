package com.legit.solo_leveling.gui;

import com.legit.solo_leveling.SoloLevelingMod;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SoloLevelingMod.MOD_ID, value = Dist.CLIENT)
public class LevelUpGuiScreen extends Screen {

    private final Player player; // Store the player reference
    private Button closeButton; // Button to close the GUI

    // Constructor
    public LevelUpGuiScreen(Player player) {
        super(new TextComponent("! Alert"));
        this.player = player;
    }

    @Override
    protected void init() {
        // Initialize buttons here
        closeButton = addRenderableWidget(new Button(this.width / 2 - 50, this.height / 2 + 30, 100, 20,
                new TextComponent("Close"), button -> {
            // Action on button click
            Minecraft.getInstance().setScreen(null); // Close the GUI
        }));
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        // Render background
        drawCustomBackground(poseStack);
        drawTitle(poseStack); // Draw title
        drawLevelUpMessage(poseStack); // Draw level up message

        super.render(poseStack, mouseX, mouseY, partialTicks); // Call to super to render other elements
    }

    private void drawCustomBackground(PoseStack poseStack) {
        // Fill the background with a darker semi-transparent color (ARGB format)
        fill(poseStack, 0, 0, this.width, this.height, 0xAA000000); // Dark semi-transparent background
    }

    private void drawTitle(PoseStack poseStack) {
        Minecraft minecraft = Minecraft.getInstance();
        String titleText = "! Alert";
        int titleColor = 0xFFFF0000; // Red title color

        // Draw the title at the top center
        int titleWidth = minecraft.font.width(titleText);
        minecraft.font.draw(poseStack, new TextComponent(titleText),
                (this.width - titleWidth) / 2, 20, titleColor);
    }

    private void drawLevelUpMessage(PoseStack poseStack) {
        Minecraft minecraft = Minecraft.getInstance();
        String messageText = "You leveled up!";
        int messageColor = 0xFFFFFFFF; // White message color

        // Draw the message at the center
        int messageWidth = minecraft.font.width(messageText);
        minecraft.font.draw(poseStack, new TextComponent(messageText),
                (this.width - messageWidth) / 2, this.height / 2 - 10, messageColor);
    }
}
