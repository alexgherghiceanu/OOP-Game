package org.example.entities.characters;

public class CharacterFactory {
    public static Character createCharacter(String profession, String cname, int experience, int level) {
        switch (profession) {
            case "Mage":
                return new Mage(cname,experience,level);
            case "Warrior":
                return new Warrior(cname,experience,level);
            case "Rogue":
                return new Rogue(cname,experience,level);
            default:
                return null;
        }
    }
}
