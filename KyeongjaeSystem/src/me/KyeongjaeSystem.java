package me.yourname.kyeongjae;

import org.bukkit.plugin.java.JavaPlugin;

public class KyeongjaeSystem extends JavaPlugin {

    private static KyeongjaeSystem instance;

    @Override
    public void onEnable() {
        instance = this;
        getCommand("경재").setExecutor(new MarketCommand());
        getServer().getPluginManager().registerEvents(new MarketGUI(), this);
    }

    public static KyeongjaeSystem getInstance() {
        return instance;
    }
}
