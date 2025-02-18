package com.arc.outland_horizon.client;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class CustomToast implements Toast {
    private boolean playedSound;
    Component title = Component.literal("title");
    Component content = Component.literal("content");
    int titleColor = 0;
    int contentColor = 0;
    ItemStack item = new ItemStack(Items.STONE);
    ResourceLocation sound = new ResourceLocation("empty", "empty");
    boolean mute = false;

    private CustomToast() {
    }

    private CustomToast(Component title, Component content, ItemStack item, ResourceLocation sound, int titleColor, int contentColor) {
        this.titleColor = titleColor;
        this.contentColor = contentColor;
        this.sound = sound;
        this.content = content;
        this.item = item;
        this.title = title;
    }

    private CustomToast(Component title, Component content, ItemStack item, int titleColor, int contentColor) {
        this.titleColor = titleColor;
        this.contentColor = contentColor;
        this.mute = true;
        this.content = content;
        this.item = item;
        this.title = title;
    }

    public static class Builder {
        CustomToast customToast = new CustomToast();

        public static Builder of() {
            return new Builder();
        }

        public Builder title(Component component) {
            this.customToast.title = component;
            return this;
        }

        public Builder content(Component component) {
            this.customToast.content = component;
            return this;
        }

        public Builder titleColor(int color) {
            this.customToast.titleColor = color;
            return this;
        }

        public Builder contentColor(int color) {
            this.customToast.contentColor = color;
            return this;
        }

        public Builder item(ItemStack item) {
            this.customToast.item = item;
            return this;
        }

        public Builder sound(SoundEvent sound) {
            this.customToast.sound = sound.getLocation();
            return this;
        }

        public Builder mute(boolean mute) {
            this.customToast.mute = mute;
            return this;
        }

        public CustomToast build() {
            return customToast;
        }
    }

    public Toast.@NotNull Visibility render(GuiGraphics pGuiGraphics, ToastComponent pToastComponent, long pTimeSinceLastVisible) {
        pGuiGraphics.blit(TEXTURE, 0, 0, 0, 0, this.width(), this.height());
        Font font = pToastComponent.getMinecraft().font;
        pGuiGraphics.drawString(font, title, 30, 7, titleColor, false);
        pGuiGraphics.drawString(font, content, 30, 18, contentColor, false);
        if (!mute) {
            if (this.sound.compareTo(Objects.requireNonNull(ResourceLocation.tryBuild("minecraft", "empty"))) != 0 && !playedSound && pTimeSinceLastVisible > 0L) {
                this.playedSound = true;
                pToastComponent.getMinecraft().getSoundManager()
                        .play(SimpleSoundInstance.forUI(SoundEvent.createVariableRangeEvent(sound),
                                1.0F,
                                1.0F
                        ));
            }
        }
        pGuiGraphics.renderFakeItem(item, 8, 8);
        return pTimeSinceLastVisible >= 5000.0D * pToastComponent.getNotificationDisplayTimeMultiplier() ? Toast.Visibility.HIDE : Toast.Visibility.SHOW;
    }

    public String save() {
        CompoundTag tag = new CompoundTag();
        tag.put("title", StringTag.valueOf(Component.Serializer.toJson(title)));
        tag.put("content", StringTag.valueOf(Component.Serializer.toJson(content)));
        tag.put("titleColor", StringTag.valueOf(String.valueOf(titleColor)));
        tag.put("contentColor", StringTag.valueOf(String.valueOf(contentColor)));
        tag.put("item", item.save(new CompoundTag()));
        tag.put("sound", StringTag.valueOf(sound.toString()));
        return tag.toString();
    }

    public static CustomToast load(String tag) throws CommandSyntaxException {
        CompoundTag t = TagParser.parseTag(tag);
        return new CustomToast(Component.Serializer.fromJson(t.getString("title")),
                Component.Serializer.fromJson(t.getString("content")),
                ItemStack.of(t.getCompound("item")),
                new ResourceLocation(t.getString("sound")),
                Integer.parseInt(t.getString("titleColor")),
                Integer.parseInt(t.getString("contentColor"))
        );
    }
}
