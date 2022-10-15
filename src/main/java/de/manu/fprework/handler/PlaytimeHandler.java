package de.manu.fprework.handler;

import de.manu.fprework.FPRework;
import de.manu.fprework.utils.Constants;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class PlaytimeHandler {

    private static long lastTime = Long.MIN_VALUE;

    public static void init() {
        new BukkitRunnable() {
            @Override
            public void run() {
                var worldTime = Constants.WORLD.getFullTime();
                var systemTime = System.currentTimeMillis();
                if ((lastTime == Long.MIN_VALUE && worldTime > 17900 && worldTime < 18100 || (systemTime - lastTime >= 86400000))) {
                    lastTime = systemTime;
                    onMidnight();
                }
            }
        }.runTaskTimerAsynchronously(FPRework.INSTANCE, 0, 100);
    }

    private static void onMidnight() {
        Bukkit.broadcastMessage("MIDNIGHT!");
    }

}
