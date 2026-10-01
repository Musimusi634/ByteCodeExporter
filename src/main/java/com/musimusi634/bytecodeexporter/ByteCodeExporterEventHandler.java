package com.musimusi634.bytecodeexporter;

import net.minecraft.network.chat.Component;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ByteCodeExporter.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = net.minecraftforge.api.distmarker.Dist.CLIENT)
public class ByteCodeExporterEventHandler {
    @SubscribeEvent
    public static void onPlayerLogin(ClientPlayerNetworkEvent.LoggingIn event){
        if (!event.getPlayer().level().isClientSide()) return;
        if (!ByteCodeExporter.AGENTLOADED) event.getPlayer().sendSystemMessage(Component.literal("[ByteCodeExporter] Agent is not Loaded. Pleas allow Self-Attach"));
    }
}
