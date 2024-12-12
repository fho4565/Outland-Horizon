package com.arc.outland_horizon.develop;

import com.arc.outland_horizon.registry.BlockRegistry;
import com.arc.outland_horizon.registry.ItemRegistry;
import com.arc.outland_horizon.registry.OHMobEffects;
import com.arc.outland_horizon.utils.Utils;
import com.arc.outland_horizon.world.entity.EntityRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraftforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.function.Predicate;

public class ModLang {
    public static final HashSet<String> lang = new HashSet<>();
    private static final String out = """
            "%s" : "%s",
            """;
    private static int count = 0;

    private static void check() {
        pre("item", ItemRegistry.ITEMS, item -> item instanceof BlockItem);
        pre("block", BlockRegistry.BLOCKS, item -> false);
        pre("entity", EntityRegistry.ENTITIES, item -> false);
        pre("effect", OHMobEffects.EFFECTS, item -> false);
    }

    private static <T> void pre(String pre, DeferredRegister<T> register, Predicate<T> predicate) {
        register.getEntries().forEach(entry -> {
            if (predicate.test(entry.get())) {
                return;
            }
            String key = pre + "." + entry.getId().toString().replace(":", ".");
            lang.add(key);
        });
    }

    private static void clear() {
        count = 0;
        lang.removeIf(s -> s.startsWith("item.") || s.startsWith("block.") || s.startsWith("entity.") || s.startsWith("effect."));
    }

    public static int generate() throws IOException {
        clear();
        check();
        String userHome = System.getProperty("user.home");
        String desktopPath = userHome + java.io.File.separator + "Desktop";

        File file = new File(desktopPath + "\\todo\\lang\\" + Minecraft.getInstance().getLanguageManager().getSelected() + ".json");
        if (!file.exists()) {
            File dir = new File(desktopPath + "\\todo\\lang\\");
            if (!dir.mkdirs() && !file.createNewFile()) {
                Utils.LOGGER.error("Failed to create lang file");
            }
        }
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        StringBuilder stringBuilder = generateStringBuilder();
        if (!stringBuilder.toString().equals("{}") && stringBuilder.toString().contains(",")) {
            stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","));
            writeFile(bufferedWriter, stringBuilder.toString());
        }
        bufferedWriter.close();
        return count;
    }

    private static @NotNull StringBuilder generateStringBuilder() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{\n");
        for (String s : lang) {
            if (Component.translatable(s).plainCopy().getString().equals(s)) {
                stringBuilder.append(out.formatted(s, "EMPTY"));
                count++;
            }
        }
        stringBuilder.append("}\n");
        return stringBuilder;
    }

    private static void writeFile(BufferedWriter bufferedWriter, String content) throws IOException {
        bufferedWriter.write(content);
        bufferedWriter.flush();
    }
}
