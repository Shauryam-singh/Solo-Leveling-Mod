package com.legit.solo_leveling.client;

import com.legit.solo_leveling.capability.PlayerLevelCapability;
import com.legit.solo_leveling.SoloLevelingMod;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SoloLevelingMod.MOD_ID, value = Dist.CLIENT)
public class OverlayRenderer {

    private static int xpDisplayTicks = 0;
    private static String lastXpMessage = "";

    public static void displayXpGain(int xp) {
        lastXpMessage = "Gained " + xp + " XP!";
        xpDisplayTicks = 40; // Display for 2 seconds (20 ticks per second)
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (xpDisplayTicks > 0) {
            xpDisplayTicks--;
            Minecraft.getInstance().player.displayClientMessage(new TextComponent(lastXpMessage), true);
        }
    }
}
