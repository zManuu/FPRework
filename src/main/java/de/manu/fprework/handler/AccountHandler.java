package de.manu.fprework.handler;

import de.manu.fprework.models.Account;
import org.bukkit.entity.Player;

public class AccountHandler {

    public static Account getAccount(Player player) {
        return DatabaseHandler.Accounts.stream()
                .filter(e -> e.uuid.equals(player.getUniqueId().toString()))
                .findAny()
                .orElse(null);
    }

    public static void createAccount(Player player) {
        var acc = new Account(player.getName(), player.getUniqueId().toString());
        DatabaseHandler.Accounts.add(acc);
        DatabaseHandler.AccountTable.insert(acc);
        player.sendMessage("Ein Account wurde für dich erstellt!");
    }

}
