package com.legit.solo_leveling.event;

import com.legit.solo_leveling.capability.PlayerLevelCapability;
import com.legit.solo_leveling.capability.PlayerLevelCapabilityProvider;
import com.legit.solo_leveling.SoloLevelingMod;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SoloLevelingMod.MOD_ID)
public class EventHandler {

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.register(PlayerLevelCapability.class);
    }

    @SubscribeEvent
    public static void attachPlayerCapability(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            event.addCapability(new ResourceLocation(SoloLevelingMod.MOD_ID, "player_level"), new PlayerLevelCapabilityProvider());
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        event.getOriginal().getCapability(PlayerLevelCapability.INSTANCE).ifPresent(oldCap -> {
            event.getPlayer().getCapability(PlayerLevelCapability.INSTANCE).ifPresent(newCap -> {
                newCap.deserializeNBT(oldCap.serializeNBT());
            });
        });
    }

    @SubscribeEvent
    public static void onMobKill(LivingDeathEvent event) {
        if (event.getSource().getEntity() instanceof Player) {
            Player player = (Player) event.getSource().getEntity();
            player.getCapability(PlayerLevelCapability.INSTANCE).ifPresent(cap -> {
                cap.addXp(50, player); // Add XP

                // Display the action bar for a short time
                player.sendMessage(new TextComponent("Gained 50 XP!"), player.getUUID()); // Action bar message
            });
        }
    }
}
