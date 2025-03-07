package org.example.entities.characters.spells;

public class Test_Spells_Main {
    public void testEarth(){
        Earth earthSpell = new Earth(100,100);
        int correctDamage = 100;
        int correctMana = 100;
        System.out.println("Correct damage: " + correctDamage + ", Correct Mana : " + correctMana);
        System.out.println(earthSpell);
        if(earthSpell.abilityDamage == correctDamage && earthSpell.manaCost == correctMana){
            System.out.println("Earth Test Passed!");
        }
    }

    public void testFire(){
        Fire fireSpell = new Fire(100,100);
        int correctDamage = 100;
        int correctMana = 100;
        System.out.println("Correct damage: " + correctDamage + ", Correct Mana : " + correctMana);
        System.out.println(fireSpell);
        if(fireSpell.abilityDamage == correctDamage && fireSpell.manaCost == correctMana){
            System.out.println("Earth Test Passed!");
        }
    }

    public void testIce(){
        Ice iceSpell = new Ice(100,100);
        int correctDamage = 100;
        int correctMana = 100;
        System.out.println("Correct damage: " + correctDamage + ", Correct Mana : " + correctMana);
        System.out.println(iceSpell);
        if(iceSpell.abilityDamage == correctDamage && iceSpell.manaCost == correctMana){
            System.out.println("Ice Test Passed!");
        }
    }

    public static void main(String[] args) {
        new Test_Spells_Main().testEarth();
        new Test_Spells_Main().testFire();
        new Test_Spells_Main().testIce();
    }
}
