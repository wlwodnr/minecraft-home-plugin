package me.jju.helloplugin;

import org.bukkit.ChatColor;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class HomeGuiListener implements Listener {
    private final HomeGuiManager guiManager;
    private final HomeStorage homeStorage;

    public HomeGuiListener(HomeGuiManager guiManager, HomeStorage homeStorage) {
        this.guiManager = guiManager;
        this.homeStorage = homeStorage;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        HumanEntity clicker = event.getWhoClicked();
        if (!(clicker instanceof Player player)) return;

        Inventory inv = event.getInventory();
        String title = event.getView().getTitle();

        ItemStack clicked = event.getCurrentItem();
        if (clicked == null || !clicked.hasItemMeta()) return;

        String itemName = clicked.getItemMeta().getDisplayName();

        // GUI 안에서 클릭 방지
        if (title.contains("커스텀 상자 GUI") || title.contains("집 설정") || title.contains("위치 선택")) {
            event.setCancelled(true);
        }

        if (title.contains("커스텀 상자 GUI") && itemName.contains("집")) {
            guiManager.openHomeMenu(player);
        } else if (title.contains("집 설정")) {
            if (itemName.contains("집 설정")) {
                guiManager.openSetHomeGui(player);
            } else if (itemName.matches(".*[1-3]번.*")) {
                int index = Integer.parseInt(itemName.replaceAll("[^0-9]", "")) - 1;
                homeStorage.teleportToHome(player, index);
            }
        } else if (title.contains("위치 선택") && itemName.matches(".*[1-3]번.*")) {
            int index = Integer.parseInt(itemName.replaceAll("[^0-9]", "")) - 1;
            homeStorage.savePlayerHome(player, index);
            player.sendMessage(ChatColor.YELLOW +""+ (index + 1) + "번 집 위치가 저장되었습니다!");
            guiManager.openHomeMenu(player);
        }
    }
}
