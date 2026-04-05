package org.achymake.harvester.listeners;

import org.achymake.harvester.Harvester;
import org.achymake.harvester.events.PlayerReplantEvent;
import org.achymake.harvester.handlers.BlockHandler;
import org.achymake.harvester.handlers.GameModeHandler;
import org.achymake.harvester.handlers.MaterialHandler;
import org.achymake.harvester.handlers.WorldHandler;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.PluginManager;

public class PlayerInteract implements Listener {
    private Harvester getInstance() {
        return Harvester.getInstance();
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
    private WorldHandler getWorldHandler() {
        return getInstance().getWorldHandler();
    }
    private PluginManager getPluginManager() {
        return getInstance().getPluginManager();
    }
    public PlayerInteract() {
        getPluginManager().registerEvents(this, getInstance());
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK))return;
        var block = event.getClickedBlock();
        if (block == null)return;
        if (event.getHand() != EquipmentSlot.HAND)return;
        if (!getBlockHandler().isEnable(block))return;
        if (!getBlockHandler().isRightAge(block))return;
        if (!getWorldHandler().isReplantAllowed(block))return;
        var player = event.getPlayer();
        if (player.getGameMode().equals(getGameModeHandler().get("spectator")))return;
        var heldItem = player.getInventory().getItemInMainHand();
        if (getMaterialHandler().isHoe(heldItem)) {
            if (!player.hasPermission("harvester.event.replant.hoe"))return;
            if (!getMaterialHandler().hasEnchantment(heldItem))return;
        } else if (!player.hasPermission("harvester.event.replant.hand"))return;
        event.setUseItemInHand(Event.Result.DENY);
        event.setUseInteractedBlock(Event.Result.DENY);
        var fortune = heldItem.getEnchantmentLevel(getMaterialHandler().getEnchantment("fortune"));
        var drops = getBlockHandler().getDrops(block.getType(), fortune);
        getPluginManager().callEvent(new PlayerReplantEvent(player, block, drops));
    }
}