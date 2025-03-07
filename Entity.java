package org.example.entities.characters;

import org.example.entities.characters.spells.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

//Class that handles the basic atributes of all entities
public abstract class Entity implements Battle,Element<Entity>{
    int currentHealth;
    int maxHealth;
    int currentMana;
    int maxMana;
    boolean imuneFire;
    boolean imuneIce;
    boolean imuneEarth;
    ArrayList<Spell> abilities;

    public Entity(int maxHealth, int maxMana,boolean imuneFire, boolean imuneIce, boolean imuneEarth) {
        this.currentHealth = maxHealth;
        this.currentMana = maxMana;
        this.maxHealth = maxHealth;
        this.maxMana = maxMana;
        this.imuneFire = imuneFire;
        this.imuneIce = imuneIce;
        this.imuneEarth = imuneEarth;
        this.abilities = new ArrayList<>();
    }


    //Method used to regen HP
    public void regenHealth(int amount) {
        if(currentHealth + amount <= this.maxHealth)
            currentHealth += amount;
        else
            currentHealth = this.maxHealth;
    }

    //Method used to regen Mana
    public void regenMana(int amount) {
        if(currentMana + amount <= this.maxMana)
            currentMana += amount;
        else
            currentMana = this.maxMana;
    }

    //Methods used to check each posible imunity
    public boolean isImuneToFire(Spell ability) {
        if (ability instanceof Fire && this.imuneFire)
            return true;
        return false;
    }

    public boolean isImuneIce(Spell ability) {
        if (ability instanceof Ice && this.imuneIce)
            return true;
        return false;
    }

    public boolean isImuneEarth(Spell ability) {
        if (ability instanceof Earth && this.imuneEarth)
            return true;
        return false;
    }


    @Override
    public void accept(Visitor<Entity> visitor) {
        visitor.visit(this);
    }

    public void useAbility(Spell ability, Entity enemy) {
        if(ability.getManaCost() > this.currentMana) {
            int damageDealt = this.getDamage();
            enemy.receiveDamage(damageDealt);
            if (this instanceof Character) {
                JOptionPane.showMessageDialog(null,"Need more mana to cast this spell. Dealt " + damageDealt +  " normal damage");
            }
        }
        else {
            enemy.accept(ability);
            this.currentMana -= ability.getManaCost();
            this.abilities.remove(ability);

            if(enemy.isImuneToFire(ability) && ability instanceof Fire) {
                //System.out.println("Switching to basic attack");
                int damageDealt = this.getDamage();
                enemy.receiveDamage(damageDealt);
                if (this instanceof Character) {
                    JOptionPane.showMessageDialog(null,"Imune to your spell. Dealt " + damageDealt +  " normal damage");
                }
            }

            else if (enemy.isImuneIce(ability) && ability instanceof Ice) {
                //System.out.println("Switching to basic attack");
                int damageDealt = this.getDamage();
                enemy.receiveDamage(damageDealt);
                if (this instanceof Character) {
                    JOptionPane.showMessageDialog(null,"Imune to your spell. Dealt " + damageDealt +  " normal damage");
                }
            }

            else if (enemy.isImuneEarth(ability) && ability instanceof Earth) {
                //System.out.println("Switching to basic attack");
                int damageDealt = this.getDamage();
                enemy.receiveDamage(damageDealt);
                if (this instanceof Character) {
                    JOptionPane.showMessageDialog(null,"Imune to your spell. Dealt " + damageDealt +  " normal damage");
                }
            }
            else {
                enemy.receiveDamage(ability.getAbilityDamage());
                if (this instanceof Character) {
                    JOptionPane.showMessageDialog(null,"Ability dealt " + ability.getAbilityDamage() +  " damage");
                }
            }
        }
    }

    //Method used to generate a random spell list when entering a battle
    public ArrayList<Spell> generateRandSpells() {
        ArrayList<Spell> spells = new ArrayList<>();
        Random rand = new Random();
        spells.add(new Fire(rand.nextInt(60),rand.nextInt(50)));
        spells.add(new Ice(rand.nextInt(60),rand.nextInt(50)));
        spells.add(new Earth(rand.nextInt(60),rand.nextInt(50)));
        for (int i = 0; i < new Random().nextInt(4); i++) {
            switch (new Random().nextInt() % 3) {
                case 0:
                    spells.add(new Fire(new Random().nextInt(50), new Random().nextInt(40)));
                    break;
                case 1:
                    spells.add(new Ice(new Random().nextInt(50), new Random().nextInt(40)));
                    break;
                case 2:
                    spells.add(new Earth(new Random().nextInt(50), new Random().nextInt(40)));
                    break;
            }

        }
        return spells;
    }

    public int getMaxHealth() {
        return this.maxHealth;
    }

    public int getCurrentHealth() {
        if (this.currentHealth < 0)
            return 0;
        else
            return this.currentHealth;
    }

    public int getCurrentMana() {
        if (this.currentMana < 0)
            return 0;
        else
            return this.currentMana;
    }

    public int getMaxMana() {
        return this.maxMana;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    public void setCurrentMana(int currentMana) {
        this.currentMana = currentMana;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    public ArrayList<Spell> getAbilities(){
        return this.abilities;
    }


    public abstract void receiveDamage(int damage);
    public abstract int getDamage();
}