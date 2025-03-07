package org.example.entities.characters.spells;

import org.example.entities.characters.Entity;

//Class that handles Earth Spells
public class Earth extends Spell {
    public Earth(int damage, int mana) {
        super(damage, mana);
    }

    @Override
    public String toString() {
        return " Earth Spell : Damage = " + this.abilityDamage + ", Mana Cost = " + this.manaCost;
    }

    @Override
    public void visit(Entity entity) {
        if(entity.isImuneEarth(this)) {
            System.out.println("Imune to the Earth Spell used");
            System.out.println("Switching to basic attack");
        }
//        else {
//            entity.receiveDamage(this.getAbilityDamage());
//        }
    }
}
