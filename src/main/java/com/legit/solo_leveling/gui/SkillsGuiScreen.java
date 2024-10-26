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

import java.util.List;

@Mod.EventBusSubscriber(modid = SoloLevelingMod.MOD_ID, value = Dist.CLIENT)
public class SkillsGuiScreen extends Screen {

    private final Player player;
    private static final int WIDTH = 400; // Width of the GUI
    private static final int HEIGHT = 300; // Height of the GUI
    private Button closeButton; // Button to close the GUI

    // Constructor
    public SkillsGuiScreen(Player player) {
        super(new TextComponent("Player Skills"));
        this.player = player;
    }

    @Override
    protected void init() {
        // Initialize buttons here
        closeButton = addRenderableWidget(new Button(this.width / 2 - 50, this.height / 2 + 120, 100, 20,
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
        drawSkills(poseStack); // Draw skills

        super.render(poseStack, mouseX, mouseY, partialTicks); // Call to super to render other elements
    }

    @Override
    public boolean isPauseScreen() {
        return false; // Make sure the game doesn't pause when the GUI is open
    }

    private void drawCustomBackground(PoseStack poseStack) {
        // Fill the background with a semi-transparent black color (ARGB format)
        fill(poseStack, (this.width - WIDTH) / 2, (this.height - HEIGHT) / 2,
                (this.width + WIDTH) / 2, (this.height + HEIGHT) / 2, 0xAA000000); // Semi-transparent black
    }

    private void drawTitle(PoseStack poseStack) {
        Minecraft minecraft = Minecraft.getInstance();
        String titleText = "Skills";
        int textColor = 0xFFFFFFFF; // White text

        // Draw the title at the top center
        int titleWidth = minecraft.font.width(titleText);
        minecraft.font.draw(poseStack, new TextComponent(titleText),
                (this.width - titleWidth) / 2, (this.height - HEIGHT) / 2 + 10, textColor);
    }

    private void drawSkills(PoseStack poseStack) {
        Minecraft minecraft = Minecraft.getInstance();
        List<String> skills = List.of("Swordsmanship", "Archery", "Magic", "Stealth"); // Replace with actual skills

        int textColor = 0xFFFFFFFF; // White text
        int skillYPos = (this.height - HEIGHT) / 2 + 35;

        for (String skill : skills) {
            minecraft.font.draw(poseStack, new TextComponent(skill),
                    (this.width - WIDTH) / 2 + 10, skillYPos, textColor);
            skillYPos += 15; // Increment Y position for each skill
        }

        // Example: Draw skill levels
        player.getCapability(PlayerLevelCapability.INSTANCE).ifPresent(cap -> {
            int level = cap.getLevel(); // Assuming getLevel() returns player level
            // Here you could also draw the skill levels next to the skills
            for (int i = 0; i < skills.size(); i++) {
                minecraft.font.draw(poseStack, new TextComponent("Level: " + level),
                        (this.width + WIDTH) / 2 - 100,
                        (this.height - HEIGHT) / 2 + 35 + (i * 15), textColor);
            }
        });
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Minecraft minecraft = Minecraft.getInstance();
            if (minecraft.screen instanceof SkillsGuiScreen gui) {
                PoseStack poseStack = new PoseStack(); // Create a new PoseStack
                gui.render(poseStack, 0, 0, 0); // Force render to update GUI
            }
        }
    }
}
