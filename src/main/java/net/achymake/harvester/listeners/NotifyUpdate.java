package net.achymake.harvester.listeners;

import net.achymake.harvester.Harvester;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class NotifyUpdate implements Listener {
    public NotifyUpdate(Harvester plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onNotifyUpdate(PlayerJoinEvent event) {
        if (event.getPlayer().hasPermission("harvester.command"))return;
        Harvester.getUpdate(event.getPlayer());
    }
}
