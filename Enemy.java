package org.example.entities.characters;

import javax.swing.*;
import java.util.Random;

//Class used to generate foes
public class Enemy extends Entity {
    int damage;
    public Enemy(){
        super(100 + new Random().nextInt(101),100 + new Random().nextInt(101),new Random().nextBoolean(),new Random().nextBoolean(),new Random().nextBoolean());
        this.imuneEarth = new Random().nextBoolean();
        this.imuneFire = new Random().nextBoolean();
        this.imuneIce = new Random().nextBoolean();
        this.abilities = generateRandSpells();
        this.damage = new Random().nextInt(55);
    }

    //Method used to apply damage
    @Override
    public void receiveDamage(int damage) {
        //Has a 50% chance to dodge damage
        if (new Random().nextBoolean()){
        this.currentHealth -= damage;
        }
        else {
            JOptionPane.showMessageDialog(null,"Enemy dodged your attack");
        }
    }

    //Method used to deal damage
    @Override
    public int getDamage() {
        //50% chance to double damage
        Random rand = new Random();
        int damagedealt = this.damage;
        if (rand.nextBoolean()){
            damagedealt *= 2;
        }
        return damagedealt;
    }

    @Override
    public String toString() {
        return "Enemy stats : Base Damage " + this.damage + " Health : " + this.currentHealth + " Mana : " + this.currentMana;
    }
}
