package net.achymake.harvester.files;

import net.achymake.harvester.Harvester;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;

public class CropsConfig {
    private File getFolder() {
        return Harvester.getFolder();
    }
    private final File file = new File (getFolder(), "settings/crops.yml");
    private FileConfiguration config = YamlConfiguration.loadConfiguration(file);
    public boolean isEnable(Block block) {
        return config.getBoolean(block.getType() + ".enable");
    }
    public boolean isRightAge(Block block) {
        return ((Ageable) block.getBlockData()).getAge() == config.getInt(block.getType() + ".max-age");
    }
    public void playHarvestSound(Player player, Block block) {
        player.playSound(block.getLocation().add(0.5, 0.3, 0.5), Sound.ITEM_SHOVEL_FLATTEN, 1.0F, 1.0F);
    }
    public void resetAge(Block block) {
        BlockData blockData = block.getBlockData();
        ((Ageable) blockData).setAge(0);
        block.setBlockData(blockData);
    }
    public void dropExperience(Player player, Block block) {
        if (config.getBoolean(block.getType() + ".experience.enable")) {
            if (new Random().nextInt(100) < config.getInt(block.getType() + ".experience.chance")) {
                ExperienceOrb experience = (ExperienceOrb) player.getWorld().spawnEntity(block.getLocation().add(0.5, 0.3, 0.5), EntityType.EXPERIENCE_ORB);
                experience.setExperience(config.getInt(block.getType() + ".experience.amount"));
                if (config.getBoolean(block.getType() + ".sound.enable")){
                    player.playSound(block.getLocation().add(0.5, 0.3, 0.5), Sound.valueOf(config.getString(block.getType() + ".sound.type")), Float.valueOf(config.getString(block.getType() + ".sound.volume")), Float.valueOf(config.getString(block.getType() + ".sound.pitch")));
                }
                if (config.getBoolean(block.getType() + ".particle.enable")) {
                    player.spawnParticle(Particle.valueOf(config.getString(block.getType() + ".particle.type")), block.getLocation().add(0.5, 0.3, 0.5), config.getInt(block.getType() + ".particle.count"), config.getDouble(block.getType() + ".particle.offsetX"), config.getDouble(block.getType() + ".particle.offsetY"), config.getDouble(block.getType() + ".particle.offsetZ"), 0.0);
                }
            }
        }
    }
    public void dropItems(Player player, Block block) {
        if (player.getInventory().getItemInMainHand().containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) {
            int amount = player.getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);
            for (ItemStack drops : block.getDrops()) {
                drops.setAmount(drops.getAmount() + amount);
                player.getWorld().dropItem(block.getLocation().add(0.5,0.3,0.5), drops);
            }
        } else {
            for (ItemStack drops : block.getDrops()) {
                player.getWorld().dropItem(block.getLocation().add(0.5,0.3,0.5), drops);
            }
        }
    }
    public void reload() {
        if (file.exists()) {
            config = YamlConfiguration.loadConfiguration(file);
        } else {
            config.addDefault("CARROTS.enable", true);
            config.addDefault("CARROTS.max-age", 7);
            config.addDefault("CARROTS.experience.enable", true);
            config.addDefault("CARROTS.experience.chance", 30);
            config.addDefault("CARROTS.experience.amount", 1);
            config.addDefault("CARROTS.particle.enable", true);
            config.addDefault("CARROTS.particle.type", "TOTEM");
            config.addDefault("CARROTS.particle.offsetX", 0.3);
            config.addDefault("CARROTS.particle.offsetY", 0.3);
            config.addDefault("CARROTS.particle.offsetZ", 0.3);
            config.addDefault("CARROTS.particle.count", 25);
            config.addDefault("CARROTS.sound.enable", true);
            config.addDefault("CARROTS.sound.type", "BLOCK_AMETHYST_BLOCK_BREAK");
            config.addDefault("CARROTS.sound.volume", "0.75F");
            config.addDefault("CARROTS.sound.pitch", "1.00F");
            config.addDefault("POTATOES.enable", true);
            config.addDefault("POTATOES.max-age", 7);
            config.addDefault("POTATOES.experience.enable", true);
            config.addDefault("POTATOES.experience.chance", 30);
            config.addDefault("POTATOES.experience.amount", 1);
            config.addDefault("POTATOES.particle.enable", true);
            config.addDefault("POTATOES.particle.type", "TOTEM");
            config.addDefault("POTATOES.particle.offsetX", 0.3);
            config.addDefault("POTATOES.particle.offsetY", 0.3);
            config.addDefault("POTATOES.particle.offsetZ", 0.3);
            config.addDefault("POTATOES.particle.count", 25);
            config.addDefault("POTATOES.sound.enable", true);
            config.addDefault("POTATOES.sound.type", "BLOCK_AMETHYST_BLOCK_BREAK");
            config.addDefault("POTATOES.sound.volume", "0.75F");
            config.addDefault("POTATOES.sound.pitch", "1.00F");
            config.addDefault("WHEAT.enable", true);
            config.addDefault("WHEAT.max-age", 7);
            config.addDefault("WHEAT.experience.enable", true);
            config.addDefault("WHEAT.experience.chance", 30);
            config.addDefault("WHEAT.experience.amount", 1);
            config.addDefault("WHEAT.particle.enable", true);
            config.addDefault("WHEAT.particle.type", "TOTEM");
            config.addDefault("WHEAT.particle.offsetX", 0.3);
            config.addDefault("WHEAT.particle.offsetY", 0.3);
            config.addDefault("WHEAT.particle.offsetZ", 0.3);
            config.addDefault("WHEAT.particle.count", 25);
            config.addDefault("WHEAT.sound.enable", true);
            config.addDefault("WHEAT.sound.type", "BLOCK_AMETHYST_BLOCK_BREAK");
            config.addDefault("WHEAT.sound.volume", "0.75F");
            config.addDefault("WHEAT.sound.pitch", "1.00F");
            config.addDefault("BEETROOTS.enable", true);
            config.addDefault("BEETROOTS.max-age", 3);
            config.addDefault("BEETROOTS.experience.enable", true);
            config.addDefault("BEETROOTS.experience.chance", 30);
            config.addDefault("BEETROOTS.experience.amount", 1);
            config.addDefault("BEETROOTS.particle.enable", true);
            config.addDefault("BEETROOTS.particle.type", "TOTEM");
            config.addDefault("BEETROOTS.particle.offsetX", 0.3);
            config.addDefault("BEETROOTS.particle.offsetY", 0.3);
            config.addDefault("BEETROOTS.particle.offsetZ", 0.3);
            config.addDefault("BEETROOTS.particle.count", 25);
            config.addDefault("BEETROOTS.sound.enable", true);
            config.addDefault("BEETROOTS.sound.type", "BLOCK_AMETHYST_BLOCK_BREAK");
            config.addDefault("BEETROOTS.sound.volume", "0.75F");
            config.addDefault("BEETROOTS.sound.pitch", "1.00F");
            config.addDefault("COCOA.enable", true);
            config.addDefault("COCOA.max-age", 3);
            config.addDefault("COCOA.experience.enable", true);
            config.addDefault("COCOA.experience.chance", 30);
            config.addDefault("COCOA.experience.amount", 1);
            config.addDefault("COCOA.particle.enable", true);
            config.addDefault("COCOA.particle.type", "TOTEM");
            config.addDefault("COCOA.particle.offsetX", 0.3);
            config.addDefault("COCOA.particle.offsetY", 0.3);
            config.addDefault("COCOA.particle.offsetZ", 0.3);
            config.addDefault("COCOA.particle.count", 25);
            config.addDefault("COCOA.sound.enable", true);
            config.addDefault("COCOA.sound.type", "BLOCK_AMETHYST_BLOCK_BREAK");
            config.addDefault("COCOA.sound.volume", "0.75F");
            config.addDefault("COCOA.sound.pitch", "1.00F");
            config.addDefault("NETHER_WART.enable", true);
            config.addDefault("NETHER_WART.max-age", 3);
            config.addDefault("NETHER_WART.experience.enable", true);
            config.addDefault("NETHER_WART.experience.chance", 30);
            config.addDefault("NETHER_WART.experience.amount", 1);
            config.addDefault("NETHER_WART.particle.enable", true);
            config.addDefault("NETHER_WART.particle.type", "TOTEM");
            config.addDefault("NETHER_WART.particle.offsetX", 0.3);
            config.addDefault("NETHER_WART.particle.offsetY", 0.3);
            config.addDefault("NETHER_WART.particle.offsetZ", 0.3);
            config.addDefault("NETHER_WART.particle.count", 25);
            config.addDefault("NETHER_WART.sound.enable", true);
            config.addDefault("NETHER_WART.sound.type", "BLOCK_AMETHYST_BLOCK_BREAK");
            config.addDefault("NETHER_WART.sound.volume", "0.75F");
            config.addDefault("NETHER_WART.sound.pitch", "1.00F");
            config.options().copyDefaults(true);
            try {
                config.save(file);
            } catch (IOException e) {
                Harvester.sendLog(Level.WARNING, e.getMessage());
            }
        }
    }
}
