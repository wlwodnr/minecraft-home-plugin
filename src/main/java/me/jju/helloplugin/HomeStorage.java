package me.jju.helloplugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;

import java.io.File;
import java.io.IOException;

public class HomeStorage {
    private final Main plugin;
    private final File file;
    private final FileConfiguration config;

    public boolean hasHome(Player player, int index) {
        String base = player.getUniqueId().toString() + ".home" + index;
        return config.contains(base + ".world");
    }


    public HomeStorage(Main plugin) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), "homes.yml");

        if (!file.exists()) {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile(); // 직접 파일 생성
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public void savePlayerHome(Player player, int index) {
        Location loc = player.getLocation();
        String base = player.getUniqueId().toString() + ".home" + index;
        config.set(base + ".world", loc.getWorld().getName());
        config.set(base + ".x", loc.getX());
        config.set(base + ".y", loc.getY());
        config.set(base + ".z", loc.getZ());
        save();
    }

    public void teleportToHome(Player player, int index) {
        String base = player.getUniqueId().toString() + ".home" + index;
        if (config.contains(base)) {
            String world = config.getString(base + ".world");
            double x = config.getDouble(base + ".x");
            double y = config.getDouble(base + ".y");
            double z = config.getDouble(base + ".z");
            Location loc = new Location(Bukkit.getWorld(world), x, y, z);
            player.teleport(loc);
            player.sendMessage(ChatColor.GREEN + "집 " + (index + 1) + "번으로 이동했습니다!");
        } else {
            player.sendMessage(ChatColor.RED + "해당 위치가 저장되어 있지 않습니다.");
        }
    }

    private void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
