package org.achymake.harvester.listeners;

import org.achymake.harvester.Harvester;
import org.achymake.harvester.events.PlayerReplantEvent;
import org.achymake.harvester.handlers.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

public class PlayerReplant implements Listener {
    private Harvester getInstance() {
        return Harvester.getInstance();
    }
    private FileConfiguration getConfig() {
        return getInstance().getConfig();
    }
    private BlockHandler getBlockHandler() {
        return getInstance().getBlockHandler();
    }
    private GameModeHandler getGameModeHandler() {
        return getInstance().getGameModeHandler();
    }
    private MaterialHandler getMaterialHandler() {
        return getInstance().getMaterialHandler();
    }
    private RandomHandler getRandomHandler() {
        return getInstance().getRandomHandler();
    }
    private WorldHandler getWorldHandler() {
        return getInstance().getWorldHandler();
    }
    private PluginManager getPluginManager() {
        return getInstance().getPluginManager();
    }
    public PlayerReplant() {
        getPluginManager().registerEvents(this, getInstance());
    }
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerReplant(PlayerReplantEvent event) {
        if (event.isCancelled())return;
        var block = event.getClickedBlock();
        if (!getBlockHandler().isEnable(block))return;
        var player = event.getPlayer();
        var heldItem = player.getInventory().getItemInMainHand();
        var damage = getConfig().getInt("blocks." + block.getType() + ".damage");
        player.swingMainHand();
        getBlockHandler().playSound(block);
        getMaterialHandler().giveItemStacks(player, event.getDrops());
        if (getBlockHandler().isExperienceEnable(block)) {
            if (getRandomHandler().isTrue(getBlockHandler().getExperienceChance(block))) {
                getWorldHandler().spawnExperience(block.getLocation().add(0.5, 0.3, 0.5), getBlockHandler().getExperienceAmount(block));
            }
        }
        getBlockHandler().resetAge(block);
        if (!getMaterialHandler().isHoe(heldItem))return;
        if (!player.getGameMode().equals(getGameModeHandler().get("survival")))return;
        getMaterialHandler().addDamage(heldItem, damage);
        if (!getMaterialHandler().isDestroyed(heldItem))return;
        getMaterialHandler().breakItem(player.getLocation(), heldItem);
    }
}