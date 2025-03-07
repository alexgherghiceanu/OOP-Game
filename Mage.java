package org.example.entities.characters;

//import java.lang.Character;

//Class used to implement the Mage character class
public class Mage extends Character {
    public Mage(String name, int currentExp, int level) {
        super(name,currentExp,level,3,2,5,150,200);
        this.imuneIce = true;
    }

    @Override
    public String toString() {
        return "Mage stats : Name : " + this.name + " Level : " + this.currentLevel + " Experience : " +  this.currentExperience + "/" + this.currentLevel*10 + " Damage : " + this.getDamage() + " Health : " + this.getCurrentHealth() + " Mana : " + this.getCurrentMana();
    }

    //Method used to receive damage
    @Override
    public void receiveDamage(int damage) {
        //int receivedDamage = damage;
        //The secondary atributes lower damage
        damage -= this.dexterityLevel * 2;
        damage -= this.strengthLevel ;
        if (damage > 0)
            this.currentHealth -= damage;
        if (this.currentHealth <= 0) {
            this.currentHealth = 0;
        }
    }

    //Method used to deal damage
    @Override
    public int getDamage() {
        //The main atribute affects damage
        return 10 + this.charismaLevel * 5;
    }
}
