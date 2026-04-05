package org.achymake.harvester.handlers;

import org.achymake.harvester.Harvester;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

public class ScheduleHandler {
    private Harvester getInstance() {
        return Harvester.getInstance();
    }
    private BukkitScheduler getBukkitScheduler() {
        return getInstance().getBukkitScheduler();
    }
    public BukkitTask runLater(Runnable runnable, long timer) {
        return getBukkitScheduler().runTaskLater(getInstance(), runnable, timer);
    }
    public void runAsynchronously(Runnable runnable) {
        getBukkitScheduler().runTaskAsynchronously(getInstance(), runnable);
    }
    public void disable() {
        getBukkitScheduler().cancelTasks(getInstance());
    }
}