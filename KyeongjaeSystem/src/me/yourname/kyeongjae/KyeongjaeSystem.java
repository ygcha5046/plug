package me.yourname.kyeongjae;

import org.bukkit.plugin.java.JavaPlugin;

public class KyeongjaeSystem extends JavaPlugin {

    private static KyeongjaeSystem instance;

    @Override
    public void onEnable() {
        instance = this;

        // 명령어 등록
        getCommand("경재").setExecutor(new MarketCommand());
        getCommand("가격").setExecutor(new PriceCommand());

        // 이벤트 리스너 등록
        getServer().getPluginManager().registerEvents(new MarketGUI(), this);

        getLogger().info("✅ KyeongjaeSystem 플러그인이 활성화되었습니다!");
    }

    @Override
    public void onDisable() {
        getLogger().info("🛑 KyeongjaeSystem 플러그인이 비활성화되었습니다.");
    }

    public static KyeongjaeSystem getInstance() {
        return instance;
    }
}
