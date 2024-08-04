package com.isl.outland_horizon.client.key;

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
    public static final KeyMapping KEY_RAGE = register("des", "type", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_J);

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