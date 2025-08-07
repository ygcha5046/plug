package me.yourname.kyeongjae;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class MarketGUI implements Listener {

    private static final List<MarketItem> items = new ArrayList<>();

    public static void openMarket(Player player) {
        Inventory inv = Bukkit.createInventory(null, 54, "📦 거래 시장");

        for (MarketItem mi : items) {
            ItemStack displayItem = mi.item.clone();
            ItemMeta meta = displayItem.getItemMeta();
            meta.setLore(Arrays.asList(ChatColor.GRAY + "가격: " + mi.price + " 다이아몬드"));
            displayItem.setItemMeta(meta);
            inv.addItem(displayItem);
        }

        // 마지막 칸에 "아이템 등록" 버튼
        ItemStack registerButton = new ItemStack(Material.EMERALD);
        ItemMeta meta = registerButton.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "아이템 등록하기");
        registerButton.setItemMeta(meta);
        inv.setItem(53, registerButton);

        player.openInventory(inv);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getView().getTitle().equals("📦 거래 시장")) {
            e.setCancelled(true);

            Player p = (Player) e.getWhoClicked();
            ItemStack clicked = e.getCurrentItem();
            if (clicked == null) return;

            if (clicked.getType() == Material.EMERALD) {
                p.sendMessage("§a인벤토리에서 아이템을 들고 가격을 입력하세요 (/가격 [숫자])");
                pendingAdd.put(p.getUniqueId(), p.getItemOnCursor().clone());
                p.closeInventory();
            } else {
                for (MarketItem mi : items) {
                    if (mi.item.isSimilar(clicked)) {
                        // 구매 처리
                        int diamondCount = countDiamonds(p);
                        if (diamondCount >= mi.price) {
                            removeDiamonds(p, mi.price);
                            p.getInventory().addItem(mi.item);
                            Player seller = Bukkit.getPlayer(mi.seller);
                            if (seller != null && seller.isOnline()) {
                                seller.getInventory().addItem(new ItemStack(Material.DIAMOND, mi.price));
                                seller.sendMessage("§b" + p.getName() + "님이 당신의 아이템을 구매했습니다!");
                            }
                            items.remove(mi);
                            p.sendMessage("§a구매 완료!");
                            p.closeInventory();
                        } else {
                            p.sendMessage("§c다이아몬드가 부족합니다.");
                        }
                        break;
                    }
                }
            }
        }
    }

    // 가격 입력 대기 목록
    public static Map<UUID, ItemStack> pendingAdd = new HashMap<>();

    // 다이아 수 계산
    private int countDiamonds(Player p) {
        int count = 0;
        for (ItemStack i : p.getInventory().getContents()) {
            if (i != null && i.getType() == Material.DIAMOND) {
                count += i.getAmount();
            }
        }
        return count;
    }

    private void removeDiamonds(Player p, int amount) {
        int toRemove = amount;
        ItemStack[] contents = p.getInventory().getContents();
        for (int i = 0; i < contents.length; i++) {
            ItemStack item = contents[i];
            if (item != null && item.getType() == Material.DIAMOND) {
                if (item.getAmount() > toRemove) {
                    item.setAmount(item.getAmount() - toRemove);
                    break;
                } else {
                    toRemove -= item.getAmount();
                    contents[i] = null;
                }
            }
        }
        p.getInventory().setContents(contents);
    }
}
