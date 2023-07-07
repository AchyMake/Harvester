package net.achymake.harvester;

import net.achymake.harvester.commands.HarvesterCommand;
import net.achymake.harvester.files.*;
import net.achymake.harvester.listeners.*;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Consumer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Harvester extends JavaPlugin {
    private static Harvester instance;
    public static Harvester getInstance() {
        return instance;
    }
    private static File folder;
    public static File getFolder() {
        return folder;
    }
    private static FileConfiguration configuration;
    public static FileConfiguration getConfiguration() {
        return configuration;
    }
    private static Logger logger;
    public static void sendLog(Level level, String message) {
        logger.log(level, message);
    }
    private static BlocksConfig blocksConfig;
    public static BlocksConfig getBlocksConfig() {
        return blocksConfig;
    }
    private static CropsConfig cropsConfig;
    public static CropsConfig getCropsConfig() {
        return cropsConfig;
    }
    private static Hoe hoe;
    public static Hoe getHoe() {
        return hoe;
    }
    @Override
    public void onEnable() {
        instance = this;
        folder = getDataFolder();
        configuration = getConfig();
        logger = getLogger();
        blocksConfig = new BlocksConfig();
        cropsConfig = new CropsConfig();
        hoe = new Hoe();
        commands();
        events();
        reload();
        sendLog(Level.INFO, "Enabled " + this.getName() + " " + this.getDescription().getVersion());
        getUpdate();
    }
    @Override
    public void onDisable() {
        sendLog(Level.INFO, "Disabled " + this.getName() + " " + this.getDescription().getVersion());
    }
    private void commands() {
        getCommand("harvester").setExecutor(new HarvesterCommand());
    }
    private void events() {
        new DiamondHoeBlocks(this);
        new DiamondHoeCrops(this);
        new GoldenHoeBlocks(this);
        new GoldenHoeCrops(this);
        new IronHoeBlocks(this);
        new IronHoeCrops(this);
        new NetheriteHoeBlocks(this);
        new NetheriteHoeCrops(this);
        new NotifyUpdate(this);
        new StoneHoeBlocks(this);
        new StoneHoeCrops(this);
        new WoodenHoeBlocks(this);
        new WoodenHoeCrops(this);
    }
    public static void reload() {
        File file = new File(getFolder(), "config.yml");
        if (file.exists()) {
            try {
                getConfiguration().load(file);
            } catch (IOException | InvalidConfigurationException e) {
                sendLog(Level.WARNING, e.getMessage());
            }
        } else {
            getConfiguration().options().copyDefaults(true);
            try {
                getConfiguration().save(file);
            } catch (IOException e) {
                sendLog(Level.WARNING, e.getMessage());
            }
        }
        getBlocksConfig().reload();
        getCropsConfig().reload();
    }
    public static void getUpdate(Player player) {
        if (notifyUpdate()) {
            getLatest((latest) -> {
                if (!getInstance().getDescription().getVersion().equals(latest)) {
                    send(player,"&6" + getInstance().getName() + " Update:&f " + latest);
                    send(player,"&6Current Version: &f" + getInstance().getDescription().getVersion());
                }
            });
        }
    }
    public void getUpdate() {
        if (notifyUpdate()) {
            getServer().getScheduler().runTaskAsynchronously(this, new Runnable() {
                @Override
                public void run() {
                    getLatest((latest) -> {
                        sendLog(Level.INFO, "Checking latest release");
                        if (getDescription().getVersion().equals(latest)) {
                            sendLog(Level.INFO, "You are using the latest version");
                        } else {
                            sendLog(Level.INFO, "New Update: " + latest);
                            sendLog(Level.INFO, "Current Version: " + getDescription().getVersion());
                        }
                    });
                }
            });
        }
    }
    public static void getLatest(Consumer<String> consumer) {
        try {
            InputStream inputStream = (new URL("https://api.spigotmc.org/legacy/update.php?resource=" + 111033)).openStream();
            Scanner scanner = new Scanner(inputStream);
            if (scanner.hasNext()) {
                consumer.accept(scanner.next());
                scanner.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (IOException e) {
            sendLog(Level.WARNING, e.getMessage());
        }
    }
    public static boolean notifyUpdate() {
        return getConfiguration().getBoolean("notify-update.enable");
    }
    public static void send(ConsoleCommandSender commandSender, String message) {
        commandSender.sendMessage(message);
    }
    public static void send(Player player, String message) {
        player.sendMessage(addColor(message));
    }
    public static void sendActionBar(Player player, String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(addColor(message)));
    }
    public static String addColor(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}