package org.example.entities.characters;

//import java.lang.Character;

//Class used to implement the Warrior character class
public class Warrior extends Character {
    public Warrior(String name, int currentExperience, int level) {
        super(name,currentExperience,currentExperience,5,2,3,200,75);
        this.imuneFire = true;
        this.currentLevel = level;
    }

    //Method used to receive damage
    @Override
    public void receiveDamage(int damage) {
        //Secondary atributes lower damage
        damage -= this.dexterityLevel * 2;
        damage -= this.charismaLevel;
        if (damage < 0) {
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
        //Main atribute affects damage
        return 5 + this.strengthLevel * 5;
    }

    @Override
    public String toString() {
        return "Warrior stats : Name : " + this.name + " Level : " + this.currentLevel + " Experience : " +  this.currentExperience + "/" + this.currentLevel*10 + " Damage : " + this.getDamage() + " Health : " + this.getCurrentHealth() + " Mana : " + this.getCurrentMana();
    }
}
