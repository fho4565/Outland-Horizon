package com.arc.outland_horizon;

import com.arc.outland_horizon.utils.Utils;
import com.arc.outland_horizon.utils.WorldUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.File;
import java.io.IOException;

import static com.arc.outland_horizon.utils.Utils.LOGGER;

@Mod.EventBusSubscriber
public class OHDataManager {
    public static ModDifficulties modDifficulties;
    private static OHData data;
    private static File file;


    @SubscribeEvent
    public static void onLevelLoad(ServerStartedEvent event) {
        data = new OHData(event.getServer());
    }

    @SubscribeEvent
    public static void onLevelSave(LevelEvent.Save event) {
        if (event.getLevel() instanceof ServerLevel serverLevel) {
            if (serverLevel.dimension().equals(Level.OVERWORLD)) {
                data.save();
                Utils.LOGGER.info(OutlandHorizon.MOD_NAME + " mod data saved");
            }
        }
    }

    public static void save() {
        if (data != null) {
            data.save();
        }
    }

    private static class OHData {
        public OHData(MinecraftServer server) {
            file = new File(WorldUtils.getWorldFolderPath(server) + "\\data\\outland_horizon.dat");
            if (!file.exists()) {
                try {
                    if (!file.createNewFile()) {
                        LOGGER.error("Could not create more command data file：");
                    }
                } catch (IOException e) {
                    LOGGER.error(e.getStackTrace());
                }
                init();
                load();
            } else {
                load();
            }
        }

        public void save() {
            try {
                CompoundTag root = new CompoundTag();
                root.putInt("modDifficulties", modDifficulties.getId());
                NbtIo.writeCompressed(root, file);
            } catch (IOException e) {
                LOGGER.error("Problem occurred when saving data file: {}", e.getMessage());
            }
        }

        public void load() {
            try {
                CompoundTag tag = NbtIo.readCompressed(file);
                modDifficulties = ModDifficulties.byId(tag.getInt("modDifficulties"));
            } catch (IOException e) {
                LOGGER.error("Failed to load data file: {}", e.getMessage());
            }
        }

        private void init() {
            CompoundTag root = new CompoundTag();
            root.putInt("modDifficulties", ModDifficulties.DISABLED.getId());
            try {
                NbtIo.writeCompressed(root, file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            modDifficulties = ModDifficulties.DISABLED;
        }
    }
}
