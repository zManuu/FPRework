package de.manu.fprework.listeners;

import de.manu.fprework.handler.AccountHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.TabCompleteEvent;

import java.util.LinkedList;

public class ChatListener implements Listener {

    @EventHandler
    public static void onTabComplete(TabCompleteEvent event) {
        if (!(event.getSender() instanceof Player)) return;

        var player = (Player) event.getSender();

        var buffer = event.getBuffer();
        var args = buffer.split(" ");

        var res = new LinkedList<String>();

        switch (args[0]) {
            case "/account":
                if (args.length == 1) {
                    res.add("setName");
                    res.add("info");
                } else if (args[1].equalsIgnoreCase("setName") && args.length < 3) {
                    res.add("<name>");
                }
                break;
        }

        event.setCompletions(res);
    }

}
