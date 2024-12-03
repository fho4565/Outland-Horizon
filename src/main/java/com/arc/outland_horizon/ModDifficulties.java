package com.arc.outland_horizon;

import com.arc.outland_horizon.utils.ChatUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;

import javax.annotation.Nullable;
import java.util.function.IntFunction;

public enum ModDifficulties implements StringRepresentable {
    DISABLED(0, "disabled"),//关闭模组难度调整
    DEATH(1, "death"),//死亡模式
    TRIBULATION(2, "tribulation"),//苦难模式
    ETERNAL(3, "eternal");//永恒模式

    public static final StringRepresentable.EnumCodec<ModDifficulties> CODEC = StringRepresentable.fromEnum(ModDifficulties::values);
    private static final IntFunction<ModDifficulties> BY_ID = ByIdMap.continuous(ModDifficulties::getId, values(), ByIdMap.OutOfBoundsStrategy.WRAP);
    private final int id;
    private final String key;

    ModDifficulties(int pId, String pKey) {
        this.id = pId;
        this.key = pKey;
    }

    public static ModDifficulties byId(int pId) {
        return BY_ID.apply(pId);
    }

    @Nullable
    public static ModDifficulties byName(String pName) {
        return CODEC.byName(pName);
    }

    public int getId() {
        return this.id;
    }

    public Component getDisplayName() {
        return ChatUtils.translatable("options.difficulty." + this.key);
    }

    public Component getInfo() {
        return ChatUtils.translatable("options.difficulty." + this.key + ".info");
    }

    public String getKey() {
        return this.key;
    }

    public String getSerializedName() {
        return this.key;
    }
}
