package com.isl.outland_horizon.setup;

import com.isl.outland_horizon.utils.ChatUtils;
import com.isl.outland_horizon.utils.ManaUtils;
import com.isl.outland_horizon.utils.Utils;
import com.isl.outland_horizon.world.capability.ModCapabilities;
import com.isl.outland_horizon.world.capability.entity.OhAttribute;
import com.isl.outland_horizon.world.capability.provider.OhAttributeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Utils.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeCommonEvents {

    @SubscribeEvent
    public static void onAttachCaps(AttachCapabilitiesEvent<Entity> event) {
        if (!(event.getObject() instanceof Player)) return;

        event.addCapability(new ResourceLocation(Utils.MOD_ID, "attribute"), new OhAttributeProvider(event.getObject()));
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if (event.phase == TickEvent.Phase.END) {
            if (!player.level().isClientSide && player.getRandom().nextDouble() > 0.5D) {
                ManaUtils.recoverMana(player);
                ChatUtils.sendSimpleMessageToPlayer(player, "[Mana + 10]：" + ManaUtils.getMana(player));
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        // Inheritance capability
        event.getOriginal().reviveCaps();
        LazyOptional<OhAttribute> oldCap = event.getOriginal().getCapability(ModCapabilities.OH_ATTRIBUTE);
        LazyOptional<OhAttribute> newCap = event.getEntity().getCapability(ModCapabilities.OH_ATTRIBUTE);
        if (oldCap.isPresent() && newCap.isPresent()) {
            newCap.ifPresent((newCap1) -> oldCap.ifPresent((oldCap1) -> newCap1.deserializeNBT(oldCap1.serializeNBT())));
        }
        event.getOriginal().invalidateCaps();
    }
}
