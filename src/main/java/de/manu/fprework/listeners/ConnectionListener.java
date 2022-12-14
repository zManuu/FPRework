package de.manu.fprework.listeners;

import de.manu.fprework.handler.AccountHandler;
import de.manu.fprework.handler.CharacterHandler;
import de.manu.fprework.utils.Constants;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

public class ConnectionListener implements Listener {

    @EventHandler
    public void playerJoined(@NotNull PlayerJoinEvent event) {
        var player = event.getPlayer();
        var account = AccountHandler.getAccount(player) != null ? AccountHandler.getAccount(player) : AccountHandler.createAccount(player);
        var character = CharacterHandler.getSelectedCharacter(account.id);

        if (character == null) {
            CharacterHandler.openSelectCharTypeMenu(player);
            event.setJoinMessage(null);
            return;
        }

        player.setGameMode(GameMode.SURVIVAL);
        player.sendMessage(Constants.M_INFO + "Du wurdest erfolgreich eingeloggt!");
        event.setJoinMessage("§8[§a+§8] §7" + account.name);
    }

}
