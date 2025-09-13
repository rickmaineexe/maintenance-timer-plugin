package com.example.maintenancetimer;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.Duration;
import java.time.Instant;

public class MaintenanceTimer extends JavaPlugin {

    private Instant endTime = null;
    private BukkitRunnable shutdownTask = null;

    @Override
    public void onEnable() {
        getLogger().info("MaintenanceTimer aktiviert");
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
    }

    @Override
    public void onDisable() {
        if (shutdownTask != null) shutdownTask.cancel();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("timermaint")) return false;

        if (!sender.isOp()) {
            sender.sendMessage("§cNur OPs können das Kommando nutzen.");
            return true;
        }

        if (args.length != 4) {
            sender.sendMessage("§cBenutzung: /timermaint <days> <hours> <minutes> <seconds>");
            return true;
        }

        try {
            long days = Long.parseLong(args[0]);
            long hours = Long.parseLong(args[1]);
            long minutes = Long.parseLong(args[2]);
            long seconds = Long.parseLong(args[3]);

            Duration dur = Duration.ofDays(days).plusHours(hours).plusMinutes(minutes).plusSeconds(seconds);
            if (dur.isZero() || dur.isNegative()) {
                sender.sendMessage("§cDie Zeit muss größer als 0 sein.");
                return true;
            }

            endTime = Instant.now().plus(dur);
            sender.sendMessage("§aMaintenance-Timer gesetzt: " + formatDuration(dur));

            // cancel previous task if exists
            if (shutdownTask != null) shutdownTask.cancel();

            shutdownTask = new BukkitRunnable() {
                @Override
                public void run() {
                    if (endTime == null) {
                        cancel();
                        return;
                    }
                    if (Instant.now().isAfter(endTime)) {
                        Bukkit.getLogger().info("Maintenance-Timer abgelaufen. Server stoppt...");
                        Bukkit.shutdown();
                        cancel();
                    }
                }
            };
            // run every second
            shutdownTask.runTaskTimer(this, 20L, 20L);

        } catch (NumberFormatException e) {
            sender.sendMessage("§cBitte nur Zahlen benutzen.");
        }

        return true;
    }

    public String getRemainingTime() {
        if (endTime == null) return null;
        Duration remaining = Duration.between(Instant.now(), endTime);
        if (remaining.isNegative() || remaining.isZero()) return null;
        return formatDuration(remaining);
    }

    private String formatDuration(Duration d) {
        long totalSec = d.getSeconds();
        long days = totalSec / 86400;
        long hours = (totalSec % 86400) / 3600;
        long minutes = (totalSec % 3600) / 60;
        long seconds = totalSec % 60;
        return days + "d " + hours + "h " + minutes + "m " + seconds + "s";
    }
}
