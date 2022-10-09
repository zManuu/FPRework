package de.manu.fprework.commands;

import de.manu.fprework.handler.AccountHandler;
import de.manu.fprework.handler.DatabaseHandler;
import de.manu.fprework.models.Account;
import de.manu.fprework.utils.Constants;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AccountCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            var player = (Player) sender;
            var acc = AccountHandler.getAccount(player);
            switch (args.length) {
                case 1:
                    switch (args[0]) {
                        case "info":
                            info(player, acc);
                            break;
                        default:
                            syntax(player);
                            break;
                    }
                    break;
                case 2:
                    switch (args[0]) {
                        case "setName":
                            setName(player, acc, args[1]);
                            break;
                        default:
                            syntax(player);
                            break;
                    }
                    break;
                default:
                    syntax(player);
                    break;
            }
        }

        return false;
    }

    private void syntax(Player player) {
        player.sendMessage(
                Constants.TLINE +
                    "\nAccount - Syntax:" +
                    "\n/account info" +
                    "\n/account setName <name>" +
                    Constants.BLINE
        );
    }

    private void info(Player player, Account acc) {
        player.sendMessage(
                Constants.TLINE +
                        "\nAccount info für " + acc.name + ":" +
                        "\nID: " + acc.id +
                        "\nName: " + acc.name +
                        "\nUUID: " + acc.uuid +
                        Constants.BLINE
        );
    }

    private void setName(Player player, Account acc, String value) {
        if (value == null || value.length() < 5 || value.length() > 50) {
            player.sendMessage("Der Name muss zwischen 5 und 50 Zeichen lang sein!");
            return;
        }
        acc.setName(value);
        DatabaseHandler.AccountTable.save(acc);
    }

}
