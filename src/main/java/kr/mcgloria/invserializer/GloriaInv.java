package kr.mcgloria.invserializer;

import kr.mcgloria.invserializer.mc1182.V118R1Zstd;
import kr.mcgloria.invserializer.mc1194.V119R3Zstd;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GloriaInv {

    private static InvSerializer serializer;

    public static void init() {
        String version = Bukkit.getBukkitVersion();
        switch (version) {
            case "1.19.4-R0.1-SNAPSHOT" -> {
                GloriaInv.serializer = new V119R3Zstd();
                Bukkit.getLogger().info("initialized inventory serializer as "+version);
            }
            case "1.18.2-R0.1-SNAPSHOT" -> {
                GloriaInv.serializer = new V118R1Zstd();
                Bukkit.getLogger().info("initialized inventory serializer as "+version);
            }
            default -> { Bukkit.getLogger().warning("cannot find version of "+version); }
        }
    }

    public static String itemStackArrayToBase64(ItemStack[] items) {
        return GloriaInv.serializer.itemStackArrayToBase64(items);
    }

    public static ItemStack[] itemStackArrayFromBase64(String data) {
        return GloriaInv.serializer.itemStackArrayFromBase64(data);
    }

    public static String toBase64(ItemStack item) {
        return GloriaInv.serializer.toBase64(item);
    }

    public static ItemStack fromBase64(String data) {
        return GloriaInv.serializer.fromBase64(data);
    }
}
