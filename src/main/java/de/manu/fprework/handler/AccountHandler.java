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

    public static Account getAccount(int id) {
        return DatabaseHandler.Accounts.stream()
                .filter(e -> e.id == id)
                .findAny()
                .orElse(null);
    }

    public static Account createAccount(Player player) {
        var acc = new Account(player.getName(), player.getUniqueId().toString());
        DatabaseHandler.Accounts.add(acc);
        DatabaseHandler.table(Account.class).insert(acc);
        player.sendMessage("§7§lWillkommen auf §5§lFantasy§6§lPixel§7§l!");
        return acc;
    }

}
