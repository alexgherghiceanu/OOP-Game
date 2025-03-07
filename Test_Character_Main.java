package org.example.entities.characters;

import org.example.entities.characters.spells.*;

//Class used to test the implementation of the entities
public class Test_Character_Main {

    //Method to test Warrior
    public void testWarrior() {
        //Initialising the character
        Warrior warrior = new Warrior("Conan", 50, 5);
        Fire fireSpell = new Fire(30, 20);
        System.out.println(warrior);

        //Checking imunity is correctly implemented
        if (warrior.isImuneToFire(fireSpell)) {
            System.out.println("Warrior is immune to Fire spells.");
        } else {
            System.out.println("Warrior is not immune to Fire spells.");
        }

        //Making sure the receive damage and regen methods work correctly
        warrior.receiveDamage(50);
        System.out.println("Warrior Health after receiving damage: " + warrior.currentHealth);
        warrior.regenHealth(50);
        System.out.println("Warrior Health after regen: " + warrior.currentHealth);
        System.out.println("Warrior Damage: " + warrior.getDamage());

        //Testing level up logic and use of abilities
        warrior.levelUp();
        System.out.println(warrior);
        warrior.abilities.add(fireSpell);
        warrior.useAbility(warrior.abilities.getFirst(),warrior);
        for (int i = 0; i < 10;i++) {
            System.out.println(" ");
        }
    }

    public void testMage() {
        //Initialising the character
        Mage mage = new Mage("Gandalf", 100, 10);
        Ice iceSpell = new Ice(40, 30);
        System.out.println(mage);
        //Checking imunity is correctly implemented
        if (mage.isImuneIce(iceSpell)) {
            System.out.println("Mage is immune to Ice spells.");
        } else {
            System.out.println("Mage is not immune to Ice spells.");
        }

        //Making sure the receive damage and regen methods work correctly
        mage.receiveDamage(50);
        System.out.println("Mage Health after receiving damage: " + mage.currentHealth);
        mage.regenHealth(50);
        System.out.println("Mage Health after healing damage: " + mage.currentHealth);
        //Testing level up logic and use of abilities
        mage.useAbility(mage.abilities.getFirst(),mage);
        mage.levelUp();
        for (int i = 0; i < 10;i++) {
            System.out.println(" ");
        }
    }

    public void testRogue() {
        //Initialising the character
        Rogue rogue = new Rogue("Ezio", 80, 7);
        Earth earthSpell = new Earth(25, 78);
        System.out.println(rogue);
        //Checking imunity is correctly implemented
        if (rogue.isImuneEarth(earthSpell)) {
            System.out.println("Rogue is immune to Earth spells.");
        } else {
            System.out.println("Rogue is not immune to Earth spells.");
        }

        //Making sure the receive damage and regen methods work correctly
        rogue.receiveDamage(30);
        System.out.println("Rogue Health after receiving damage: " + rogue.currentHealth);
        rogue.regenMana(10);
        System.out.println(rogue.currentMana);
        //Testing level up logic and use of abilities
        rogue.abilities.add(earthSpell);
        rogue.useAbility(rogue.abilities.get(0),rogue);
        System.out.println(rogue.currentMana);
        System.out.println("Rogue Damage: " + rogue.getDamage());
        for (int i = 0; i < 10;i++) {
            System.out.println(" ");
        }
    }

    public void testEnemy() {
        //Initialising the enemy
        Enemy enemy = new Enemy();
        Fire fireSpell = new Fire(50, 30);
        Ice iceSpell = new Ice(40, 30);
        Earth earthSpell = new Earth(25, 78);
        System.out.println(enemy);

        //Seeing what the enemy is imune to
        if (enemy.isImuneToFire(fireSpell)) {
            System.out.println("Enemy is immune to Fire spells.");
        } else {
            System.out.println("Enemy is not immune to Fire spells.");
        }

        if (enemy.isImuneEarth(earthSpell)) {
            System.out.println("Enemy is imune to Earth spells.");
        }
        else {
            System.out.println("Enemy is not imune to Earth spells.");
        }

        if (enemy.isImuneIce(iceSpell)) {
            System.out.println("Enemy is imune to Ice spells.");
        }
        else {
            System.out.println("Enemy is imune to Ice spells.");
        }

        //Checking the damage dealing and receiving methods
        enemy.receiveDamage(50);
        System.out.println("Enemy Health after receiving damage: " + enemy.currentHealth);
        System.out.println(enemy);
        System.out.println("Enemy Damage: " + enemy.getDamage());
    }

    public static void main(String[] args) {
        new Test_Character_Main().testWarrior();
        new Test_Character_Main().testRogue();
        new Test_Character_Main().testMage();
        new Test_Character_Main().testEnemy();
    }
}
