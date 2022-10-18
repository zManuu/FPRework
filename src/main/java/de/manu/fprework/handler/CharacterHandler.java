package de.manu.fprework.handler;

import de.manu.fprework.models.Character;
import de.manu.fprework.models.CharacterItem;
import de.manu.fprework.models.CharacterLockedItem;
import de.manu.fprework.utils.Constants;
import de.manu.fprework.utils.ItemBuilder;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CharacterHandler {

    public static Character getSelectedCharacter(int accountId) {
        var selectedCharacterId = AccountHandler.getAccount(accountId).selectedChar;
        return DatabaseHandler.Characters.stream()
                .filter(e -> e.id == selectedCharacterId)
                .findAny()
                .orElse(null);
    }

    public static int getCharId(Player player) {
        return AccountHandler.getAccount(player).selectedChar;
    }

    public static void openSelectCharTypeMenu(Player player) {
        player.setInvisible(true);
        player.setGameMode(GameMode.ADVENTURE);
        player.teleport(Constants.DARK);
        InventoryHandler.buildInventory(player, "§lWähle einen Charakter-Typen", InventoryType.HOPPER, () -> openSelectCharTypeMenu(player),
                new InventoryHandler.CustomInventoryItem(1, new ItemBuilder(Material.STICK, "§6§lMagier").build(), () -> createChar(player, 1)),
                new InventoryHandler.CustomInventoryItem(2, new ItemBuilder(Material.BOW, "§6§lBogenschütze").build(), () -> createChar(player, 2)),
                new InventoryHandler.CustomInventoryItem(3, new ItemBuilder(Material.IRON_SWORD, "§6§lKrieger").build(), () -> createChar(player, 3)));
    }

    private static void createChar(Player player, int characterClass) {
        var account = AccountHandler.getAccount(player);
        var character = new Character(account.id, characterClass, 0, 0);
        DatabaseHandler.Characters.add(character);
        DatabaseHandler.CharacterTable.insert(character);
        account.setSelectedChar(character.id);
        DatabaseHandler.AccountTable.save(account);
        player.sendMessage(Constants.M_SUCCESS + "Dein Charakter ist erstellt worden!");
        player.setInvisible(false);
        player.setGameMode(GameMode.SURVIVAL);
        player.teleport(Constants.FIRST_SPAWN);
        player.getInventory().clear();

        ItemHandler.addItem(player, 1, 1, 7); // QuestBook
        ItemHandler.addItem(player, 2, 1, 8); // Char-Management
    }

}
