package com.legit.solo_leveling;

import com.legit.solo_leveling.event.EventHandler;
import com.legit.solo_leveling.network.NetworkHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(SoloLevelingMod.MOD_ID)
public class SoloLevelingMod {
    public static final String MOD_ID = "solo_leveling";

    public SoloLevelingMod() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        MinecraftForge.EVENT_BUS.register(new EventHandler());
        NetworkHandler.registerPackets();
    }

    private void setup(FMLCommonSetupEvent event) {
        // Setup initialization if needed
    }
}
