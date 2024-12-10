package com.possiblemission.game;


import com.possiblemission.datastructures.abstractdatatypes.lists.unordered.UnorderedArrayList;
import com.possiblemission.entities.Enemy;
import com.possiblemission.entities.Target;
import com.possiblemission.entities.abstractEntities.Items;
import pt.ipp.estg.ed.UnorderedListADT;

public class Division {

    private String name;

    private UnorderedListADT<Enemy> enemies;

    private boolean isExitOrEntry;

    private boolean hasTarget;

    private Target target;

    private Items item;

    public Division(String name) {
        this.name = name;
        enemies = new UnorderedArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addEnemy(Enemy enemy){
        enemies.addToRear(enemy);
    }

    public void removeEnemy(Enemy enemy){
        enemies.remove(enemy);
    }

    public void setExitOrEntry(boolean exitOrEntry) {
        isExitOrEntry = exitOrEntry;
    }

    public void setTarget(Target target) {
        this.hasTarget = true;
        this.target = target;
    }

    public void setItem(Items item) {
        this.item = item;
    }

    public Items getItem() {
        return item;
    }

    public UnorderedListADT<Enemy> getEnemies(){
        return this.enemies;
    }

    @Override
    public String toString() {
        return name;
    }

    public void removeItem() {
        this.item = null;
    }
}
