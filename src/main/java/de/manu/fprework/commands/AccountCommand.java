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
                Constants.LINE_T +
                    "\n§4Account - Syntax:" +
                    "\n§c/account info" +
                    "\n§c/account setName <name>" +
                    Constants.LINE_B
        );
    }

    private void info(Player player, Account acc) {
        player.sendMessage(
                Constants.LINE_T +
                        "\n§2Account info für " + acc.name + ":" +
                        "\n§aID: " + acc.id +
                        "\n§aName: " + acc.name +
                        "\n§aUUID: " + acc.uuid +
                        Constants.LINE_B
        );
    }

    private void setName(Player player, Account acc, String value) {
        if (value == null || value.length() < 5 || value.length() > 50) {
            player.sendMessage(Constants.M_ERROR + "Der Name muss zwischen 5 und 50 Zeichen lang sein!");
            return;
        }
        acc.setName(value);
        DatabaseHandler.table(Account.class).save(acc);
        player.sendMessage(Constants.M_SUCCESS + "Dein Accountname ist jetzt " + value + ".");
    }

}
