package me.yourname.kyeongjae;

import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class PriceCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player p = (Player) sender;

        if (args.length != 1) {
            p.sendMessage("§c사용법: /가격 [다이아 수]");
            return true;
        }

        if (!MarketGUI.pendingAdd.containsKey(p.getUniqueId())) {
            p.sendMessage("§e먼저 /경재에서 아이템을 등록하려고 시도하세요.");
            return true;
        }

        int price;
        try {
            price = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            p.sendMessage("§c숫자만 입력하세요.");
            return true;
        }

        if (price <= 0) {
            p.sendMessage("§c가격은 1 이상이어야 합니다.");
            return true;
        }

        MarketItem mi = new MarketItem(p.getUniqueId(), MarketGUI.pendingAdd.get(p.getUniqueId()), price);
        MarketGUI.items.add(mi);
        MarketGUI.pendingAdd.remove(p.getUniqueId());
        p.sendMessage("§a아이템이 등록되었습니다. 가격: " + price + " 다이아몬드");
        return true;
    }
}
