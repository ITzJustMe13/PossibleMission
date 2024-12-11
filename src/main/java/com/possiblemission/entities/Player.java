package com.possiblemission.entities;

import com.possiblemission.datastructures.abstractdatatypes.stacks.LinkedStack;
import com.possiblemission.entities.abstractEntities.Human;
import com.possiblemission.game.Division;
import pt.ipp.estg.ed.StackADT;

import java.io.IOException;
import java.io.Writer;

public class Player extends Human {

    private StackADT<HealthKit> backpack;

    private int backpackSize;

    private int maxHealth;

    public Player(String name, int power, int health) {
        super(name, power,health);
        backpack = new LinkedStack<>();
        this.maxHealth = health;
    }

    public void setCurrentDivision(Division currentDivision) {
        this.currentDivision = currentDivision;
    }

    public void addHealthKit(HealthKit healthKit) {
        if(backpack.size() < backpackSize) {
            backpack.push(healthKit);
        }else{
            System.out.println("Backpack is full");
        }
    }

    public void useHealthKit(){
        if (backpack.isEmpty()) return;
        HealthKit healthKit = backpack.pop();
        int heal = healthKit.getValue();
        int healthDiference = this.maxHealth - this.health;
        if(heal >= healthDiference){
            this.health += healthDiference;
        }else{
            this.health += heal;
        }
    }

    public void useArmour(Armour armour) {
        this.health += armour.getValue();
    }

    public HealthKit getTopHealthKit() {
        return (backpack.isEmpty()) ? null : backpack.peek();
    }

    public void setBackpackSize(int backpackSize) {
        this.backpackSize = backpackSize;
    }

    public boolean hasHealthKits(){
        return !backpack.isEmpty();
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    @Override
    public String toJson() {
        return "";
    }

    @Override
    public void toJson(Writer writer) throws IOException {

    }
}
