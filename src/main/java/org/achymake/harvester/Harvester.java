package org.achymake.harvester;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.StateFlag;
import org.achymake.harvester.commands.*;
import org.achymake.harvester.data.*;
import org.achymake.harvester.handlers.*;
import org.achymake.harvester.listeners.*;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.io.File;

public final class Harvester extends JavaPlugin {
    private static Harvester instance;
    private Message message;
    private BlockHandler blockHandler;
    private GameModeHandler gameModeHandler;
    private MaterialHandler materialHandler;
    private RandomHandler randomHandler;
    private ScheduleHandler scheduleHandler;
    private WorldHandler worldHandler;
    private UpdateChecker updateChecker;
    private BukkitScheduler bukkitScheduler;
    private PluginManager pluginManager;
    private StateFlag REPLANT_FLAG;
    @Override
    public void onLoad() {
        REPLANT_FLAG = new StateFlag("replant", true);
        getWorldGuard().getFlagRegistry().register(getFlag());
    }
    @Override
    public void onEnable() {
        instance = this;
        message = new Message();
        blockHandler = new BlockHandler();
        gameModeHandler = new GameModeHandler();
        materialHandler = new MaterialHandler();
        randomHandler = new RandomHandler();
        scheduleHandler = new ScheduleHandler();
        worldHandler = new WorldHandler();
        updateChecker = new UpdateChecker();
        bukkitScheduler = getServer().getScheduler();
        pluginManager = getServer().getPluginManager();
        commands();
        events();
        reload();
        sendInfo("Enabled for " + getMinecraftProvider() + " " + getMinecraftVersion());
        getUpdateChecker().getUpdate();
    }
    @Override
    public void onDisable() {
        getScheduleHandler().disable();
        sendInfo("Disabled for " + getMinecraftProvider() + " " + getMinecraftVersion());
    }
    private void commands() {
        new ReplantCommand();
    }
    private void events() {
        new PlayerInteract();
        new PlayerJoin();
        new PlayerReplant();
    }
    public void reload() {
        if (!new File(getDataFolder(), "config.yml").exists()) {
            getConfig().options().copyDefaults(true);
            saveConfig();
        } else reloadConfig();
    }
    public StateFlag getFlag() {
        return REPLANT_FLAG;
    }
    public PluginManager getPluginManager() {
        return pluginManager;
    }
    public BukkitScheduler getBukkitScheduler() {
        return bukkitScheduler;
    }
    public UpdateChecker getUpdateChecker() {
        return updateChecker;
    }
    public WorldHandler getWorldHandler() {
        return worldHandler;
    }
    public ScheduleHandler getScheduleHandler() {
        return scheduleHandler;
    }
    public RandomHandler getRandomHandler() {
        return randomHandler;
    }
    public MaterialHandler getMaterialHandler() {
        return materialHandler;
    }
    public GameModeHandler getGameModeHandler() {
        return gameModeHandler;
    }
    public BlockHandler getBlockHandler() {
        return blockHandler;
    }
    public Message getMessage() {
        return message;
    }
    public static Harvester getInstance() {
        return instance;
    }
    public void sendInfo(String message) {
        getLogger().info(message);
    }
    public void sendWarning(String message) {
        getLogger().warning(message);
    }
    public String name() {
        return getDescription().getName();
    }
    public String version() {
        return getDescription().getVersion();
    }
    public String getMinecraftVersion() {
        return getServer().getBukkitVersion();
    }
    public String getMinecraftProvider() {
        return getServer().getName();
    }
    public WorldGuard getWorldGuard() {
        return WorldGuard.getInstance();
    }
    public boolean isBukkit() {
        return getMinecraftProvider().equals("Spigot") ||
                getMinecraftProvider().equals("Bukkit") ||
                getMinecraftProvider().equals("CraftBukkit");
    }
}