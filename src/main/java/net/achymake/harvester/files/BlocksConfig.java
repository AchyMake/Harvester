package net.achymake.harvester.files;

import net.achymake.harvester.Harvester;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
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

public class BlocksConfig {
    private File getFolder() {
        return Harvester.getFolder();
    }
    private final File file = new File (getFolder(), "settings/blocks.yml");
    private FileConfiguration config = YamlConfiguration.loadConfiguration(file);
    public boolean isEnable(Block block) {
        return config.getBoolean(block.getType() + ".enable");
    }
    public void playHarvestSound(Player player, Block block) {
        player.playSound(block.getLocation().add(0.5, 0.3, 0.5), Sound.ITEM_SHOVEL_FLATTEN, 1.0F, 1.0F);
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
        block.setType(Material.AIR);
    }
    public void reload() {
        if (file.exists()) {
            config = YamlConfiguration.loadConfiguration(file);
        } else {
            config.addDefault("MELON.enable", true);
            config.addDefault("MELON.experience.enable", true);
            config.addDefault("MELON.experience.chance", 30);
            config.addDefault("MELON.experience.amount", 1);
            config.addDefault("MELON.particle.enable", true);
            config.addDefault("MELON.particle.type", "TOTEM");
            config.addDefault("MELON.particle.offsetX", 0.3);
            config.addDefault("MELON.particle.offsetY", 0.3);
            config.addDefault("MELON.particle.offsetZ", 0.3);
            config.addDefault("MELON.particle.count", 25);
            config.addDefault("MELON.sound.enable", true);
            config.addDefault("MELON.sound.type", "BLOCK_AMETHYST_BLOCK_BREAK");
            config.addDefault("MELON.sound.volume", "0.75F");
            config.addDefault("MELON.sound.pitch", "1.00F");
            config.addDefault("PUMPKIN.enable", true);
            config.addDefault("PUMPKIN.experience.enable", true);
            config.addDefault("PUMPKIN.experience.chance", 30);
            config.addDefault("PUMPKIN.experience.amount", 1);
            config.addDefault("PUMPKIN.particle.enable", true);
            config.addDefault("PUMPKIN.particle.type", "TOTEM");
            config.addDefault("PUMPKIN.particle.offsetX", 0.3);
            config.addDefault("PUMPKIN.particle.offsetY", 0.3);
            config.addDefault("PUMPKIN.particle.offsetZ", 0.3);
            config.addDefault("PUMPKIN.particle.count", 25);
            config.addDefault("PUMPKIN.sound.enable", true);
            config.addDefault("PUMPKIN.sound.type", "BLOCK_AMETHYST_BLOCK_BREAK");
            config.addDefault("PUMPKIN.sound.volume", "0.75F");
            config.addDefault("PUMPKIN.sound.pitch", "1.00F");
            config.addDefault("SUGAR_CANE.enable", true);
            config.addDefault("SUGAR_CANE.experience.enable", true);
            config.addDefault("SUGAR_CANE.experience.chance", 30);
            config.addDefault("SUGAR_CANE.experience.amount", 1);
            config.addDefault("SUGAR_CANE.particle.enable", true);
            config.addDefault("SUGAR_CANE.particle.type", "TOTEM");
            config.addDefault("SUGAR_CANE.particle.offsetX", 0.3);
            config.addDefault("SUGAR_CANE.particle.offsetY", 0.3);
            config.addDefault("SUGAR_CANE.particle.offsetZ", 0.3);
            config.addDefault("SUGAR_CANE.particle.count", 25);
            config.addDefault("SUGAR_CANE.sound.enable", true);
            config.addDefault("SUGAR_CANE.sound.type", "BLOCK_AMETHYST_BLOCK_BREAK");
            config.addDefault("SUGAR_CANE.sound.volume", "0.75F");
            config.addDefault("SUGAR_CANE.sound.pitch", "1.00F");
            config.addDefault("BAMBOO.enable", true);
            config.addDefault("BAMBOO.experience.enable", true);
            config.addDefault("BAMBOO.experience.chance", 30);
            config.addDefault("BAMBOO.experience.amount", 1);
            config.addDefault("BAMBOO.particle.enable", true);
            config.addDefault("BAMBOO.particle.type", "TOTEM");
            config.addDefault("BAMBOO.particle.offsetX", 0.3);
            config.addDefault("BAMBOO.particle.offsetY", 0.3);
            config.addDefault("BAMBOO.particle.offsetZ", 0.3);
            config.addDefault("BAMBOO.particle.count", 25);
            config.addDefault("BAMBOO.sound.enable", true);
            config.addDefault("BAMBOO.sound.type", "BLOCK_AMETHYST_BLOCK_BREAK");
            config.addDefault("BAMBOO.sound.volume", "0.75F");
            config.addDefault("BAMBOO.sound.pitch", "1.00F");
            config.options().copyDefaults(true);
            try {
                config.save(file);
            } catch (IOException e) {
                Harvester.sendLog(Level.WARNING, e.getMessage());
            }
        }
    }
}