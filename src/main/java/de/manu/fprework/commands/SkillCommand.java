package de.manu.fprework.commands;

import de.manu.fprework.handler.SkillsHandler;
import de.manu.fprework.utils.Constants;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SkillCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return true;
        if (args.length != 1) return true;

        var skillName = args[0];
        var skill = SkillsHandler.getSkill(skillName);

        if (skill == null) {
            player.sendMessage(Constants.M_ERROR + "Skill wurde nicht gefunden!");
            return true;
        }

        SkillsHandler.startSkill(player, skill);

        return false;
    }
}
