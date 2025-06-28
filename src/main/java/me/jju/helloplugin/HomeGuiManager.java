
package me.jju.helloplugin;

import me.jju.helloplugin.Main; // ✅ 네가 만든 Main 클래스

//import com.sun.tools.javac.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class HomeGuiManager {
    private final Main plugin;
    private final HomeStorage homeStorage;

    public HomeGuiManager(Main plugin, HomeStorage homeStorage) {
        this.plugin = plugin;
        this.homeStorage = homeStorage;
    }

    public void openMainGui(Player player) {
        Inventory gui = Bukkit.createInventory(null, 54, ChatColor.GREEN + "커스텀 상자 GUI");
        ItemStack bed = new ItemStack(Material.RED_BED);
        ItemMeta bedMeta = bed.getItemMeta();
        bedMeta.setDisplayName(ChatColor.GOLD + "집");
        bed.setItemMeta(bedMeta);
        gui.setItem(10, bed);
        player.openInventory(gui);
    }

    public void openHomeMenu(Player player) {
        Inventory gui = Bukkit.createInventory(null, 27, ChatColor.YELLOW + "집 설정");

        ItemStack setHome = new ItemStack(Material.YELLOW_CONCRETE);
        ItemMeta setMeta = setHome.getItemMeta();
        setMeta.setDisplayName(ChatColor.GOLD + "집 설정");
        setHome.setItemMeta(setMeta);
        gui.setItem(11, setHome);

        for (int i = 0; i < 3; i++) {
            boolean hasHome = homeStorage.hasHome(player, i);
            Material material = hasHome ? Material.GREEN_CONCRETE : Material.RED_CONCRETE;

            ItemStack home = new ItemStack(material);
            ItemMeta meta = home.getItemMeta();
            meta.setDisplayName((hasHome ? ChatColor.GREEN : ChatColor.RED) +""+ (i + 1) + "번");
            home.setItemMeta(meta);
            gui.setItem(13 + i, home);
        }

        player.openInventory(gui);
    }


    public void openSetHomeGui(Player player) {
        Inventory gui = Bukkit.createInventory(null, 27, ChatColor.GOLD + "위치 선택");
        for (int i = 0; i < 3; i++) {
            ItemStack block = new ItemStack(Material.RED_CONCRETE);
            ItemMeta meta = block.getItemMeta();
            meta.setDisplayName(ChatColor.RED +""+ (i + 1) + "번");
            block.setItemMeta(meta);
            gui.setItem(11 + i, block);
        }
        player.openInventory(gui);
    }
}