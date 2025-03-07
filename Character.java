package org.example.entities.characters;

import org.example.entities.characters.spells.Spell;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

//The basic class behind the 3 character classes
public abstract class Character extends Entity {
    String name;
    int currentExperience;
    int currentLevel;
    int strengthLevel;
    int dexterityLevel;
    int charismaLevel;
    ArrayList<Spell> abilities;
    int enemiesSlain;

    public Character(String name, int currentExperience, int currentLevel,int strengthLevel, int dexterityLevel, int charismaLevel,int maxHealth,int maxMana) {
        super(maxHealth,maxMana,false,false,false);
        this.name = name;
        this.currentHealth = maxHealth;
        this.currentMana = maxMana;
        this.currentExperience = currentExperience;
        this.currentLevel = currentLevel;
        this.strengthLevel = strengthLevel;
        this.dexterityLevel = dexterityLevel;
        this.charismaLevel = charismaLevel;
        this.abilities = generateRandSpells();
        this.enemiesSlain = 0;
    }

    //Method used to handle the logic behind upgrading your character
    public void levelUp() {
        int originaLevel = this.currentLevel;
        while (this.currentExperience >= this.currentLevel * 10) {
            this.currentExperience -= (this.currentLevel - 1) * 10;
            this.currentLevel++;
        }
        this.maxHealth += 25 * ( this.currentLevel - originaLevel );
        this.maxMana += 25 * ( this.currentLevel - originaLevel );
        this.currentHealth = this.maxHealth;
        this.currentMana = this.maxMana;
        //Every 10 levels, upgrade all atributes
        for (int i = 0 ; i < (this.currentLevel - originaLevel); i++) {
            if (this.currentLevel % 10 == 0) {
                this.strengthLevel++;
                this.dexterityLevel++;
                this.charismaLevel++;
            }
            //Else, only upgrade one
            else {
                switch (this.currentLevel % 3) {
                    case 0:
                        this.strengthLevel++;
                    case 1:
                        this.dexterityLevel++;
                    case 2:
                        this.charismaLevel++;
                }
            }
        }


        JOptionPane pane = new JOptionPane();
        pane.showMessageDialog(null, "You have leveled up! You are now level: " + this.currentLevel);
    }

    public String getName() {
        return this.name;
    }

    public int getCurrentExperience() {
        return this.currentExperience;
    }

    public int getCurrentLevel() {
        return this.currentLevel;
    }

    public int getCurrentMana() {
        return this.currentMana;
    }

    public int getStrengthLevel() {
        return this.strengthLevel;
    }

    public int getDexterityLevel() {
        return this.dexterityLevel;
    }

    public int getCharismaLevel() {
        return this.charismaLevel;
    }

    public void setCurrentExperience(int currentExperience) {
        this.currentExperience = currentExperience;
    }


    public ArrayList<Spell> getAbilities() {
        return this.abilities;
    }

    public void setAbilities(ArrayList<Spell> abilities) {
        this.abilities = abilities;
    }

    public String getType() {
        String type = "";
        if (this instanceof Mage) {
            type = "Mage";
        }
        if (this instanceof Warrior) {
            type = "Warrior";
        }
        if (this instanceof Rogue) {
            type = "Rogue";
        }
        return type;
    }

    public int getEnemiesSlain(){
        return this.enemiesSlain;
    }

    public void setEnemiesSlain(int enemiesSlain){
        this.enemiesSlain = enemiesSlain;
    }

}