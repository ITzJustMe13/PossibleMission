package com.possiblemission;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.github.cliftonlabs.json_simple.*;
import com.possiblemission.Import.ImportJson;
import com.possiblemission.datastructures.abstractdatatypes.lists.unordered.UnorderedArrayList;
import com.possiblemission.entities.Player;
import com.possiblemission.game.Game;

public class Main {
    public static void main(String[] args) {

        Game game = ImportJson.importJson("Json/Import/game.json");

        System.out.println(game.getMap().toString());
        System.out.println("Items: \n"+game.getItems().toString());
        System.out.println(game.getTarget().toString());
        System.out.println("Enemies: \n"+ game.getEnemies().toString());

        Player player = new Player("nome", 25, 100);

        System.out.println(player.getClass());
    }
}