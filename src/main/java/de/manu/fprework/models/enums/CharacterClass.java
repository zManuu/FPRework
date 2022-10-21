package de.manu.fprework.models.enums;

public enum CharacterClass {

    CLASS_WIZARD,
    CLASS_ARCHER,
    CLASS_WARRIOR;

    public static CharacterClass get(int id) {
        return switch (id) {
            case 1 -> CLASS_WIZARD;
            case 2 -> CLASS_ARCHER;
            case 3 -> CLASS_WARRIOR;
            default -> null;
        };
    }

    public int getId() {
        return switch (this) {
            case CLASS_WIZARD -> 1;
            case CLASS_ARCHER -> 2;
            case CLASS_WARRIOR -> 3;
        };
    }

}
