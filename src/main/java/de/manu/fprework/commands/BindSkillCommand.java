package de.manu.fprework.commands;

import de.manu.fprework.handler.CharacterHandler;
import de.manu.fprework.handler.DatabaseHandler;
import de.manu.fprework.handler.SkillsHandler;
import de.manu.fprework.models.database.CharacterSkillBind;
import de.manu.fprework.utils.Constants;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BindSkillCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return true;
        if (args.length != 2) return true;

        SkillsHandler.bindSkill(player, args[0], args[1]);

        return false;
    }
}
