package org.example.entities.characters;

//import java.lang.Character;

//Class used to implement the Rogue character class
public class Rogue extends Character {
    public Rogue(String name, int currentExperience, int level) {
        super(name,currentExperience,currentExperience,1,5,4,100,125);
        this.imuneEarth = true;
        this.currentLevel = level;
    }

    //Method used to receive damage
    @Override
    public void receiveDamage(int damage) {
        //Secondary atributes lower damage
        damage -= this.charismaLevel ;
        damage -= this.strengthLevel * 3;
        if (damage <= 0) {
            damage = 0;
        }
        this.currentHealth -= damage;
        if (this.currentHealth <= 0) {
            this.currentHealth = 0;
        }
    }

    //Method used to deal damage
    @Override
    public int getDamage() {
        //Main atribute affects damage dealt
        return 20 + this.dexterityLevel * 5;
    }

    @Override
    public String toString() {
        return "Rogue stats : Name : " + this.name + " Level : " + this.currentLevel + " Experience : " +  this.currentExperience + "/" + this.currentLevel*10 + " Damage : " + this.getDamage() + " Health : " + this.getCurrentHealth() + " Mana : " + this.getCurrentMana();
    }
}
