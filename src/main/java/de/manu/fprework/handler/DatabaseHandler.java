package de.manu.fprework.handler;


import de.manu.fprework.FPRework;
import de.manu.fprework.models.Account;
import de.manu.fprework.models.Character;
import de.manu.fprework.models.CharacterLockedItem;
import de.manu.fprework.utils.javaef.Database;
import de.manu.fprework.utils.javaef.Table;

import java.util.List;

public class DatabaseHandler {

    public static Database Database;

    // Tables
    public static Table<Account> AccountTable;
    public static Table<Character> CharacterTable;
    public static Table<CharacterLockedItem> CharacterLockedItemTable;

    // Lists
    public static List<Account> Accounts;
    public static List<Character> Characters;
    public static List<CharacterLockedItem> CharacterLockedItems;

    public static void loadAll() {
        Database = new Database("localhost", "root", "", "fprework", 3306);

        AccountTable = new Table<>(Database, "accounts", Account.class);
        CharacterTable = new Table<>(Database, "characters", Character.class);
        CharacterLockedItemTable = new Table<>(Database, "characters_lockeditems", CharacterLockedItem.class);

        Accounts = AccountTable.getAll();
        Characters = CharacterTable.getAll();
        CharacterLockedItems = CharacterLockedItemTable.getAll();

        FPRework.print("§a[FP] Database-Load | Accounts: " + Accounts.size());
        FPRework.print("§a[FP] Database-Load | Characters: " + Characters.size());
        FPRework.print("§a[FP] Database-Load | Character-Lockeditems: " + CharacterLockedItems.size());
    }

}
