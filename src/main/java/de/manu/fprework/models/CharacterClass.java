package de.manu.fprework.models;

public enum CharacterClass {

    CLASS_WIZARD,
    CLASS_ARCHER,
    CLASS_WARRIOR;

    public static CharacterClass get(int id) {
        switch (id) {
            case 1:
                return CLASS_WIZARD;
            case 2:
                return CLASS_ARCHER;
            case 3:
                return CLASS_WARRIOR;
        }
        return null;
    }

    public int getId() {
        switch (this) {
            case CLASS_WIZARD:
                return 1;
            case CLASS_ARCHER:
                return 2;
            case CLASS_WARRIOR:
                return 3;
        }
        return -1;
    }

}
