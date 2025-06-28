package me.jju.helloplugin;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class ShiftSwapListener implements Listener {
    private final HomeGuiManager guiManager;

    public ShiftSwapListener(HomeGuiManager guiManager) {
        this.guiManager = guiManager;
    }

    @EventHandler
    public void onSwap(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();
        if (player.isSneaking()) {
            guiManager.openMainGui(player);
        }
    }
}