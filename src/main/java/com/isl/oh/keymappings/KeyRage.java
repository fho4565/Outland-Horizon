package com.isl.oh.keymappings;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class KeyRage {
    public static final KeyMapping KEY_RAGE = new KeyMapping("des", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_J, "type");
    @SubscribeEvent
    public static void onClientTick(TickEvent.PlayerTickEvent event) {
        if (KEY_RAGE.isDown()) {
            LocalPlayer player = Minecraft.getInstance().player;
            player.sendSystemMessage(Component.literal("你怒了！！！！！"));
        }
    }
}
