package me.yourname.kyeongjae;

import org.bukkit.inventory.ItemStack;
import java.util.UUID;

public class MarketItem {
    public UUID seller;
    public ItemStack item;
    public int price; // 다이아몬드 개수

    public MarketItem(UUID seller, ItemStack item, int price) {
        this.seller = seller;
        this.item = item;
        this.price = price;
    }
}
