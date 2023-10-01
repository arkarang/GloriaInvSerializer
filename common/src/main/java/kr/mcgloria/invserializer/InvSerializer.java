package kr.mcgloria.invserializer;

import org.bukkit.inventory.ItemStack;

public interface InvSerializer {
    String itemStackArrayToBase64(ItemStack[] items);

    ItemStack[] itemStackArrayFromBase64(String data);

    String toBase64(ItemStack item);

    ItemStack fromBase64(String data);
}
