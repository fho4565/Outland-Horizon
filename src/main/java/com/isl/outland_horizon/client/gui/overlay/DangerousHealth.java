package com.isl.outland_horizon.client.gui.overlay;

import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;

public class DangerousHealth extends HudSection{
    public int timer = 10;
    public boolean reverse;
    private final ArrayList<ResourceLocation> textures;
    public DangerousHealth() {
        super(0,0);
        textures = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            textures.add(new ResourceLocation("outland_horizon", "textures/gui/dangerous_screen/dangerous"+i+".png"));
        }

    }

    @Override
    public void render(Minecraft minecraft, GuiGraphics guiGraphics) {
        LocalPlayer player = minecraft.player;
        if (player == null) return;
        float healthScale = player.getHealth()/player.getMaxHealth();
        if(healthScale<=0.05f){
            Window window = minecraft.getWindow();
            guiGraphics.flush();
            guiGraphics.blit(textures.get(Math.round((float) timer /10)), 0, 0,
                    -1,
                    0,0,
                    window.getGuiScaledWidth() ,window.getGuiScaledHeight(),
                    window.getGuiScaledWidth(), window.getGuiScaledHeight());
            if(timer<=5){
                reverse = false;
            }
            if(timer>=94){
                reverse = true;
            }
            if(reverse){
                timer--;
            }else{
                timer++;
            }
        }
    }
}
