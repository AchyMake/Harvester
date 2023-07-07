package net.achymake.harvester.listeners;

import net.achymake.harvester.Harvester;
import net.achymake.harvester.files.BlocksConfig;
import net.achymake.harvester.files.Hoe;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

import java.util.Random;

public class IronHoeBlocks implements Listener {
    private Hoe getHoe() {
        return Harvester.getHoe();
    }
    private BlocksConfig getBlocksConfig() {
        return Harvester.getBlocksConfig();
    }
    public IronHoeBlocks(Harvester plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onIronHoeBlocks(PlayerInteractEvent event) {
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK))return;
        if (event.getClickedBlock() == null)return;
        if (!event.getPlayer().hasPermission("harvester.default"))return;
        if (!getHoe().isIronHoe(event.getPlayer().getInventory().getItemInMainHand()))return;
        if (!getBlocksConfig().isEnable(event.getClickedBlock()))return;
        event.getPlayer().swingMainHand();
        getBlocksConfig().playHarvestSound(event.getPlayer(), event.getClickedBlock());
        getHoe().addDamage(event.getPlayer().getInventory().getItemInMainHand());
        getBlocksConfig().dropExperience(event.getPlayer(), event.getClickedBlock());
        getBlocksConfig().dropItems(event.getPlayer(), event.getClickedBlock());
        getHoe().isDestroyed(event.getPlayer(), event.getPlayer().getInventory().getItemInMainHand());
    }
}