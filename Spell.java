package org.example.entities.characters.spells;

//import java.util.Random;

import org.example.entities.characters.Entity;
import org.example.entities.characters.Visitor;

import java.util.Objects;


//Class that handles the basic implementation of the spells used in-game
public abstract class Spell implements Visitor<Entity> {
    int abilityDamage;
    int manaCost;

    public Spell(int abilityDamage, int manaCost) {
        this.abilityDamage = abilityDamage;
        this.manaCost = manaCost;
    }

    public int getAbilityDamage() {
        return this.abilityDamage;
    }

    public int getManaCost() {
        return this.manaCost;
    }

    @Override
    public String toString() {
        return "Spell : Damage = " + this.abilityDamage + ", Mana Cost = " + this.manaCost;
    }

    public String getType(Spell spell) {
        String type = "";
        if (spell instanceof Fire)
            type = "Fire";
        if (spell instanceof Ice)
            type = "Ice";
        if (spell instanceof Earth)
            type = "Earth";
        return type;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Spell spell = (Spell) obj;
        return manaCost == spell.manaCost &&
                abilityDamage == spell.abilityDamage;
    }

    @Override
    public int hashCode() {
        return Objects.hash(manaCost, abilityDamage);
    }

}
