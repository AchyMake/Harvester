package net.achymake.harvester.files;

import net.achymake.harvester.Harvester;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

public class Hoe {
    private FileConfiguration getConfig() {
        return Harvester.getConfiguration();
    }
    public boolean isDiamondHoe(ItemStack itemStack) {
        return itemStack.getType().equals(Material.DIAMOND_HOE);
    }
    public boolean isGoldenHoe(ItemStack itemStack) {
        return itemStack.getType().equals(Material.GOLDEN_HOE);
    }
    public boolean isIronHoe(ItemStack itemStack) {
        return itemStack.getType().equals(Material.IRON_HOE);
    }
    public boolean isNetheriteHoe(ItemStack itemStack) {
        return itemStack.getType().equals(Material.NETHERITE_HOE);
    }
    public boolean isStoneHoe(ItemStack itemStack) {
        return itemStack.getType().equals(Material.STONE_HOE);
    }
    public boolean isWoodenHoe(ItemStack itemStack) {
        return itemStack.getType().equals(Material.WOODEN_HOE);
    }
    public void addDamage(ItemStack itemStack) {
        int durability = itemStack.getItemMeta().getEnchantLevel(Enchantment.DURABILITY);
        Damageable toolHealthDamage = (Damageable) itemStack.getItemMeta();
        toolHealthDamage.setDamage(toolHealthDamage.getDamage() + 1 + getConfig().getInt("max-durability") - durability);
        itemStack.setItemMeta(toolHealthDamage);
    }
    public void isDestroyed(Player player, ItemStack itemStack) {
        if (isWoodenHoe(itemStack)) {
            Damageable toolHealthAfter = (Damageable) itemStack.getItemMeta();
            if (toolHealthAfter.getDamage() >= 59) {
                itemStack.setAmount(0);
                player.playSound(player, Sound.ENTITY_ITEM_BREAK, 1.0F, 1.0F);
            }
        }
        if (isStoneHoe(itemStack)) {
            Damageable toolHealthAfter = (Damageable) itemStack.getItemMeta();
            if (toolHealthAfter.getDamage() >= 131) {
                itemStack.setAmount(0);
                player.playSound(player, Sound.ENTITY_ITEM_BREAK, 1.0F, 1.0F);
            }
        }
        if (isIronHoe(itemStack)) {
            Damageable toolHealthAfter = (Damageable) itemStack.getItemMeta();
            if (toolHealthAfter.getDamage() >= 250) {
                itemStack.setAmount(0);
                player.playSound(player, Sound.ENTITY_ITEM_BREAK, 1.0F, 1.0F);
            }
        }
        if (isGoldenHoe(itemStack)) {
            Damageable toolHealthAfter = (Damageable) itemStack.getItemMeta();
            if (toolHealthAfter.getDamage() >= 32) {
                itemStack.setAmount(0);
                player.playSound(player, Sound.ENTITY_ITEM_BREAK, 1.0F, 1.0F);
            }
        }
        if (isDiamondHoe(itemStack)) {
            Damageable toolHealthAfter = (Damageable) itemStack.getItemMeta();
            if (toolHealthAfter.getDamage() >= 1561) {
                itemStack.setAmount(0);
                player.playSound(player, Sound.ENTITY_ITEM_BREAK, 1.0F, 1.0F);
            }
        }
        if (isNetheriteHoe(itemStack)) {
            Damageable toolHealthAfter = (Damageable) itemStack.getItemMeta();
            if (toolHealthAfter.getDamage() >= 2031) {
                itemStack.setAmount(0);
                player.playSound(player, Sound.ENTITY_ITEM_BREAK, 1.0F, 1.0F);
            }
        }
    }
}
