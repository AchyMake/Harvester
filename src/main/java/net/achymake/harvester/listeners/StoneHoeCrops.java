package net.achymake.harvester.listeners;

import net.achymake.harvester.Harvester;
import net.achymake.harvester.files.CropsConfig;
import net.achymake.harvester.files.Hoe;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class StoneHoeCrops implements Listener {
    private Hoe getHoe() {
        return Harvester.getHoe();
    }
    private CropsConfig getCropsConfig() {
        return Harvester.getCropsConfig();
    }
    public StoneHoeCrops(Harvester plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onStoneHoeCrops(PlayerInteractEvent event) {
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK))return;
        if (event.getClickedBlock() == null)return;
        if (!event.getPlayer().hasPermission("harvester.default"))return;
        if (!getHoe().isStoneHoe(event.getPlayer().getInventory().getItemInMainHand()))return;
        if (!getCropsConfig().isEnable(event.getClickedBlock()))return;
        if (!getCropsConfig().isRightAge(event.getClickedBlock()))return;
        getCropsConfig().playHarvestSound(event.getPlayer(), event.getClickedBlock());
        getCropsConfig().resetAge(event.getClickedBlock());
        event.getPlayer().swingMainHand();
        getHoe().addDamage(event.getPlayer().getInventory().getItemInMainHand());
        getCropsConfig().dropItems(event.getPlayer(), event.getClickedBlock());
        getCropsConfig().dropExperience(event.getPlayer(), event.getClickedBlock());
        getHoe().isDestroyed(event.getPlayer(), event.getPlayer().getInventory().getItemInMainHand());
    }
}