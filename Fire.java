package org.example.entities.characters.spells;

import org.example.entities.characters.Entity;

//Class that handles Fire Spells
public class Fire extends Spell {
    public Fire(int damage,int mana) {
        super(damage, mana);
    }

    @Override
    public String toString() {
        return " Fire Spell : Damage = " + this.abilityDamage + ", Mana Cost = " + this.manaCost;
    }

    @Override
    public void visit(Entity entity) {
        if(entity.isImuneToFire(this)) {
            System.out.println("Imune to the Fire Spell used");
            System.out.println("Switching to basic attack");
        }
    }
}
