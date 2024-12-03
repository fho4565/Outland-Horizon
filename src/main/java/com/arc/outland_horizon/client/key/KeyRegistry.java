package com.arc.outland_horizon.client.key;

import com.arc.outland_horizon.utils.ChatUtils;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.IKeyConflictContext;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

import java.util.Set;

public class KeyRegistry {
    public static final Set<KeyMapping> keys = Sets.newHashSet();
    public static final KeyMapping KEY_RAGE = register(ChatUtils.translatable("text.outland_horizon.key.rage.description").plainCopy().getString(), ChatUtils.translatable("text.outland_horizon.mod.name").plainCopy().getString(), KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_J);
    public static final KeyMapping KEY_TRIGGER_SKILL = register(ChatUtils.translatable("text.outland_horizon.key.skill.trigger").plainCopy().getString(), ChatUtils.translatable("text.outland_horizon.mod.name").plainCopy().getString(), KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_Z);
    public static final KeyMapping KEY_SWITCH_SKILL = register(ChatUtils.translatable("text.outland_horizon.key.skill.switch").plainCopy().getString(), ChatUtils.translatable("text.outland_horizon.mod.name").plainCopy().getString(), KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_X);

    public static KeyMapping register(String description, String category,
                                      IKeyConflictContext keyConflictContext,
                                      InputConstants.Type inputType, int keyCode) {
        KeyMapping key = new KeyMapping(description, keyConflictContext, inputType, keyCode, category);
        keys.add(key);
        return key;
    }

    public static void register(RegisterKeyMappingsEvent event) {
        keys.forEach(event::register);
    }
}