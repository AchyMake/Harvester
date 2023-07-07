package net.achymake.harvester.listeners;

import net.achymake.harvester.Harvester;
import net.achymake.harvester.files.BlocksConfig;
import net.achymake.harvester.files.Hoe;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class StoneHoeBlocks implements Listener {
    private Hoe getHoe() {
        return Harvester.getHoe();
    }
    private BlocksConfig getBlocksConfig() {
        return Harvester.getBlocksConfig();
    }
    public StoneHoeBlocks(Harvester plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onStoneHoeBlocks(PlayerInteractEvent event) {
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK))return;
        if (event.getClickedBlock() == null)return;
        if (!event.getPlayer().hasPermission("harvester.default"))return;
        if (!getHoe().isStoneHoe(event.getPlayer().getInventory().getItemInMainHand()))return;
        if (!getBlocksConfig().isEnable(event.getClickedBlock()))return;
        event.getPlayer().swingMainHand();
        getBlocksConfig().playHarvestSound(event.getPlayer(), event.getClickedBlock());
        getHoe().addDamage(event.getPlayer().getInventory().getItemInMainHand());
        getBlocksConfig().dropExperience(event.getPlayer(), event.getClickedBlock());
        getBlocksConfig().dropItems(event.getPlayer(), event.getClickedBlock());
        getHoe().isDestroyed(event.getPlayer(), event.getPlayer().getInventory().getItemInMainHand());
    }
}