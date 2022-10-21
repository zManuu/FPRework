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

        var charId = CharacterHandler.getCharId(player);
        var bind = args[0];
        var skillName = args[1];
        var skill = SkillsHandler.getSkill(skillName);

        if (skill == null) {
            player.sendMessage(Constants.M_ERROR + "Skill wurde nicht gefunden!");
            return true;
        }

        if (!Constants.SKILL_BINDS.contains(bind)) {
            player.sendMessage(Constants.M_ERROR + "Ungültiger Bind!");
            return true;
        }

        var existingBind = SkillsHandler.getCharacterSkillBind(charId, bind);
        if (existingBind != null) {
            DatabaseHandler.CharacterSkillBinds.remove(existingBind);
            DatabaseHandler.table(CharacterSkillBind.class).remove(existingBind);
        }

        var skillBind = new CharacterSkillBind(charId, skill.id, bind);
        DatabaseHandler.CharacterSkillBinds.add(skillBind);
        DatabaseHandler.table(CharacterSkillBind.class).insert(skillBind);

        player.sendMessage(Constants.M_SUCCESS + "Der Skill " + skillName + " ist jetzt auf die Tastenkombination " + bind + " gespeichert!");

        return false;
    }
}
