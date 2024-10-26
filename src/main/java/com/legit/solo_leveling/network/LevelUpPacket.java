package com.legit.solo_leveling.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import com.legit.solo_leveling.gui.LevelUpGuiScreen;
import net.minecraft.client.Minecraft;

import java.util.function.Supplier;

public class LevelUpPacket {
    public LevelUpPacket() {
        // Empty constructor needed for the packet
    }

    public LevelUpPacket(FriendlyByteBuf buf) {
        // Deserialize packet data if needed
    }

    public void toBytes(FriendlyByteBuf buf) {
        // Serialize packet data if needed
    }

    public boolean handle(Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            // This code is run on the client thread to open the GUI
            Minecraft.getInstance().setScreen(new LevelUpGuiScreen(Minecraft.getInstance().player));
        });
        return true;
    }
}
