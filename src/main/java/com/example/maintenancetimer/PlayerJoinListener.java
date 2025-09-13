package com.example.maintenancetimer;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.entity.Player;

public class PlayerJoinListener implements Listener {
    private final MaintenanceTimer plugin;

    public PlayerJoinListener(MaintenanceTimer plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        String time = plugin.getRemainingTime();
        if (time != null) {
            // show title for ~5 seconds (10 ticks fadeIn, 100 ticks stay, 10 ticks fadeOut -> adjust to ~5s total visible)
            // The stay parameter is in ticks; 100 ticks = 5 seconds.
            p.sendTitle("§cMaintenance in:", time, 10, 100, 10);
        }
    }
}
