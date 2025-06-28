package me.jju.helloplugin;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private HomeGuiManager guiManager;
    private HomeStorage homeStorage;

    @Override
    public void onEnable() {
        getLogger().info("플러그인이 활성화되었습니다!");

        this.homeStorage = new HomeStorage(this);
        this.guiManager = new HomeGuiManager(this, homeStorage);


        getServer().getPluginManager().registerEvents(new ShiftSwapListener(guiManager), this);
        getServer().getPluginManager().registerEvents(new HomeGuiListener(guiManager, homeStorage), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("플러그인이 비활성화되었습니다.");
    }
}