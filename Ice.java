package org.example.entities.characters.spells;

import org.example.entities.characters.Entity;

//Class that handles Ice Spells
public class Ice extends Spell {
    public Ice(int damage,int mana) {
        super(damage,mana);
    }

    @Override
    public String toString() {
        return "Ice Spell : Damage = " + this.abilityDamage + ", Mana Cost = " + this.manaCost;
    }

    @Override
    public void visit(Entity entity) {
        if(entity.isImuneIce(this)) {
            System.out.println("Imune to the Ice Spell used");
            System.out.println("Switching to basic attack");
        }
//        else {
//            entity.receiveDamage(this.getAbilityDamage());
//        }
    }
}
