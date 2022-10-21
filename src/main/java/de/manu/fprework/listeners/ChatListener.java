package de.manu.fprework.listeners;

import de.manu.fprework.handler.AccountHandler;
import de.manu.fprework.handler.DatabaseHandler;
import de.manu.fprework.utils.Constants;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.TabCompleteEvent;

import java.util.LinkedList;

public class ChatListener implements Listener {

    @EventHandler
    public static void onTabComplete(TabCompleteEvent event) {
        if (!(event.getSender() instanceof Player)) return;

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
            case "/item":
                if (args.length == 1) {
                    for (var itemType : DatabaseHandler.ServerItems) {
                        if (itemType.type != 5)
                            res.add(itemType.name);
                    }
                } else if (args.length == 2) {
                    res.add("<anzahl>");
                }
                break;
            case "/skill":
                if (args.length != 1) break;
                for (var skill : DatabaseHandler.ServerSkills) {
                    res.add(skill.name);
                }
                break;
            case "/bindskill":
                if (args.length == 1) {
                    res.addAll(Constants.SKILL_BINDS);
                } else if (args.length == 2) {
                    for (var skill : DatabaseHandler.ServerSkills) {
                        res.add(skill.name);
                    }
                }
        }

        event.setCompletions(res);
    }

}
