package com.musimusi634.bytecodeexporter;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.lang.reflect.Method;
import java.nio.file.Files;

@Mod.EventBusSubscriber(modid = ByteCodeExporter.MODID)
public class ByteCodeExporterCommands {
    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event){
        event.getDispatcher().register(
                Commands.literal("exportbytecode").requires(source -> source.hasPermission(3))
                        .then(Commands.argument("classname", StringArgumentType.string())
                                .executes(ctx -> {
                                            if (!ByteCodeExporter.AGENTLOADED){
                                                ctx.getSource().sendFailure(Component.literal("ByteCode Export failed: Agent is not Loaded. Pleas allow Self-Attach"));
                                                return 1;
                                            }
                                            String path = StringArgumentType.getString(ctx, "classname");
                                            String fileName = path.substring(path.lastIndexOf('.') + 1) + ".class";
                                            try {
                                                Method method = Class.forName("com.musimusi634.bytecodeexporter.agent.ByteCodeExporterAgent",true, ClassLoader.getSystemClassLoader()).getMethod("getByteCode", String.class);
                                                Files.write(ByteCodeExporter.DIR.resolve(fileName),(byte[]) method.invoke(null,path));
                                                ctx.getSource().sendSuccess(() -> Component.literal("Bytecode Exported: " + fileName),true);
                                            }catch (Exception e){
                                                ctx.getSource().sendFailure(Component.literal("ByteCode Export failed: " + fileName));
                                                ByteCodeExporter.LOGGER.error("ByteCode Export failed",e);
                                            }
                                            return 1;
                                        }
                                )
                        )
        );
    }
}
