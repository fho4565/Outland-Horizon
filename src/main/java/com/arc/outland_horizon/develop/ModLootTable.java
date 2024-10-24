package com.arc.outland_horizon.develop;

import com.arc.outland_horizon.registry.block.BlockRegistry;
import com.arc.outland_horizon.registry.item.ItemRegistry;
import com.arc.outland_horizon.utils.ToolPack;
import com.arc.outland_horizon.utils.Utils;
import com.arc.outland_horizon.utils.WorldUtils;
import com.google.gson.GsonBuilder;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootDataType;
import net.minecraft.world.level.storage.loot.LootTable;
import org.apache.commons.lang3.tuple.MutablePair;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class ModLootTable extends BlockLootSubProvider {
    private static final HashMap<ResourceLocation, LootTable.Builder> lootTables = new HashMap<>();
    public static final ModLootTable MOD_LOOT_TABLE = new ModLootTable();

    private ModLootTable() {
        super(Set.of(), FeatureFlagSet.of());
    }

    public int lootTables(MinecraftServer server) throws IOException {
        int generated = 0;
        List<ResourceLocation> list = server.getLootData().getKeys(LootDataType.TABLE).stream().toList();
        for (MutablePair<String,Boolean> s : ToolPack.nameWithPrefixList) {
            toolPackLootTable(s.left,s.right);
        }
        for (ResourceLocation key : lootTables.keySet()) {
            if(list.contains(key)){
                continue;
            }
            LootTable lootTable = lootTables.get(key).build();
            File recipeFile = new File(WorldUtils.getWorldFolderPath(server) + "\\todo\\loot_table\\" + key.getPath() + ".json");
            if (!recipeFile.exists()) {
                File dir = recipeFile.getParentFile();
                if (!dir.mkdirs() && !recipeFile.createNewFile()) {
                    Utils.LOGGER.error("Failed to create recipe file");
                }
            }
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(recipeFile));
            bufferedWriter.write(new GsonBuilder().setPrettyPrinting().create().toJson(LootDataType.TABLE.parser().toJsonTree(lootTable)));
            bufferedWriter.flush();
            bufferedWriter.close();
            generated++;
        }
        return generated;
    }

    public static ModLootTable of() {
        return MOD_LOOT_TABLE;
    }

    @Override
    protected void generate() {
    }

    public void oreDrop(Block block, Item item) {
        lootTables.put(block.getLootTable(), createOreDrop(block, item));
    }

    public void dropSelf(Block block) {
        lootTables.put(block.getLootTable(), this.createSingleItemTable(block.asItem()));
    }
    private void toolPackLootTable(String nameWithPrefix,boolean dropSelf){
        Block oreBlock = BlockRegistry.getBlockRegistered(Utils.createModResourceLocation(nameWithPrefix + "_ore"));
        Block deepOreBlock = BlockRegistry.getBlockRegistered(Utils.createModResourceLocation("deep_"+nameWithPrefix + "_ore"));
        Item item = ItemRegistry.getItemRegistered(Utils.createModResourceLocation(nameWithPrefix));
        if(dropSelf){
            dropSelf(oreBlock);
            dropSelf(deepOreBlock);
        }else{
            oreDrop(oreBlock,item);
            oreDrop(deepOreBlock,item);
        }
    }
}
