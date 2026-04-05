package org.achymake.harvester.handlers;

import org.achymake.harvester.Harvester;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

import java.util.Collection;

public class MaterialHandler {
    private Harvester getInstance() {
        return Harvester.getInstance();
    }
    private FileConfiguration getConfig() {
        return getInstance().getConfig();
    }
    private RandomHandler getRandomHandler() {
        return getInstance().getRandomHandler();
    }
    private WorldHandler getWorldHandler() {
        return getInstance().getWorldHandler();
    }
    public Material get(String materialName) {
        return Material.getMaterial(materialName.toUpperCase());
    }
    public ItemStack getItemStack(String materialName, int amount) {
        return new ItemStack(get(materialName.toUpperCase()), amount);
    }
    public void giveItemStack(Player player, ItemStack itemStack) {
        if (itemStack == null)return;
        var rest = player.getInventory().addItem(itemStack).values();
        if (rest.isEmpty())return;
        var location = player.getLocation();
        rest.forEach(itemStacks -> getWorldHandler().spawnItem(location, itemStack));
    }
    public void giveItemStacks(Player player, Collection<ItemStack> itemStacks) {
        itemStacks.forEach(itemStack -> giveItemStack(player, itemStack));
    }
    public boolean hasEnchantment(ItemStack itemStack) {
        var enchantmentName = getConfig().getString("replant.enchantment");
        if (enchantmentName.equalsIgnoreCase("none")) {
            return true;
        } else return itemStack.getItemMeta().hasEnchant(getEnchantment(enchantmentName));
    }
    public Enchantment getEnchantment(String enchantmentName) {
        return Enchantment.getByName(enchantmentName.toUpperCase());
    }
    public boolean isHoe(ItemStack itemStack) {
        return isWoodenHoe(itemStack) || isStoneHoe(itemStack) ||
                isCopperHoe(itemStack) || isIronHoe(itemStack) ||
                isGoldenHoe(itemStack) || isDiamondHoe(itemStack) ||
                isNetheriteHoe(itemStack);
    }
    public boolean isWoodenHoe(ItemStack itemStack) {
        var material = get("wooden_hoe");
        if (material != null) {
            return itemStack.getType().equals(material);
        } else return false;
    }
    public boolean isStoneHoe(ItemStack itemStack) {
        var material = get("stone_hoe");
        if (material != null) {
            return itemStack.getType().equals(material);
        } else return false;
    }
    public boolean isCopperHoe(ItemStack itemStack) {
        var material = get("copper_hoe");
        if (material != null) {
            return itemStack.getType().equals(material);
        } else return false;
    }
    public boolean isIronHoe(ItemStack itemStack) {
        var material = get("iron_hoe");
        if (material != null) {
            return itemStack.getType().equals(material);
        } else return false;
    }
    public boolean isGoldenHoe(ItemStack itemStack) {
        var material = get("golden_hoe");
        if (material != null) {
            return itemStack.getType().equals(material);
        } else return false;
    }
    public boolean isDiamondHoe(ItemStack itemStack) {
        var material = get("diamond_hoe");
        if (material != null) {
            return itemStack.getType().equals(material);
        } else return false;
    }
    public boolean isNetheriteHoe(ItemStack itemStack) {
        var material = get("netherite_hoe");
        if (material != null) {
            return itemStack.getType().equals(material);
        } else return false;
    }
    public void addDamage(ItemStack itemStack, int damage) {
        var itemMeta = itemStack.getItemMeta();
        if (itemMeta == null)return;
        var unbreaking = itemStack.getEnchantmentLevel(getEnchantment("unbreaking"));
        var calculated = unbreaking + 1;
        if (!getRandomHandler().isTrue(1.0 / calculated))return;
        var toolHealthDamage = (Damageable) itemMeta;
        var result = toolHealthDamage.getDamage() + damage;
        toolHealthDamage.setDamage(result);
        itemStack.setItemMeta(toolHealthDamage);
    }
    public boolean isDestroyed(ItemStack heldItem) {
        if (isWoodenHoe(heldItem)) {
            var toolHealthAfter = (Damageable) heldItem.getItemMeta();
            if (toolHealthAfter.getDamage() >= 59) {
                heldItem.setAmount(0);
                return true;
            } else return false;
        } else if (isStoneHoe(heldItem)) {
            var toolHealthAfter = (Damageable) heldItem.getItemMeta();
            if (toolHealthAfter.getDamage() >= 131) {
                heldItem.setAmount(0);
                return true;
            } else return false;
        } else if (isCopperHoe(heldItem)) {
            var toolHealthAfter = (Damageable) heldItem.getItemMeta();
            if (toolHealthAfter.getDamage() >= 190) {
                heldItem.setAmount(0);
                return true;
            } else return false;
        } else if (isIronHoe(heldItem)) {
            var toolHealthAfter = (Damageable) heldItem.getItemMeta();
            if (toolHealthAfter.getDamage() >= 250) {
                heldItem.setAmount(0);
                return true;
            } else return false;
        } else if (isGoldenHoe(heldItem)) {
            var toolHealthAfter = (Damageable) heldItem.getItemMeta();
            if (toolHealthAfter.getDamage() >= 32) {
                heldItem.setAmount(0);
                return true;
            } else return false;
        } else if (isDiamondHoe(heldItem)) {
            var toolHealthAfter = (Damageable) heldItem.getItemMeta();
            if (toolHealthAfter.getDamage() >= 1561) {
                heldItem.setAmount(0);
                return true;
            } else return false;
        } else if (isNetheriteHoe(heldItem)) {
            var toolHealthAfter = (Damageable) heldItem.getItemMeta();
            if (toolHealthAfter.getDamage() >= 2031) {
                heldItem.setAmount(0);
                return true;
            } else return false;
        } else return false;
    }
    public void breakItem(Location location, ItemStack itemStack) {
        itemStack.setAmount(itemStack.getAmount() - 1);
        var volume = getRandomHandler().nextDouble(0.75, 1.0);
        var pitch = getRandomHandler().nextDouble(0.75, 1.0);
        getWorldHandler().playSound(location, "entity_item_break", volume, pitch);
    }
}