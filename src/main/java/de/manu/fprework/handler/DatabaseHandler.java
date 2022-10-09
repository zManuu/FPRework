package de.manu.fprework.handler;


import de.manu.fprework.models.Account;
import de.manu.fprework.models.Character;
import de.manu.fprework.utils.javaef.Database;
import de.manu.fprework.utils.javaef.Table;

import java.util.List;

public class DatabaseHandler {

    public static Database Database;

    // Tables
    public static Table<Account> AccountTable;
    public static Table<Character> CharacterTable;

    // Lists
    public static List<Account> Accounts;
    public static List<Character> Characters;

    public static void loadAll() {
        Database = new Database("localhost", "root", "", "fprework", 3306);

        AccountTable = new Table<>(Database, "accounts", Account.class);
        CharacterTable = new Table<>(Database, "characters", Character.class);

        Accounts = AccountTable.getAll();
        Characters = CharacterTable.getAll();
    }

}
