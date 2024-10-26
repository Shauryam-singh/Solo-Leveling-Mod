package com.legit.solo_leveling.client;

import com.legit.solo_leveling.SoloLevelingMod;
import com.legit.solo_leveling.gui.StatsGuiScreen;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.KeyMapping;

@Mod.EventBusSubscriber(modid = SoloLevelingMod.MOD_ID, value = Dist.CLIENT)
public class KeyBindings {
    public static final KeyMapping STATS_GUI_KEY = new KeyMapping("key.stats_gui", InputConstants.KEY_O, "key.categories.ui");

    @SubscribeEvent
    public static void registerKeyBindings(FMLClientSetupEvent event) {
        ClientRegistry.registerKeyBinding(STATS_GUI_KEY);
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent event) {
        if (STATS_GUI_KEY.isDown()) {
            Minecraft minecraft = Minecraft.getInstance();
            Player player = minecraft.player;
            if (player != null) {
                minecraft.setScreen(new StatsGuiScreen(player)); // Pass the current player to the GUI
            }
        }
    }
}
