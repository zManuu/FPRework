package de.manu.fprework.listeners;

import de.manu.fprework.handler.AccountHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

public class ConnectionListener implements Listener {

    @EventHandler
    public void playerJoined(@NotNull PlayerJoinEvent event) {
        var player = event.getPlayer();
        var account = AccountHandler.getAccount(player);
        if (account == null)
            AccountHandler.createAccount(player);
        player.sendMessage("Du wurdest erfolgreich eingeloggt!");
    }

}
