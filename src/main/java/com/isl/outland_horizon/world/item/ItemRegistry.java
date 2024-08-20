package com.isl.outland_horizon.world.item;

import com.isl.outland_horizon.utils.Utils;
import com.isl.outland_horizon.world.block.BlockRegistry;
import com.isl.outland_horizon.world.item.consumables.Consumables;
import com.isl.outland_horizon.world.item.tools.multi.Destroyer;
import com.isl.outland_horizon.world.item.tools.multi.Hammer;
import com.isl.outland_horizon.world.item.tools.multi.Paxel;
import com.isl.outland_horizon.world.item.tools.multi.Spade;
import com.isl.outland_horizon.world.item.weapons.weapon.magic.wand.FireWand;
import com.isl.outland_horizon.world.item.weapons.weapon.melee.AAASword;
import com.isl.outland_horizon.world.item.weapons.weapon.ranged.gun.FrequencyVariation;
import com.isl.outland_horizon.world.item.weapons.weapon.ranged.gun.Genocide;
import com.isl.outland_horizon.world.item.weapons.weapon.ranged.gun.MaliciousGun;
import com.isl.outland_horizon.world.item.weapons.weapon.ranged.gun.VoidImpact;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ItemRegistry {
    public static List<RegistryObject<Item>> ITEM_LIST = new ArrayList<>();
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Utils.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);
    public static final RegistryObject<Item> BLOOD_BUCKET = register("blood_bucket", () -> new BucketItem(BlockRegistry.FluidRegistry.BLOOD, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<Item> DEBUG_SWORD = register("debug_sword", AAASword::new);
    public static final RegistryObject<Item> FIRE_WAND = register("fire_wand", FireWand::new);
    public static final RegistryObject<Item> MANA_POTION = register("mana_potion", ()-> new Consumables(new Item.Properties()));
    public static final RegistryObject<Item> NIGHTMARE_ENERGY = register("nightmare_energy", ()-> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MALICIOUS = register("malicious_gun", ()-> new MaliciousGun(450, 10, ItemRegistry.NIGHTMARE_ENERGY.get()));
    public static final RegistryObject<Item> FREQUENCY_VARIATION = register("frequency_variation", ()-> new FrequencyVariation(375, 10, Items.DIAMOND));
    public static final RegistryObject<Item> PAO = register("genocide", ()-> new Genocide(100, 10, Items.DIAMOND));
    public static final RegistryObject<Item> VOID_IMPACT = register("void_impact", ()-> new VoidImpact(3500, 10, Items.DIAMOND));

    private static void initVanillaExtends(String nameWithPrefix,Tier tier){
        Tier nTier = new Tier() {
            @Override
            public int getUses() {
                return Math.round(tier.getUses()*2.5f);
            }

            @Override
            public float getSpeed() {
                return tier.getSpeed()*0.9f;
            }

            @Override
            public float getAttackDamageBonus() {
                return tier.getAttackDamageBonus();
            }

            @Override
            public int getLevel() {
                return tier.getLevel();
            }

            @Override
            public int getEnchantmentValue() {
                return tier.getEnchantmentValue();
            }

            @Override
            public @NotNull Ingredient getRepairIngredient() {
                return tier.getRepairIngredient();
            }
        };
        ItemRegistry.register(nameWithPrefix + "_paxel", () -> new Paxel(nTier, new Item.Properties()));
        ItemRegistry.register(nameWithPrefix + "_hammer", () -> new Hammer(tier, new Item.Properties()));
        ItemRegistry.register(nameWithPrefix + "_spade", () -> new Spade(tier, new Item.Properties()));
        ItemRegistry.register(nameWithPrefix + "_destroyer", () -> new Destroyer(nTier, new Item.Properties()));
    }
    public static RegistryObject<Item> register(String id, Supplier<Item> item) {
        var object = ITEMS.register(id, item);
        ITEM_LIST.add(object);
        return object;
    }
    public static RegistryObject<Item> getItemRegistered(ResourceLocation resourceLocation){
        return ITEM_LIST.stream().filter(itemRegistryObject -> {
            if (itemRegistryObject.getKey() != null) {
                return itemRegistryObject.getKey().location().equals(resourceLocation);
            }
            return false;
        }).findFirst().orElseThrow();
    }

    public static void register(IEventBus bus) {
        initVanillaExtends("wooden",Tiers.WOOD);
        initVanillaExtends("golden",Tiers.GOLD);
        initVanillaExtends("stone",Tiers.STONE);
        initVanillaExtends("iron",Tiers.IRON);
        initVanillaExtends("diamond",Tiers.DIAMOND);
        initVanillaExtends("netherite",Tiers.NETHERITE);
        TABS.register("main", () -> CreativeModeTab.builder()
                .title(Component.translatable("item_group." + Utils.MOD_ID + ".main"))
                .icon(() -> new ItemStack(Items.APPLE))
                .displayItems((p, o) -> o.acceptAll(ITEM_LIST.stream().map(itemRegistryObject -> new ItemStack(itemRegistryObject.get())).collect(Collectors.toList())))
                .build());
        ITEMS.register(bus);
        TABS.register(bus);
    }
}
